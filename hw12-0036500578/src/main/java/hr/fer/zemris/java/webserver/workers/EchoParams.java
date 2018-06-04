package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Implementation of {@link IWebWorker} returns parameters 
 * contained in user request as formated table
 * @author Josip Trbuscic
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("text/html");
		StringBuilder sb = new StringBuilder("<html>\n<body>\n<table border =\"1\">");
		for(String key : context.getParameterNames()) {
			sb.append("<tr><td>").append(key).append("</td>").append("<td>").append(context.getParameter(key)).append("</td></tr>\n");
		}
		sb.append("</table>\n</body>\n</html>");
		context.write(sb.toString());
	}

}