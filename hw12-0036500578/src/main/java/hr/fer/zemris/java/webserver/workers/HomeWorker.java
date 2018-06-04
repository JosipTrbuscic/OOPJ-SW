package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Implementation of {@link IWebWorker} which runs the script 
 * that generates html document allowing user to change the
 *  background of html page and do addition of two integers
 * @author Josip Trbuscic
 *
 */
public class HomeWorker implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String color = context.getPersistentParameter("bgcolor") == null ? "7F7F7F" :context.getPersistentParameter("bgcolor");
		
		context.setTemporaryParameter("background", new String(color));
		context.getDispatcher().dispatchRequest("/private/home.smscr");
		
		
	}

}
