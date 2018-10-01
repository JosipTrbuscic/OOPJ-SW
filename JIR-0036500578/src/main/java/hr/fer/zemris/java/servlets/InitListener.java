package hr.fer.zemris.java.servlets;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InitListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("i", 7);
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
	
}
