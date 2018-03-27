package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.hw03.prob1.Token;

public class Lexer {
	private char[] data;
	private Token token;
	private int currentIndex;
	private LexerState state;
	
	public Lexer(String text) {
		if (text == null)
			throw new IllegalArgumentException("Null cannot be processed by lexer");

		data = text.toCharArray();
		currentIndex = 0;
		state = LexerState.BASIC;
	}
	
	public Token nextToken() {
		return null;
	}
	
	public Token getToken() {
		return token;
	}
	
	

}
