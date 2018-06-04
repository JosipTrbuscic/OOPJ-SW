package hr.fer.zemris.java.webserver;

/**
 * Interface which defines method that must be implemented 
 * by a class that want to be able to process request
 * @author Josip Trbuscic
 *
 */
public interface IWebWorker {
	
	/**
	 * Processes request, generates responses and writes it
	 * to the request context
	 * @param context - request context
	 * @throws Exception
	 */
	public void processRequest(RequestContext context) throws Exception;
}
