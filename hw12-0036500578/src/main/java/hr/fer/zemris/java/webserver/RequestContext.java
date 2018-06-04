package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * This class represents context of server request. It generates and writes response 
 * and holds cookies of the current session. Additionally it hold different
 * types of parameters which are generated either directly by users request or 
 * by scripts.
 * @author Josip Trbuscic
 *
 */
public class RequestContext {
	
	/**
	 * Output stream
	 */
	private OutputStream outputStream;
	
	/**
	 * Charset
	 */
	private Charset charset;
	
	/**
	 * Response encoding
	 */
	private String encoding = "UTF-8";
	
	/**
	 * HTTP status code
	 */
	private int statusCode = 200;
	
	/**
	 * HTTP status text
	 */
	private String statusText = "OK";
	
	/**
	 * Mime type
	 */
	private String mimeType = "text/html";
	
	/**
	 * Map of regular parameters
	 */
	private Map<String, String> parameters;
	
	/**
	 * Map of temporary parameters
	 */
	private Map<String, String> temporaryParameters;
	
	/**
	 * Map of persistent parameters
	 */
	private Map<String, String> persistentParameters;
	
	/**
	 * List of cookies
	 */
	private List<RCCookie> outputCookies;
	
	/**
	 * Indicator if response header was generated
	 */
	private boolean headerGenerated = false;
	
	/**
	 * Dispatcher
	 */
	private IDispatcher dispatcher;

	/**
	 * Constructor
	 * @param outputStream - output stream
	 * @param parameters - regular parameters
	 * @param persistentParameters - persistent parameters
	 * @param outputCookies - cookies
	 */
	public RequestContext(OutputStream outputStream,
			Map<String, String> parameters,
			Map<String, String> persistentParameters,
			List<RCCookie> outputCookies
	) {
		Objects.requireNonNull(outputStream, "Output stream cannot be null");
		this.outputStream = outputStream;
		this.parameters = parameters == null ? new HashMap<String,String>() : parameters;
		this.persistentParameters = persistentParameters == null ? new HashMap<String,String>() : persistentParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<RCCookie>() : outputCookies;
		temporaryParameters = new HashMap<String,String>();
	}

	/**
	 * Constructor
	 * @param outputStream - output stream
	 * @param parameters - regular parameters
	 * @param persistentParameters - persistent parameters
	 * @param outputCookies - cookies
	 * @param temporaryParameters - temporary parameters
	 * @param dispatcher . dispatcher
	 */
	public RequestContext(OutputStream outputStream,
			Map<String, String> parameters,
			Map<String, String> persistentParameters,
			List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters,
			IDispatcher dispatcher
	) {
		this(outputStream, parameters, persistentParameters, outputCookies);
		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;
	}
	
	/**
	 * Class representing single cookie. It contains name, value,
	 * domain, path and max age value
	 * @author Josip Trbuscic
	 *
	 */
	public static class RCCookie {
		
		/**
		 * Name
		 */
		private String name;
		
		/**
		 * Value
		 */
		private String value;
		
		/**
		 * Domain
		 */
		private String domain;
		
		/**
		 * Path
		 */
		private String path;
		
		/**
		 * Max age
		 */
		private Integer maxAge;

		/**
		 * Cookie Constructor
		 * @param name -name
		 * @param value - value
		 * @param maxAge - max age
		 * @param domain domain
		 * @param path - path
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			Objects.requireNonNull(name, "Name cannot be null");
			Objects.requireNonNull(value, "Value cannot be null");
			
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}
		
		/**
		 * Returns cookie name
		 * @return cookie name
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * Returns cookie value
		 * @return cookie value
		 */
		public String getValue() {
			return value;
		}
		
		/**
		 * Returns cookie domain
		 * @return cookie domain
		 */
		public String getDomain() {
			return domain;
		}
		
		/**
		 * Returns cookie path
		 * @return cookie path
		 */
		public String getPath() {
			return path;
		}
		
		/**
		 * Returns max age
		 * @return max age
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

	}
	
	/**
	 * Writes given array of bytes to the output stream and generates 
	 * head if it was not already generated
	 * @param data - data to be written
	 * @return request context
	 * @throws IOException if writing to stream has failed
	 */
	public RequestContext write(byte[] data) throws IOException{
		if(!headerGenerated) {
			outputStream.write(generateHeader());
			headerGenerated = true;
		}
		outputStream.write(data);
		return this;
	}
	
	/**
	 * Writes given string to the output stream and generates 
	 * head if it was not already generated
	 * @param text - string to be written
	 * @return request context
	 * @throws IOException if writing to stream has failed
	 */
	public RequestContext write(String text) throws IOException {
		if(!headerGenerated) {
			outputStream.write(generateHeader());
			headerGenerated = true;
		}
		outputStream.write(text.getBytes(charset));
		return this;
	}
	
	/**
	 * Generates response header using previously set encoding
	 * @return array of bytes containing header
	 */
	private byte[] generateHeader() {
		charset = Charset.forName(encoding);
		
		StringBuilder headerSB = new StringBuilder();
		
		headerSB.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusText).append("\r\n");
		if(mimeType.startsWith("text/")) {
			headerSB.append("Content-Type: ").append(mimeType).append("; charset=").append(charset).append("\r\n");
		}else {
			headerSB.append("Content-Type: ").append(mimeType).append("\r\n");
		}
		if(!outputCookies.isEmpty()) {
			outputCookies.forEach(c->{
				headerSB.append("Set-Cookie: ").append(c.getName()+"=").append("\""+c.getValue()+"\"")
						.append(c.getDomain() == null ? "" : ("; Domain="+c.getDomain()))
						.append(c.getPath() == null ? "" : ("; Path="+c.getPath()))
						.append(c.getMaxAge() == null ? "" : ("; Max-Age="+c.getMaxAge()))
						.append("\r\n");
			});
		}
		headerSB.append("\r\n");
		return headerSB.toString().getBytes(StandardCharsets.ISO_8859_1);
	}
	
	//------------------------------------------------
	//					PARAMETERS
	//------------------------------------------------
	

	/**
	 * Returns parameter value for given key
	 * @param name - parameter key
	 * @return parameter value mapped to given key or null
	 * 		if such doesn't exist
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	/**
	 * Returns keys of parameters map
	 * @return keys of parameters map
	 */
	public Set<String> getParameterNames(){
		return new HashSet<String>(parameters.keySet());
	}
	
	//------------------------------------------------
	//				PERSISTENT PARAMETERS
	//------------------------------------------------
	
	/**
	 * Returns persistent parameter value for given key
	 * @param name - persistent parameter key
	 * @return persistent parameter value mapped to given key or null
	 * 		if such doesn't exist
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	
	/**
	 * Returns keys of persistent parameters map
	 * @return keys of persistent parameters map
	 */
	public Set<String> getPersistentParameterNames(){
		return new HashSet<>(persistentParameters.keySet());
	}
	
	/**
	 * Adds given key value pair to the map 
	 * of persistent parameters
	 * @param name - key
	 * @param value . value
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}
	
	/**
	 * Removes persistent parameter entry for given key 
	 * if such exists
	 * @param name - parameter key
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	
	//------------------------------------------------
	//				TEMPORARY PARAMETERS
	//------------------------------------------------
	
	/**
	 * Returns temporary parameter value for given key
	 * @param name - temporary parameter key
	 * @return temporary parameter value mapped to given key or null
	 * 		if such doesn't exist
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	
	/**
	 * Returns keys of temporary parameters map
	 * @return keys of temporary parameters map
	 */
	public Set<String> getTemporaryParameterNames(){
		return new HashSet<>(temporaryParameters.keySet());
	}
	
	/**
	 * Adds given key value pair to the map 
	 * of temporary parameters
	 * @param name - key
	 * @param value . value
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}
	
	/**
	 * Removes temporary parameter entry for given key 
	 * if such exists
	 * @param name - parameter key
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	//---------------------------------------------------
	//    		AUTO GENERATED GETTERS AND SETTERS
	//---------------------------------------------------
	
	/**
	 * Sets encoding to given value
	 * @param encoding - encoding to set
	 */
	public void setEncoding(String encoding) {
		if(encoding.equals(this.encoding)) return;
		if(headerGenerated) {
			throw new RuntimeException("Cannot change encoding after header was generated");
		}
		this.encoding = encoding;
	}

	/**
	 * Sets status code to given value
	 * @param statusCode - status code to set
	 */
	public void setStatusCode(int statusCode) {
		if(statusCode == this.statusCode) return;
		if(headerGenerated) {
			throw new RuntimeException("Cannot change status code after header was generated");
		}
		
		this.statusCode = statusCode;
	}

	/**
	 * Sets status text to given value
	 * @param statusText - status text to set
	 */
	public void setStatusText(String statusText) {
		if(statusText.equals(this.statusText)) return;
		if(headerGenerated) {
			throw new RuntimeException("Cannot change status text after header was generated");
		}
		this.statusText = statusText;
	}

	/**
	 * Sets mime type to given value
	 * @param mimeType - mime type to be set
	 */
	public void setMimeType(String mimeType) {
		if(mimeType.equals(this.mimeType)) return;
		if(headerGenerated) {
			throw new RuntimeException("Cannot change mime type after header was generated");
		}
		this.mimeType = mimeType;
	}
	
	/**
	 * Adds cookie to the cookie list of this context
	 * @param cookie - cookie to add
	 */
	public void addRCCookie(RCCookie cookie) {
		Objects.requireNonNull(cookie, "Cookie must not be null");
		if(headerGenerated) {
			throw new RuntimeException("Cannot add cookies type after header was generated");
		}
		outputCookies.add(cookie);
	}

	/**
	 * Returns current charset
	 * @return current charset
	 */
	public Charset getCharset() {
		return charset;
	}
	
	/**
	 * Returns dispatcher
	 * @return dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

}
