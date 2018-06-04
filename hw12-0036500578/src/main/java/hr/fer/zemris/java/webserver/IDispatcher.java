package hr.fer.zemris.java.webserver;

/**
 * Interface which defines method every request dispatcher must implement
 * @author Josip Trbuscic
 *
 */
public interface IDispatcher {
	
	/**
	 * Dispatches request to the script or class represented by the given path
	 * @param urlPath - path 
	 * @throws Exception
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
