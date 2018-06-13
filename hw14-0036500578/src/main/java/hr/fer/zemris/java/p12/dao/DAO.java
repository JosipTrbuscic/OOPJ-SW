package hr.fer.zemris.java.p12.dao;

import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;

import hr.fer.zemris.java.p12.Poll;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author marcupic
 *
 */
public interface DAO {
	public void createPolls() throws DAOException, SQLException ;
	public void createPollOptions() throws DAOException, SQLException ;
	public boolean isTableEmpty(String tableName) throws DAOException;
	public void populatePolls(ServletContextEvent sce) throws DAOException, SQLException;
	public List<Poll> getPolls();
}