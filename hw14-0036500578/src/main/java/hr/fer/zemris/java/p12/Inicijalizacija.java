package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.dao.DAOProvider;

@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		Properties dbProperties = new Properties();
		try {
			dbProperties.load(Files.newInputStream(
					Paths.get(sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties"))
					, StandardOpenOption.READ)
			);
		} catch (IOException ignorable) {}
		
		String host = dbProperties.getProperty("host");
		String port = dbProperties.getProperty("port");
		String dbName = dbProperties.getProperty("name");
		String user = dbProperties.getProperty("user");
		String pass = dbProperties.getProperty("password");
		
		String connectionURL = "jdbc:derby://"+host+":"+port+"/" + dbName + ";user="+user+";password="+pass;
//		try {
//			BufferedWriter bw = Files.newBufferedWriter(Paths.get("/home/josip/hw14.log"), StandardOpenOption.TRUNCATE_EXISTING);
//			bw.write(connectionURL);
//			bw.flush();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);
		
		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		try {
			SQLConnectionProvider.setConnection(cpds.getConnection());
		} catch (SQLException ignorable) {
		}
		try {
			DAO dao = DAOProvider.getDao();
		
			dao.createPolls();
			dao.createPollOptions();
			if(dao.isTableEmpty("Polls")) {
				dao.populatePolls(sce);
			} 
		}catch(Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}