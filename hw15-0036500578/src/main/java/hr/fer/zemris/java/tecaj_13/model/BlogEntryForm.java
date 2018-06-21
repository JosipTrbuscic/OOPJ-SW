package hr.fer.zemris.java.tecaj_13.model;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Data structure used to validate blog entry. 
 * @author Josip Trbuscic
 *
 */
public class BlogEntryForm {
	
	/**
	 * title of the blog entry
	 */
	private String title;
	
	/**
	 * Text blog entry contains
	 */
	private String text;
	
	/**
	 * Map of errors
	 */
	private Map<String, String> errors = new HashMap<>();
	
	/**
	 * Default constructor
	 */
	public BlogEntryForm() {
	}
	
	/**
	 * Fills the form with the data from the  request
	 * @param req - http request
	 */
	public void fillFromHttpRequest(HttpServletRequest req){
		this.title = prepare(req.getParameter("title"));
		this.text = prepare(req.getParameter("text"));

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
		
		if(this.title.isEmpty()) {
			errors.put("title", "Title must not be emtpy");
		}
		
		if(this.text.isEmpty()) {
			errors.put("text", "Text must not be empty");
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
	 * Returns blog entry title
	 * @return blog entry title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns blog entry text
	 * @return blog entry text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Returns map of errors
	 * @return map of errors
	 */
	public Map<String, String> getErrors() {
		return errors;
	}
	
}
