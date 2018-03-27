package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.lexer.*;

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
		state = LexerState.TEXT_STATE;
	}

	public Token nextToken() {
		if (token != null && token.getType() == TokenType.EOF)
			throw new LexerException("No tokens available");

		removeBlanks();

		if (currentIndex == data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}

		if (data[currentIndex++] == '{') {
			if (data[currentIndex] == '$') {
				currentIndex++;
				return new Token(TokenType.TAG, new ElementString("{$"));
			}
			throw new LexerException();
		}

		if (data[currentIndex++] == '$') {
			if (data[currentIndex] == '}') {
				currentIndex++;
				return new Token(TokenType.TAG, new ElementString("{$"));
			}

			throw new LexerException();
		}

		if (state.equals(LexerState.TAG_STATE)) {
			if (data[currentIndex] == '=') {
				return new Token(TokenType.EMPTY, new ElementString("="));
			}

			if (isFor()) {
				
				return new Token(TokenType.FOR, new ElementString("FOR"));
			}
		}

		if (state.equals(LexerState.TEXT_STATE)) {
			return stringToken();
		}
		
		if(state.equals(LexerState.FOR_STATE)) {
			if(isVariable()) {
				
			}
//			while(currentIndex<data.length && data[currentIndex] != '"' &&)
		}
		return null;

	}

	public Token getToken() {
		return token;
	}

	public void setState(LexerState state) {
		if (state == null)
			throw new NullPointerException("Must specify valid lexer state");
		this.state = state;
	}

	private boolean isFor() {
		if(currentIndex > data.length-3) return false;
		
		String matcher = "for";

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 3;++i) {
			sb.append(data[currentIndex++]);
		}

		if (matcher.equals(sb.toString().toLowerCase()))
			return true;

		return false;
	}

	private Token stringToken() {
		StringBuilder sb = new StringBuilder();
		boolean escape = false;

		while (currentIndex < data.length && (!Character.valueOf(data[currentIndex]).equals('{') || escape)) {

			char c = data[currentIndex];

			if (!escape && c == '\\') {
				escape = true;
				currentIndex++;
				continue;
			}

			if (escape && c != '{' && c != '\\') {
				throw new LexerException("Invalid String");
			}
			escape = false;
			sb.append(data[currentIndex]);
			currentIndex++;
		}

		return new Token(TokenType.TEXT, new ElementString(sb.toString()));
	}

	private void removeBlanks() {

		while (currentIndex < data.length) {
			char c = data[currentIndex];

			if (c == '\r' || c == '\n' || c == '\t' || c == ' ')
				currentIndex++;
			else
				break;
		}
	}

	private boolean isVariable() { 
		
		if(Character.isLetter(data[currentIndex])) {
			
		}
		
	}

	private static boolean isString(String s) {

	}

	public static void main(String[] args) {
		 String s = "This is sam\\{ple text.\n" + "asd{$ FOR i 1 10 1 $}\n"
		 + "This is {$= i $}-th time this message is generated.\n" + "{$END$}\n" +
		 "{$FOR i 0 10 2 $}\n"
		 + "sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n" + "{$END$}";
		 System.out.println(s);
		 Lexer lex = new Lexer(s);
		
		 System.out.println(lex.nextToken().getValue().asText());
		//
		// String s2 = " asd qwe ";
		// String[] parts = s2.split("\\s+");
		// for(String part:parts) {
		// System.out.println(part);
		// }

//		String var = "32";
//		System.out.println(isVariable(var));

	}

}
