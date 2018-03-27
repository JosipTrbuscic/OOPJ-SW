package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
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

		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}

		if (state.equals(LexerState.TAG_STATE)) {
			char c = data[currentIndex];
			
			Token token=null;
			if (c == '=') {
				return new Token(TokenType.EMPTY, new ElementString("="));

			} else if (isFor()) {
				return new Token(TokenType.FOR, new ElementString("FOR"));

			} else if (isEnd()) {
				return new Token(TokenType.END, new ElementString("END"));

			} else if (c == '@') {
				token = getFunction();
			} else if (c == '"') {
				token = getTagString();
			}else if (Character.isLetter(c)) {
				token = getVariable();
			}else if (Character.isDigit(c) || c == '-') {
				
			}else if (isOperator()) {
				Character operator = Character.valueOf(data[currentIndex++]);
				return new Token(TokenType.OPERATOR, new ElementOperator(operator.toString()));
			}
			
			if(token == null) {
				throw new LexerException("Invalid tag Expression");
			}
			return token;
		}

		if (state.equals(LexerState.TEXT_STATE)) {
			return documentText();
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
	
	private Token getFunction() {
		int startIndex = currentIndex++;
		
		if(currentIndex == data.length && !Character.isLetter(data[currentIndex])) return null;
		
		while(currentIndex<data.length) {
			char c = data[currentIndex];
			
			if(c == ' ' || c =='"' || c == '$') {
				return new Token(TokenType.FUNCTION, new ElementFunction(new String(data,startIndex,currentIndex-startIndex)));
			}
			
			if(!Character.isLetter(c) || !Character.isDigit(c) || c != '_') {
				break;
			}
			currentIndex++;
		}
		
		currentIndex = startIndex;
		return null;
	}
	
	private Token getTagString() {
		boolean escape=false;
		int startIndex = currentIndex++;
		
		if(currentIndex == data.length && !Character.isLetter(data[currentIndex])) return null;
		
		while(currentIndex<data.length) {
			char c = data[currentIndex];
			
			if(!escape && c == '"') {
				return new Token(TokenType.TEXT, new ElementFunction(new String(data,startIndex,currentIndex-startIndex)));
			}
			
			if (!escape && c == '\\') {
				escape = true;
				currentIndex++;
				continue;
			}
	
			if (escape && c != '"' && c != '\\') {
				break;
			}
	
			escape = false;
			currentIndex++;
		}
		currentIndex = startIndex;
		return null;
	}
	
	private Token getVariable() {
		
	}

	private boolean isFor() {
		if (currentIndex > data.length - 3)
			return false;
		int temp = currentIndex;

		String matcher = "for";

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 3; ++i) {
			sb.append(data[temp++]);
		}

		if (matcher.equals(sb.toString().toLowerCase())) {
			currentIndex += 3;
			return true;
		}

		return false;
	}

	private boolean isEnd() {
		if (currentIndex > data.length - 3)
			return false;

		int temp = currentIndex;
		String matcher = "end";

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 3; ++i) {
			sb.append(data[temp++]);
		}

		if (matcher.equals(sb.toString().toLowerCase())) {
			currentIndex += 3;
			return true;
		}

		return false;
	}
	
	private boolean isOperator() {
		Character c = Character.valueOf(data[currentIndex]);
		String operators = "+-*^\\";
		if(operators.contains(c.toString())) {
			return true;
		}
		return false;
	}

	private Token documentText() {

		int startIndex = currentIndex;
		boolean escape = false;

		while (currentIndex < data.length) {
			char c = data[currentIndex];

			if (c == '$') {
				int endIndex = currentIndex;
				String value = new String(data, startIndex, endIndex - startIndex + 1);

				if (value.contains("{$")) {
					currentIndex++;
					return new Token(TokenType.TEXT,
							new ElementString(new String(data, startIndex, endIndex - startIndex - 1).trim()));
				}
			}

			if (!escape && c == '\\') {
				escape = true;
				currentIndex++;
				continue;
			}

			if (escape && c != '{' && c != '\\') {
				throw new LexerException("Invalid String");
			}

			escape = false;
			currentIndex++;
		}

		return new Token(TokenType.TEXT,
				new ElementString(new String(data, startIndex, currentIndex - startIndex).trim()));
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

		if (Character.isLetter(data[currentIndex])) {

		}
		return false;
	}

	private static boolean isString(String s) {
		return false;
	}

	public static void main(String[] args) {
		String s = "This is sam{p$$le text.\n" + "asd            {$ FOR i 1 10 1 $}\n"
				+ "This is {$= i $}-th time this message is generated.\n" + "{$END$}\n" + "{$FOR i 0 10 2 $}\n"
				+ "sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n" + "{$END$}";
		System.out.println(s);
		Lexer lex = new Lexer(s);

		System.out.println(lex.nextToken().getType() + "kraj");
		//
		// String s2 = " asd qwe ";
		// String[] parts = s2.split("\\s+");
		// for(String part:parts) {
		// System.out.println(part);
		// }

		// String var = "32";
		// System.out.println(isVariable(var));

	}

}
