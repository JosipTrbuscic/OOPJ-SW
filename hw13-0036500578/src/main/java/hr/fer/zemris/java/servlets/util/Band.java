package hr.fer.zemris.java.servlets.util;

/**
 * This class represents a band. Every band is defined by unique ID and 
 * contains a name, link to their video and number of votes.
 * @author Josip Trbuscic
 *
 */
public class Band {
	/**
	 * Band ID
	 */
	Integer id;
	
	/**
	 * Band
	 */
	String name;
	
	/**
	 * Link to the band's video 
	 */
	String link;
	
	/**
	 * Number of votes
	 */
	Integer votes;

	/**
	 * Constructor
	 * @param id
	 * @param name
	 * @param link
	 */
	public Band(Integer id, String name, String link) {
		this.id = id;
		this.name = name;
		this.link = link;
	}

	/**
	 * Returns bands ID 
	 * @return bands ID
	 */
	public int getID() {
		return id;
	}

	/**
	 * Returns bands name
	 * @return bands name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the bands link to the video
	 * @return the bands link to the video
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Returns bands number of votes
	 * @return bands number of votes
	 */
	public Integer getVotes() {
		return votes;
	}

	/**
	 * Sets the bands number of votes
	 * @param votes - number of votes
	 */
	public void setVotes(Integer votes) {
		this.votes = votes;
	}

}
