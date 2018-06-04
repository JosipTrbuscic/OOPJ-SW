package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Implementation of http server which is able to process specific requests.
 * Server can be configured by editing the properties files where some basic 
 * setting such as IP address, port and domain name can be set. By default 
 * server listens on loopback address and port 5721. Server can be stopped by 
 * entering "end" in the console. 
 * @author Joisp Trbuscic
 *
 */
public class SmartHttpServer {
	
	/**
	 * Address on which server listens
	 */
	private String address;
	
	/**
	 * Server domain
	 */
	private String domainName;
	
	/**
	 * Port on which server listens
	 */
	private int port;
	
	/**
	 * Number of worker threads
	 */
	private int workerThreads;
	
	/**
	 * Session duration
	 */
	private int sessionTimeout;
	
	/**
	 * Map of mime types
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	
	/**
	 * Server Thread
	 */
	private ServerThread serverThread;
	
	/**
	 * Pool of worker threads
	 */
	private ExecutorService threadPool;
	
	/**
	 * Document root path
	 */
	private Path documentRoot;
	
	/**
	 * Server socket
	 */
	private ServerSocket serverSocket = null;
	
	/**
	 * Map of workers
	 */
	private Map<String, IWebWorker> workersMap;
	
	/**
	 * Map of ongoing sessions
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	
	/**
	 * Generator of SID
	 */
	private Random sessionRandom = new Random();
	
	/**
	 * Server socket timeout 
	 */
	private static final int SO_TIMEOUT = 1000;

	/**
	 * Constructor
	 * @param configFileName - name of the file containing server configuration
	 */
	public SmartHttpServer(String configFileName) {
		Properties prop = new Properties();
		try {
			prop.load(Files.newInputStream(Paths.get("./config/" + configFileName), StandardOpenOption.READ));
		} catch (IOException e) {
			throw new IllegalArgumentException("Invalid path of server.properties file");
		}
		address = prop.getProperty("server.address");
		domainName = prop.getProperty("server.domainName");
		port = Integer.parseInt(prop.getProperty("server.port"));
		workerThreads = Integer.parseInt(prop.getProperty("server.workerThreads"));
		sessionTimeout = Integer.parseInt(prop.getProperty("session.timeout"));
		documentRoot = Paths.get(prop.getProperty("server.documentRoot"));
		
		getMimes(Paths.get(prop.getProperty("server.mimeConfig")));
		getWorkers(Paths.get(prop.getProperty("server.workers")));
	}

	/**
	 * Read file containing workers and puts their instances to the map
	 * @param path - workers file path
	 */
	@SuppressWarnings("deprecation")
	private void getWorkers(Path path) {
		Properties workers = new Properties();
		try {
			workers.load(Files.newInputStream(path, StandardOpenOption.READ));
			workersMap = new HashMap<>();
			for (Object key : workers.keySet()) {
				String fqcn = workers.getProperty((String) key);
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker) newObject;

				workersMap.put(key.toString(), iww);
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("Invalid path of workers.properties file");
		} catch (InstantiationException ignorable) {
		} catch (IllegalAccessException ignorable) {
		} catch (ClassNotFoundException ignorable) {
		}
		
	}

	/**
	 * Read file containing mimes and puts them in a map
	 * @param path - mimes file path
	 */
	private void getMimes(Path path) {
		Properties mimes = new Properties();
		try {
			mimes.load(Files.newInputStream(path, StandardOpenOption.READ));
			for (Object key : mimes.keySet()) {
				mimeTypes.put(key.toString(), mimes.getProperty((String) key));
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("Invalid path of mime.properties file");
		}
	}

	/**
	 * Starts server and session remover threads
	 */
	protected synchronized void start() {
		threadPool = Executors.newFixedThreadPool(workerThreads);
		if (serverThread == null) {
			serverThread = new ServerThread();
			serverThread.setDaemon(true);
			serverThread.start();
			Thread sessionRemover = new SessionRemover();
			sessionRemover.setDaemon(true);
			sessionRemover.start();
		}
	}

	/**
	 * Stops the server
	 * @throws IOException
	 */
	protected synchronized void stop() throws IOException {
		serverThread.end = true;
		threadPool.shutdownNow();
	}

	/**
	 * Class representing server thread used to accept users request 
	 * and create worker which will process the request
	 * @author Josip Trbuscic
	 *
	 */
	protected class ServerThread extends Thread {
		public boolean end = false;
		@Override
		public void run() {

			try {
				
				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(address, port));
				serverSocket.setSoTimeout(SO_TIMEOUT);
				while (true) {
					try {
						Socket client = serverSocket.accept();
						ClientWorker cw = new ClientWorker(client);
						threadPool.submit(cw);

					} catch (SocketTimeoutException ex) {
						if (end) break;
					}
					

				}
				serverSocket.close();
			} catch (IOException ignorable) {
			}
		}
	}
	
	/**
	 * Class representing thread that will periodically remove 
	 * expired sessions preventing excessive memory consumption
	 * @author Josip Trbuscic
	 *
	 */
	private class SessionRemover extends Thread {
		@Override
		public void run() {
			while(true) {
				synchronized (sessions) {
					Iterator<Map.Entry<String,SmartHttpServer.SessionMapEntry>> it = sessions.entrySet().iterator();
					while(it.hasNext()) {
						Map.Entry<String, SmartHttpServer.SessionMapEntry> entry = it.next();
						if(entry.getValue().validUntil < System.currentTimeMillis()/1000) {
							it.remove();
						}
					}
				}
				try {
					sleep(20000);
				} catch (InterruptedException e) {
				}
			}
			
		}
	}


	/**
	 * Class representing worker that processes client's request and generates response
	 * based on the request
	 * @author Josip Trbuscic
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		
		/**
		 * Client socket
		 */
		private Socket csocket;
		
		/**
		 * Input stream
		 */
		private PushbackInputStream istream;
		
		/**
		 * Output stream
		 */
		private OutputStream ostream;
		
		/**
		 * HTTP protocol version
		 */
		private String version;
		
		/**
		 * HTTP protocol method
		 */
		private String method;
		
		/**
		 * Host
		 */
		private String host;
		
		/**
		 * Map of regular parameters
		 */
		private Map<String, String> params = new HashMap<String, String>();
		
		/**
		 * Map of temporary parameters
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();
		
		/**
		 * Map of persistent parameters
		 */
		private Map<String, String> permParams = new HashMap<String, String>();
		
		/**
		 * List of output cookies
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		
		/**
		 * Request Context
		 */
		private RequestContext context;

		/**
		 * Constructor
		 * @param csocket - client socket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
				List<String> headers = readRequest();
				if (headers == null) {
					return;
				}
				checkHost(headers);

				synchronized (SmartHttpServer.this) {
					checkSession(headers);
				}
				String[] firstLine = headers.get(0).split("\\s+");
				String request = firstLine[1];
				String requestedPath;
				if (request.contains("?")) {
					try {
						requestedPath = request.split("\\?")[0];
						String paramString = request.split("\\?")[1];
						parseParameters(paramString);
					} catch(IndexOutOfBoundsException e) {
						sendError(ostream, 400, "Invalid arguments");
						return;
					}
					
					
				} else {
					requestedPath = request;
				}
				internalDispatchRequest(requestedPath, true);
				

			} catch (IOException ignorable) {
				
			} catch (Exception e) {
			} finally {
				try {
					csocket.close();
				} catch (IOException e) {
				}
			}

		}

		/**
		 * Checks if ongoing session with the same SID was already 
		 * created and extends its duration or creates new one and maps it
		 * @param headers
		 */
		private void checkSession(List<String> headers) {
			String sidCandidate = null;

			for (String header : headers) {
				if (header.trim().startsWith("Cookie:")) {
					sidCandidate = checkSid(header);
				}
			}
			if (sidCandidate == null) {
				generateSessionEntry();
			} else {
				SessionMapEntry session = sessions.get(sidCandidate);
				if (session == null || !session.host.equals(host) || session.validUntil < System.currentTimeMillis() / 1000) {
					generateSessionEntry();
				} else {
					session.validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
					outputCookies.add(new RCCookie("sid", session.sid, null, host, "/"));
					permParams = session.map;
				}

			}

		}

		/**
		 * Generates new session with unique SID
		 */
		private void generateSessionEntry() {
			String sid = null;
			sid = generateRandomSID();
			SessionMapEntry entry = new SessionMapEntry(sid, host, System.currentTimeMillis() / 1000 + sessionTimeout);
			permParams.put("a", "b");
			permParams = entry.map;
			outputCookies.add(new RCCookie("sid", sid, null, host, "/"));
			sessions.put(sid, entry);
		}

		/**
		 * Checks if given string represents a valid SID
		 * @param h - string to be checked
		 * @return string representation of SID or null
		 */
		private String checkSid(String h) {
			Pattern p = Pattern.compile("sid=\"([A-Z]{20})\"");
			Matcher m = p.matcher(h);
			if (!m.find())
				return null;
			return m.group(1);
		}

		/**
		 * Generates random SID
		 * @return unique SID
		 */
		private String generateRandomSID() {
			byte[] data = new byte[20];

			for (int i = 0; i < 20; ++i) {
				data[i] = (byte) (sessionRandom.nextInt(26) + 65);
			}
			return new String(data);
		}

		/**
		 * Checks if there is a host header specified in 
		 * clients request.
		 * @param headers - headers
		 */
		private void checkHost(List<String> headers) {
			host = domainName.trim();
			headers.forEach(h -> {
				if (h.trim().startsWith("Host:")) {
					String[] parts = h.split(":");
					if (parts.length > 1) {
						host = parts[1].trim();
					}
				}
			});
		}

		/**
		 * Extracts extension of requested file
		 * @param resolved - file path
		 * @return extension of requested file
		 */
		private String extractFileExtension(String resolved) {
			int dot = resolved.lastIndexOf('.');
			String extension = resolved.substring(dot + 1);
			return extension;
		}

		/**
		 * Parses the given parameters and puts the in a 
		 * parameters map.
		 * @param paramString - string of parameters
		 */
		private void parseParameters(String paramString) {
			String parts[] = paramString.split("&");
			for (String part : parts) {
				String[] keyAndVal = part.split("=");
				if(keyAndVal.length < 2) {
					try {
						sendError(ostream, 403, "Invalid Request");
					} catch (IOException ignorable) {
					}
					return;
				}
				params.put(keyAndVal[0].trim(), keyAndVal[1].trim());
			}

		}

		/**
		 * Reads and validates clients request and returns the list of headers,
		 * or null if request is not valid
		 * @return list of headers
		 * @throws IOException if request could not be read
		 */
		private List<String> readRequest() throws IOException {
			byte[] request = readRequest(istream);

			if (request == null) {
				sendError(ostream, 400, "Bad Request");
			}
			
			String requestStr;
			try {
				requestStr = new String(request, StandardCharsets.US_ASCII);
			}catch(NullPointerException e) {
				return null;
			}
			List<String> headers = extractHeaders(requestStr);
			String[] firstLine = headers.isEmpty() ? null : headers.get(0).split(" ");
			if (firstLine == null || firstLine.length != 3) {
				sendError(ostream, 400, "Bad request");
				return null;
			}

			method = firstLine[0].toUpperCase();
			if (!method.equals("GET")) {
				sendError(ostream, 405, "Method Not Allowed");
				return null;
			}

			version = firstLine[2].toUpperCase();
			if (!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
				sendError(ostream, 505, "HTTP Version Not Supported");
				return null;
			}

			return headers;
		}

		/**
		 * Reads request from input stream and returns it as a array of bytes
		 * @param is - input stream 
		 * @return array of bytes representing clients request
		 * @throws IOException if request could not be read
		 */
		private byte[] readRequest(InputStream is) throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = is.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			return bos.toByteArray();
		}

		/**
		 * Generates and sends and error to the output stream 
		 * @param cos - output stream
		 * @param statusCode - http status code
		 * @param statusText - status text
		 * @throws IOException if error could not be written
		 */
		private void sendError(OutputStream cos, int statusCode, String statusText) throws IOException {

			cos.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			cos.flush();

		}

		/**
		 * Extracts headers from the given string and returns them as 
		 * a list of strings
		 * @param requestHeader - headers string
		 * @return list of headers
		 */
		private List<String> extractHeaders(String requestHeader) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for (String s : requestHeader.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);

		}

		/**
		 * Dispatches request to the script or class represented by the given path
		 * @param urlPath - path 
		 * @param directCall - flag that indicates if request is direct
		 * @throws Exception
		 */
		@SuppressWarnings("deprecation")
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			if (urlPath.startsWith("/private") && directCall) {
				sendError(ostream, 403, "Private");
			}
			
			Path resolved = documentRoot.resolve(urlPath.substring(1)).toAbsolutePath();
			if (!resolved.startsWith(documentRoot)) {
				sendError(ostream, 403, "Forbidden");
				return;
			}
			

			context = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this);
			if (urlPath.startsWith("/ext/")) {
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(
						Class.forName("hr.fer.zemris.java.webserver.workers." + urlPath.substring(5)).getName());
				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker) newObject;
				iww.processRequest(context);
				return;
			} else if (workersMap.containsKey(urlPath)) {
				synchronized (resolved) {
					workersMap.get(urlPath).processRequest(context);
				}
				return;

			}
			
			if (!Files.exists(resolved) || !Files.isReadable(resolved) || !Files.isRegularFile(resolved)) {
				sendError(ostream, 404, "File not found!");
				return;
			}
			
			if (urlPath.endsWith(".smscr")) {
				String document = new String(Files.readAllBytes(resolved));
				SmartScriptParser parser = new SmartScriptParser(document);
				SmartScriptEngine engine = new SmartScriptEngine(parser.getDocumentNode(), context);
				try {
					engine.execute();
				} catch (RuntimeException ignorable) {
				}
			} else {
				
				String extension = extractFileExtension(resolved.toString());
				String mimeType = mimeTypes.get(extension) == null ? "application/octet-stream": mimeTypes.get(extension);
				context.setMimeType(mimeType);
				context.setStatusCode(200);

				byte[] data = Files.readAllBytes(resolved);
				context.write(data);
			}
			
		}
	}
	
	/**
	 * Class representing session 
	 * @author Josip Trbuscic
	 *
	 */
	private static class SessionMapEntry {
		
		/**
		 * Session id
		 */
		String sid;
		
		/**
		 * Host
		 */
		String host;
		
		/**
		 * Expiration time
		 */
		long validUntil;
		
		/**
		 * Parameters map
		 */
		Map<String, String> map;

		public SessionMapEntry(String sid, String host, long validUnitl) {
			this.sid = sid;
			this.host = host;
			this.validUntil = validUnitl;
			map = new ConcurrentHashMap<>();
		}
	}

	/**
	 * Main method
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		SmartHttpServer smartServer = new SmartHttpServer("server.properties");
		smartServer.start();
		Scanner sc = new Scanner(System.in);
		while (true) {
			if (sc.nextLine().equals("end")) {
				break;
			}
		}
		sc.close();
		smartServer.stop();
	}
}
