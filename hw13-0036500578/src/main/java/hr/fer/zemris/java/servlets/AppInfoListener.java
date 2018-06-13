package hr.fer.zemris.java.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application listeners which maps the time application was
 * in the servlet context 
 * @author Josip Trbuscic
 *
 */
@WebListener
public class AppInfoListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
			sce.getServletContext().setAttribute("startTime", System.currentTimeMillis());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
