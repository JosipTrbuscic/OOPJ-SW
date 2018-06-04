package hr.fer.zemris.java.webserver.workers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Implementation of {@link IWebWorker} which allows user 
 * to change background of HTML page
 * @author Josip Trbuscic
 *
 */
public class BgColorWorker implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String color = context.getParameter("bgcolor");
		context.setMimeType("text/html");
		context.write("<html><body><p> Context was ");
		
		if(isValidColor(color)) {
			context.setPersistentParameter("bgcolor", color);
		}else {
			context.write("not ");
		} 
		context.write("updated.</a>");
		
		context.write("<a href=\"/index2.html\">Index2</a>");
		context.write("</body></html>");
	}

	/**
	 * Returns true if given string represents valid 
	 * 6 digit hexadecimal number
	 * @param string - string to be checked
	 * @return if given string represents valid 
	 *			 6 digit hexadecimal number
	 */
	private boolean isValidColor(String string) {
		Pattern p = Pattern.compile("[0-9A-F]{6}");
		Matcher m = p.matcher(string);
		if(m.find()) return true;
		return false;
	}

}
