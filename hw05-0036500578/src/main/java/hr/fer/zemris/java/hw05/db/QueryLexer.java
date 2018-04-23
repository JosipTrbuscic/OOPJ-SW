package hr.fer.zemris.java.hw05.db;

/**
 * Simple lexer which offers methods for extracting parts
 * of valid conditional expression. Parts will be extracted 
 * as a string and it is expected there is a parser which 
 * will group these parts.
 * @author Josip Trbuscic
 *
 */
public class QueryLexer {
	/**
	 * Array where string is stored 
	 */
	private char[] data;
	
	/**
	 * Current position in an array
	 */
	private int currentIndex;
	
	/**
	 * Constructs new lexer with given string 
	 * which will be converted to character array
	 * @param query to analyze
	 * @throws NullPointerException if given string is null
	 */
	public QueryLexer(String query) {
		if(query == null) throw new NullPointerException("Query cannot be null");
		
		data = query.toCharArray();
		currentIndex = 0;
	}
	
	/**
	 * Extracts next attribute name and returns its
	 * string representation. 
	 * @return string representation of attribute name
	 * @throws QueryLexerException if strings ends with attribute name
	 */
	public String getNextAttributeName() {
		skipBlanks();
		int startIndex = currentIndex;
		
		while(currentIndex < data.length) {
			if(!Character.isLetter(data[currentIndex]) ||
					(data[currentIndex] == 'L' && isLIKE())) {
				break;
			}
			currentIndex++;
		}
		int endIndex = currentIndex;

		if(currentIndex == data.length) throw new QueryLexerException("Query cannot end with attribute name");
		
		return new String(data, startIndex, endIndex-startIndex);
	}
	
	/**
	 * Extracts next operator and returns its
	 * string representation. 
	 * @return string representation of an operator
	 * @throws QueryLexerException if string end with operator
	 */
	public String getNextOperator() {
		skipBlanks();
		int startIndex = currentIndex;
		
		while(currentIndex < data.length) {
			if(Character.isWhitespace(data[currentIndex]) || data[currentIndex] == '"') {
				break;
			}
			currentIndex++;
		}
		
		int endIndex = currentIndex;

		if(currentIndex == data.length) throw new QueryLexerException("Query cannot end with operator");
		
		return new String(data, startIndex, endIndex-startIndex);
	}
	
	/**
	 * Extracts next literal and returns its
	 * string representation.  
	 * @return String representation of a literal
	 * @throws QueryLexerException if string end with operator
	 */
	public String getNextLiteral() {
		skipBlanks();
		int startIndex = ++currentIndex;
		
		while(currentIndex < data.length) {
			if(data[currentIndex] == '"') {
				break;
			}
			currentIndex++;
		}
		
		int endIndex = currentIndex;
		
		if(currentIndex == data.length) throw new QueryLexerException("Literal must be closed with \"");
		currentIndex++;
		return new String(data, startIndex, endIndex-startIndex);
	}
	
	/**
	 * Extracts next logic operator and returns its
	 * string representation.  
	 * @return String representation of a literal
	 * @throws QueryLexerException if string end with operator
	 */
	public String nextLogicOperator() {
		try {
			return getNextAttributeName();
		} catch(QueryLexerException ex) {
			throw new QueryLexerException("Query cannot end with logical operator");
		}
	}
	
	/**
	 * Returns true if there are more non-whitespace
	 * characters left in a string.
	 * @return {@code true} if there are more elements
	 * 			left in a string
	 */
	public boolean hasNext() {
		skipBlanks();
		return currentIndex < data.length;
	}
	
	/**
	 * Skips over whitespace characters
	 */
	private void skipBlanks() {
		while (currentIndex < data.length) {
			char c = data[currentIndex];

			if (c == '\r' || c == '\n' || c == '\t' || c == ' ')
				currentIndex++;
			else
				break;
		}
	}
	
	/**
	 * Returns true if next four characters
	 * are equal to string "LIKE"
	 * @return true if next four characters
	 * are equal to string "LIKE", false otherwise
	 */
	private boolean isLIKE() {
		String like = "LIKE";
		int startIndex = currentIndex;
		int endIndex = startIndex + like.length();
		StringBuilder sb = new StringBuilder();
		
		while(startIndex < data.length && startIndex < endIndex) {
			sb.append(data[startIndex]);
			startIndex++;
		}
		
		return sb.toString().equals(like);
	}
	
}
