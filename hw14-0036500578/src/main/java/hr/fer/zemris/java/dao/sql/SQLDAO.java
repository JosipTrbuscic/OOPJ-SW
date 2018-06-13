package hr.fer.zemris.java.dao.sql;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;

import hr.fer.zemris.java.SQLConnectionProvider;
import hr.fer.zemris.java.dao.DAO;
import hr.fer.zemris.java.dao.DAOException;
import hr.fer.zemris.java.model.Poll;
import hr.fer.zemris.java.model.PollOption;

/**
 * 
 * Implementation of DAO system using SQL. This implementation expects 
 * connection to be already established using {@link SQLConnectionProvider}
 * class. 
 *  
 * @author Josip Trbuscic
 */
public class SQLDAO implements DAO {
	
	@Override
	public void createPolls() throws DAOException{
		Connection con = SQLConnectionProvider.getConnection();
		if(checkIfTableAlreadyCreated("Polls", con)) return;
		
		try {
			try (PreparedStatement pst = con.prepareStatement("CREATE TABLE Polls "
					+ "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
					+ "title VARCHAR(150) NOT NULL,"
					+ "message CLOB(2048) NOT NULL)")
				)
			{
				pst.executeUpdate();
			}
		} catch(Exception ex) {
			throw new DAOException("Could not create Polls", ex);
		}
	}
	
	@Override
	public void createPollOptions() throws DAOException{
		Connection con = SQLConnectionProvider.getConnection();
		if(checkIfTableAlreadyCreated("PollOptions", con)) return;
		
		try {
			try (PreparedStatement pst = con.prepareStatement("CREATE TABLE PollOptions " + 
					"(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " + 
					"optionTitle VARCHAR(100) NOT NULL, " + 
					"optionLink VARCHAR(150) NOT NULL, " + 
					"pollID BIGINT, " + 
					"votesCount BIGINT, " + 
					"FOREIGN KEY (pollID) REFERENCES Polls(id) " + 
					")")
				)
			{
				pst.executeUpdate();
			}
		} catch(Exception ex) {
			throw new DAOException("Could not create PollOptions", ex);
		}
	}
	
	
	@Override
	public void populatePolls(ServletContextEvent sce) throws DAOException{
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(sce.getServletContext().getRealPath("/WEB-INF/polls.txt")));
		} catch (IOException ignorable) {
		}
		try {
			try {
				for(String line : lines) {
					String[] parts = line.split("\\t");
					pst = con.prepareStatement(
							"insert into Polls (title, message) values (?,?)"
							, Statement.RETURN_GENERATED_KEYS
							);
					pst.setString(1, parts[0]);
					pst.setString(2, parts[1]);
					pst.executeUpdate();
					ResultSet res = pst.getGeneratedKeys();
					res.next();
					populateOptions(res.getInt(1), Paths.get(sce.getServletContext().getRealPath(parts[2])));
				}
				
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Could not populate polls", ex);
		}
	}
	
	/**
	 * Populates poll options with predefined data 
	 * @param pollID - poll id
	 * @param path - path to poll options data
	 * @throws DAOException if polls cannot be populated
	 */
	private void populateOptions(Integer pollID, Path path) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path);
		} catch (IOException ignorable) {
		}
		try {
			try {
				for (String line : lines) {
					String[] parts = line.split("\\t");
					
					pst = con.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink,pollID,votesCount) VALUES (?,?,?,0)");
					
					pst.setString(1, parts[1]);
					pst.setString(2, parts[2]);
					pst.setInt(3, pollID);
					pst.executeUpdate();
					pst.clearParameters();
				}
				
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Could not populate poll options", ex);
		}
	}

	private boolean checkIfTableAlreadyCreated(String tableName, Connection con) {
		boolean tExists = false;
		
		try(ResultSet res = con.getMetaData().getTables(null, null, tableName.toUpperCase(), null)) {
			if(res != null && res.next()) { 
				tExists = true;
	        }
		} catch (SQLException e) {
			throw new DAOException("Could not get "+tableName+" metadata", e);
		}
		return tExists;
	}

	@Override
	public boolean isTableEmpty(String tableName) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		boolean isEmpty = false;
		try {
			try (PreparedStatement pst = con.prepareStatement("select * from "+tableName)){
				ResultSet rs = pst.executeQuery();
				if(rs == null || !rs.next()) isEmpty = true;
			}
		} catch(Exception e) {
			throw new DAOException("Cold not read table entries", e);
		}
		return isEmpty;
	}
	
	@Override
	public List<Poll> getPolls() throws DAOException{
		Connection con = SQLConnectionProvider.getConnection();
		List<Poll> polls = new ArrayList<>();
		try {
			try (PreparedStatement pst = con.prepareStatement("select * from polls")) {
				ResultSet rs = pst.executeQuery();
				while(rs.next()) {
					int id = rs.getInt(1);
					String title = rs.getString(2);
					String message = rs.getString(3);
					polls.add(new Poll(id, title, message));
				}
				
			}
		} catch(Exception ex) {
			throw new DAOException("Could not obtain polls", ex);
		}
		return polls;
	}
	
	@Override
	public List<PollOption> getPollOptions(int pollID) throws DAOException{
		Connection con = SQLConnectionProvider.getConnection();
		List<PollOption> pollOptions = new ArrayList<>();
		try {
			try (PreparedStatement pst = con.prepareStatement("select * from pollOptions where pollID = ?")) {
				pst.setInt(1, pollID);
				ResultSet rs = pst.executeQuery();
				while(rs.next()) {
					int id = rs.getInt(1);
					String optionTitle = rs.getString(2);
					String optionLink = rs.getString(3);
					int votesCount = rs.getInt(5);
					pollOptions.add(new PollOption(id, optionTitle, optionLink, pollID, votesCount));
				}
			}
		} catch(Exception ex) {
			throw new DAOException("Could not obtain poll options", ex);
		}
		return pollOptions;
	}
	
	@Override
	public Poll getPoll(int pollID) throws DAOException{
		Connection con = SQLConnectionProvider.getConnection();
		Poll poll = null;
		try {
			try(PreparedStatement pst = con.prepareStatement("select * from polls where id = ?")) {
				pst.setInt(1, pollID);
				ResultSet rs = pst.executeQuery();
				while(rs.next()) {
					String title = rs.getString(2);
					String message = rs.getString(3);
					poll = new Poll(pollID, title, message);
				}
			}
		} catch(Exception ex) {
			throw new DAOException("Could not obtain poll", ex);
		}
		return poll;
	}
	
	@Override
	public void incrementVoteCount(int optionID) throws DAOException{
		Connection con = SQLConnectionProvider.getConnection();
		try {
			try(PreparedStatement pst = con.prepareStatement(
					"update pollOptions set votesCount = votesCount + 1 where id = ?"
					)
				)
			{
				
				pst.setInt(1, optionID);
				pst.executeUpdate();
			}
		} catch(Exception ex) {
			throw new DAOException("Could not increment vote count", ex);
		}
	}
	
	@Override
	public int getPollID(int optionID) throws DAOException{
		Connection con = SQLConnectionProvider.getConnection();
		try {
			try(PreparedStatement pst = con.prepareStatement(
					"select distinct pollID from pollOptions where id = ?"
					)
				) {
				pst.setInt(1, optionID);
				ResultSet res = pst.executeQuery();
				res.next();
				return res.getInt(1);
			}
		} catch(Exception ex) {
			throw new DAOException("Could not poll ID", ex);
		}
		
	}
}