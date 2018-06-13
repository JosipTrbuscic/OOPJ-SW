package hr.fer.zemris.java.p12.dao.sql;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;

import hr.fer.zemris.java.p12.Poll;
import hr.fer.zemris.java.p12.SQLConnectionProvider;
import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova
 * konkretna implementacija očekuje da joj veza stoji na raspolaganju
 * preko {@link SQLConnectionProvider} razreda, što znači da bi netko
 * prije no što izvođenje dođe do ove točke to trebao tamo postaviti.
 * U web-aplikacijama tipično rješenje je konfigurirati jedan filter 
 * koji će presresti pozive servleta i prije toga ovdje ubaciti jednu
 * vezu iz connection-poola, a po zavrsetku obrade je maknuti.
 *  
 * @author marcupic
 */
public class SQLDAO implements DAO {
	@Override
	public void createPolls() throws DAOException, SQLException {
		Connection con = SQLConnectionProvider.getConnection();
		if(checkIfTableAlreadyCreated("Polls", con)) return;
		
		try {
			PreparedStatement pst = null;
			try {
				pst = con.prepareStatement("CREATE TABLE Polls "
						+ "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
						+ "title VARCHAR(150) NOT NULL,"
						+ "message CLOB(2048) NOT NULL)");
				pst.executeUpdate();
			}finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Could not create Polls", ex);
		}
	}
	
	@Override
	public void createPollOptions() throws DAOException, SQLException {
		Connection con = SQLConnectionProvider.getConnection();
		if(checkIfTableAlreadyCreated("PollOptions", con)) return;
		
		try {
			PreparedStatement pst = null;
			try {
				pst = con.prepareStatement("CREATE TABLE PollOptions\n" + 
						"    (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" + 
						"    optionTitle VARCHAR(100) NOT NULL,\n" + 
						"    optionLink VARCHAR(150) NOT NULL,\n" + 
						"    pollID BIGINT,\n" + 
						"    votesCount BIGINT,\n" + 
						"    FOREIGN KEY (pollID) REFERENCES Polls(id)\n" + 
						"	 )");
				pst.executeUpdate();
			}finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Could not create PollOptions", ex);
		}
	}
	@Override
	public void populatePolls(ServletContextEvent sce) throws DAOException, SQLException{
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
					pst = con.prepareStatement(
							"insert into PollOptions (optionTitle, optionLink, pollID, votesCount) values (?, ?, ?, 0)");
					
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
		PreparedStatement pst = null;
		boolean isEmpty = false;
		try {
			try {
				pst = con.prepareStatement("select * from "+tableName);
				ResultSet rs = pst.executeQuery();
				if(rs == null || !rs.next()) isEmpty = true;
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception e) {
			throw new DAOException("Cold not read table entries", e);
		}
		return isEmpty;
	}
	
	@Override
	public List<Poll> getPolls(){
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		List<Poll> polls = new ArrayList<>();
		try {
			try {
				pst = con.prepareStatement("select * from polls");
				ResultSet rs = pst.executeQuery();
				while(rs.next()) {
					int id = rs.getInt(1);
					String title = rs.getString(2);
					String message = rs.getString(3);
					polls.add(new Poll(id, title, message));
				}
				
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Could not obtain polls", ex);
		}
		return polls;
	}
}