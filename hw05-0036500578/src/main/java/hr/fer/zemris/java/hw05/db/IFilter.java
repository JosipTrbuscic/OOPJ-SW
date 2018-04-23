package hr.fer.zemris.java.hw05.db;

/**
 * Implementing this interface allows filtering 
 * student records from database
 * @author Josip Trbuscic
 */
public interface IFilter {
	
	/**
	 * Returns true if student record satisfies
	 * condition
	 * @param record - student record
	 * @return {@code true} if student record satisfies
	 *			 condition
	 */
	public boolean accepts(StudentRecord record);
}
