package hr.fer.zemris.java.dao;

import java.nio.file.Path;
import java.util.List;

import javax.servlet.ServletContextEvent;

import hr.fer.zemris.java.model.Poll;
import hr.fer.zemris.java.model.PollOption;

/**
 * Interface towards application layer
 * 
 * @author Josip Trbuscic
 *
 */
public interface DAO {
	
	/**
	 * Creates table Polls in database if such doesn't exist. 
	 * Table contains all information about every poll available
	 * for voting in this application
	 * @throws DAOException if table could not be created
	 */
	public void createPolls() throws DAOException;
	
	/**
	 * Creates table PollOptions in database if such doesn't exist.
	 * Table contains all options available to vote for and every
	 * option contains id of corresponding poll
	 * @throws DAOException if table could not be created
	 */
	public void createPollOptions() throws DAOException;
	
	/**
	 * Returns true if table with given name contains no entries
	 * @param tableName - table name
	 * @return true if table with given name contains no entries, false otherwise
	 * @throws DAOException if database could not be accessed
	 */
	public boolean isTableEmpty(String tableName) throws DAOException;
	
	/**
	 * Populates tables with predefined data
	 * @param sce - servlet context
	 * @throws DAOException if tables cannot be populated 
	 */
	public void populatePolls(ServletContextEvent sce) throws DAOException;
	
	/**
	 * Returns list of available polls
	 * @return list of available polls
	 * @throws DAOException - if polls cannot  be obtained
	 */
	public List<Poll> getPolls() throws DAOException;
	
	/**
	 * Returns list of poll options for specified poll
	 * @param pollID - ID of a poll
	 * @return list of poll options for specified poll
	 * @throws DAOException if poll options cannot  be obtained
	 */
	public List<PollOption> getPollOptions(int pollID) throws DAOException;
	
	/**
	 * Returns poll with specified id 
	 * @param pollID - poll id
	 * @return poll with specified id 
	 * @throws DAOException if poll could not be obtained
	 */
	public Poll getPoll(int pollID) throws DAOException;
	
	/**
	 * Adds one to the number of votes of poll option
	 * specified with given id
	 * @param optionID - option id
	 * @throws DAOException if number of votes cannot be incremented
	 */
	public void incrementVoteCount(int optionID) throws DAOException;
	
	/**
	 * Returns poll id for given id of a poll option
	 * @param optionID - option id
	 * @return poll id for given id of a poll option
	 * @throws DAOException if poll id cannot be obtained
	 */
	public int getPollID(int optionID) throws DAOException;
}