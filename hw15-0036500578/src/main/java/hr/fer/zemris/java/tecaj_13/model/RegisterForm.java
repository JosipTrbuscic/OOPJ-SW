package hr.fer.zemris.java.tecaj_13.model;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;

/**
 * Form used to validate users data given on registration
 * @author Josip Trbuscic
 *
 */
public class RegisterForm {
	
	/**
	 * Users first name
	 */
	private String firstName;
	
	/**
	 * Users last name
	 */
	private String lastName;
	
	/**
	 * Users nick
	 */
	private String nick;
	
	/**
	 * Users email
	 */
	private String email;
	
	/**
	 * Users password
	 */
	private String password;
	
	private Map<String, String> errors = new HashMap<>();
	
	/**
	 * Constructor
	 */
	public RegisterForm() {
	}
	
	/**
	 * Fills the form with the data from the  request
	 * @param req - http request
	 */
	public void fillFromHttpRequest(HttpServletRequest req){
		this.firstName = prepare(req.getParameter("firstName"));
		this.lastName = prepare(req.getParameter("lastName"));
		this.nick = prepare(req.getParameter("nick"));
		this.email = prepare(req.getParameter("email"));
		this.password = prepare(req.getParameter("password"));
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
	 * Populates data of given user with data stored in this form
	 * @param user - blog user
	 */
	public void populateUser(BlogUser user) {
		user.setId(null);
		user.setFirstName(this.firstName);
		user.setLastName(this.lastName);
		user.setNick(this.nick);
		user.setEmail(this.email);
		user.setPasswordHash(EncryptionUtil.encrypt(this.password));
	}
	
	/**
	 * Validates the form and puts errors in the map if they are found
	 */
	public void validate() {
		
		errors.clear();
		
		if(this.firstName.isEmpty()) {
			errors.put("firstName", "Name is required");
		}
		
		
		if(this.lastName.isEmpty()) {
			errors.put("lastName", "Last name is required");
		}

		if(this.email.isEmpty()) {
			errors.put("email", "EMail is required!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if(l<3 || p==-1 || p==0 || p==l-1) {
				errors.put("email", "Invalid email format");
			}
		}
		
		if(this.password.isEmpty()) {
			errors.put("password", "Password is required");
		}
		
		if(this.nick.isEmpty()) {
			errors.put("nick", "Nick is required");
		} else {
			if(DAOProvider.getDAO().getUserFromNick(this.nick) != null) {
				errors.put("nickTaken", "Nick taken");
			}
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
	 * Returns users first name
	 * @return users first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns users last name
	 * @return users last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns users nick
	 * @return users nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Returns users email
	 * @return users email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns users hashed password
	 * @return users hashed password
	 */
	public String getPasswordHash() {
		return password;
	}

	/**
	 * Returns errors 
	 * @return errors
	 */
	public Map<String, String> getErrors() {
		return errors;
	}
	
}
