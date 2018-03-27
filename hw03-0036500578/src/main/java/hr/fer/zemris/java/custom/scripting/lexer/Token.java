package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.elems.Element;

public class Token {
	private TokenType type;
	private Element value;
	
	public Token(TokenType type, Element value) {
		this.type = type;
		this.value = value;
		
	}
	
	public Element getValue() {
		
		return value;
	}
	
	public TokenType getType() {
		return type;
		
	}
}
