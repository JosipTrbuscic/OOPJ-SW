package hr.fer.zemris.java.custom.scripting.parser;
/**
 * Valid token types
 * @author Josip Trbuscic
 *
 */
public enum TokenType {

	/**
	 * End of file type
	 */
	EOF,
	
	/**
	 * Symbol type
	 */
	SYMBOL,
	
	/**
	 * Start of tag type
	 */
	SOT,
	
	/**
	 * End of tag type
	 */
	EOT,
	
	/**
	 * Text type
	 */
	TEXT,
	
	/**
	 * Variable type
	 */
	VARIABLE,
	
	/**
	 * Integer type
	 */
	INTEGER,
	
	/**
	 * Double type
	 */
	DOUBLE,
	
	/**
	 * Operator type
	 */
	OPERATOR,
	
	/**
	 *Function type
	 */
	FUNCTION;
	
}
