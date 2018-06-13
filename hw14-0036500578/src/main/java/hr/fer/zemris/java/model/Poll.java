package hr.fer.zemris.java.model;

/**
 * Represents entry in Polls table and contains
 * poll id, title and display message
 * @author Josip Trbuscic
 *
 */
public class Poll {
	
	/**
	 * Poll id
	 */
	private int id;
	
	/**
	 * Poll title
	 */
	private String title;
	
	/**
	 * Poll message
	 */
	private String message;
	
	/**
	 * Constructor
	 * @param id - poll id 
	 * @param title - poll title
	 * @param message - poll message
	 */
	public Poll(int id, String title, String message) {
		this.id = id;
		this.title = title;
		this.message = message;
	}

	/**
	 * Returns poll's id
	 * @return poll's id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns poll's title
	 * @return poll's title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns poll's message
	 * @return poll's message
	 */
	public String getMessage() {
		return message;
	}
}
