package hr.fer.zemris.java.model;

import java.util.Comparator;

/**
 * Represents entry in PollOptions table and contains 
 * option id, title, link, number of votes and 
 * associated poll's id 
 * @author Josip Trbuscic
 *
 */
public class PollOption {
	
	/**
	 * Option id
	 */
	private int id;
	
	/**
	 * Option title
	 */
	private String optionTitle;
	
	/**
	 * Option link
	 */
	private String optionLink;
	
	/**
	 * ID of poll associated with this option
	 */
	private int pollID;
	
	/**
	 * Number of votes
	 */
	private int votesCount;
	
	/**
	 * Option comparator
	 */
	public static final Comparator<PollOption> OPTIONS_COMPARATOR = (o1,o2) -> Integer.compare(o2.votesCount, o1.votesCount);
	
	/**
	 * Constructor
	 * @param id - option id
	 * @param optionTitle - option title
	 * @param optionLink - option link
	 * @param pollID - id of poll associated with this option
	 * @param votesCount - number of votes
	 */
	public PollOption(int id, String optionTitle, String optionLink, int pollID, int votesCount) {
		this.id = id;
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.pollID = pollID;
		this.votesCount = votesCount;
	}

	/**
	 * Returns option id
	 * @return option id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns option title
	 * @return option title
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Returns option link
	 * @return option link
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Returns id of poll associated with this option
	 * @return id of poll associated with this option
	 */
	public int getPollID() {
		return pollID;
	}

	/**
	 * Returns number of votes
	 * @return number of votes
	 */
	public int getVotesCount() {
		return votesCount;
	}
}
