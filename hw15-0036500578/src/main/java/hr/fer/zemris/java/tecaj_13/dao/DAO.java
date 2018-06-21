package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Interface towards application layer
 * 
 * @author Josip Trbuscic
 *
 */
public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Returns all registered users 
	 * @return all registered users
	 * @throws DAOException
	 */
	public List<BlogUser> getAllUsers() throws DAOException;
	
	/**
	 * Returns user who is uniquely specified by its nick and password or
	 * null such user doesn't exist
	 * @param nick - users nickname
	 * @param password - hashed password
	 * @return user who is uniquely specified by its nick and password
	 * @throws DAOException
	 */
	public BlogUser getBlogUser(String nick, String password) throws DAOException;
	
	/**
	 * Returns user who is uniquely specified by its nickname or
	 * null such user doesn't exist
	 * @param nick - users nickname
	 * @return user who is uniquely specified by its nickname
	 * @throws DAOException
	 */
	public BlogUser getUserFromNick(String nick) throws DAOException;
	
	/**
	 * Registers given user. Given user must not already be 
	 * stored
	 * @param user - user to register
	 */
	public void registerBlogUser(BlogUser user);
	
	/**
	 * Returns all blog entries which were created by specified user
	 * or null if such doesn't exist
	 * @param user - registered user
	 * @return all blog entries which were created by specified user
	 */
	public List<BlogEntry> getUserBlogs(BlogUser user);
	
	/**
	 * Stores specified blog entry to the database
	 * @param entry - blog entry to be stored
	 * @throws DAOException
	 */
	public void addBlogEntry(BlogEntry entry) throws DAOException;
	
	/**
	 * Stores given blog comment in database
	 * @param comment - blog comment
	 * @throws DAOException
	 */
	public void addBlogComment(BlogComment comment) throws DAOException;
	
}