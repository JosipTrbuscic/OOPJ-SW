package hr.fer.zemris.java.custom.scripting.lexer;
/**
 * Valid token types
 * @author Josip Trbuscic
 *
 */
public enum TokenType {

	EOF, //End of file type
	SYMBOL, //Symbol type
	SOT, //Start of tag type
	EOT, //End of tag type
	TEXT, //Text type
	VARIABLE, //Variable type
	INTEGER, //Integer type 
	DOUBLE, //Double type
	OPERATOR, //Operator type
	FUNCTION; //Function type
	
}
