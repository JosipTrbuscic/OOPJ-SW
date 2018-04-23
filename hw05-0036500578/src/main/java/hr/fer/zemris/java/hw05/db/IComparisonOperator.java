package hr.fer.zemris.java.hw05.db;

/**
 * Implementing this interface allows comparing 
 * two strings
 * @author Josip Trbuscic
 */
public interface IComparisonOperator {
	
	/**
	 * Returns true if condition is
	 * satisfied 
	 * @param value1 - string to compare
	 * @param value2  - string literal
	 * @return {@code true} if condition is satisfied 
	 */
	public boolean satisfied(String value1, String value2);
}
