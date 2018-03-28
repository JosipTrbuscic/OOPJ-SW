package hr.fer.zemris.java.hw03.prob1;
/**
 * This class represents language processor's token generated by lexer. 
 * Token is specified by its type and value. 
 * @author Josip Trbuscic
 *
 */
public class Token {
	private TokenType type; 
	private Object value;
	
	/**
	 * Constructs new token with given TokenType and Object
	 * @param type of token
	 * @param value of token
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
		
	}
	
	/**
	 * Returns token value
	 */
	public Object getValue() {
		
		return value;
	}
	
	/**
	 * Returns token type
	 */
	public TokenType getType() {
		return type;
		
	}
}
