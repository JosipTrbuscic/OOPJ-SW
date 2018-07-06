package hr.fer.zemris.java.tecaj_13.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.EncryptionUtil;

/**
 * Form used to validate users login data
 * @author Josip Trbuscic
 *
 */
public class LoginForm {
	
	/**
	 * Users nick
	 */
	private String nick;
	
	/**
	 * Users password
	 */
	private String password;
	
	/**
	 * Blog user
	 */
	BlogUser user;
	
	private Map<String, String> errors = new HashMap<>();
	
	/**
	 * Constructor
	 */
	public LoginForm() {
	}
	
	/**
	 * Fills the form with the data from the  request
	 * @param req - http request
	 */
	public void fillFromHttpRequest(HttpServletRequest req){
		this.nick = prepare(req.getParameter("nick"));
		this.password = prepare(req.getParameter("password"));
		this.user = DAOProvider.getDAO().getBlogUser(nick, EncryptionUtil.encrypt(password));
	}
	
	/**
	 * Returns true if errors were found
	 * @return true if errors were found
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	/**
	 * Returns true if specified error was found
	 * @param name - error name
	 * @return true if specified error was found
	 */
	public boolean hasError(String name) {
		return errors.containsKey(name);
	}
	
	/**
	 * Returns error with specified name
	 * @param name
	 * @return terror with specified name
	 */
	public String getError(String name) {
		return errors.get(name);
	}
	
	/**
	 * Validates the form and puts errors in the map if they are found
	 */
	public void validate() {
		errors.clear();
		
		if(this.password.isEmpty()) {
			errors.put("password", "Password is required");
		}
		
		if(this.nick.isEmpty()) {
			errors.put("nick", "Nick is required");
		} 
		
		if(user == null && errors.size() == 0) {
			errors.put("exists", "Invalid username or password");
		}
	}
	
	/**
	 * Prepares data for validation
	 * @param s - string to prepare
	 * @return prepared string
	 */
	private String prepare(String s) {
		if(s==null) return "";
		return s.trim();
	}
	
	/**
	 * Returns hashed password
	 * @return hashed password
	 */
	public String getPasswordHash() {
		return password;
	}
	
	/**
	 * Returns blog user
	 * @return blog user
	 */
	public BlogUser getBlogUser() {
		return user;
	}

	/**
	 * Returns errors
	 * @return errors
	 */
	public Map<String, String> getErrors() {
		return errors;
	}
	
}
