package hr.fer.zemris.java.hw05.db;

/**
 * Implementing this interface allows
 * writing simple getters for 
 * {@link StudentRecord} attributes
 * @author Josip Trbuscic
 *
 */
public interface IFieldValueGetter {
	
	/**
	 * Returns one of student record's
	 * attribute
	 * @param record - student record
	 * @return student record attribute
	 */
	public String get(StudentRecord record);
}
