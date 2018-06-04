package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Implementation of {@link IWebWorker} which adds two integers 
 * given by users and prints the result in formated table
 * @author Josip Trbuscic
 *
 */
public class SumWorker implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("text/html");
		Integer a = getInteger(context.getParameter("a")) == null ? 1 : getInteger(context.getParameter("a"));
		Integer b = getInteger(context.getParameter("b")) == null ? 2 : getInteger(context.getParameter("b"));
		context.setTemporaryParameter("a", a.toString());
		context.setTemporaryParameter("b", b.toString());
		context.setTemporaryParameter("zbroj", String.valueOf(a+b));
		context.getDispatcher().dispatchRequest("/private/calc.smscr");
	}
	
	/**
	 * Returns {@link Integer} object if given string can be
	 * parsed as integer, null otherwise
	 * @param s - string to be parsed
	 * @return integer object if given string can be
	 * parsed as integer, null otherwise
	 */
	private Integer getInteger(String s) {
		if(s == null) return null;
		
		try {
			return Integer.parseInt(s);
		}catch(NumberFormatException e){
			return null;
		}
	}
}
