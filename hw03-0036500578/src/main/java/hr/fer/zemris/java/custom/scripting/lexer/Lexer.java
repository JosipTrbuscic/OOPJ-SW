package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
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

		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}

		if (state.equals(LexerState.TEXT_STATE)) {
			return getTextToken();
		} else {
			return getTagToken();
		}

	}

	public Token getToken() {
		return token;
	}

	public void setState(LexerState state) {
		if (state == null)
			throw new NullPointerException("Must specify valid lexer state");
		this.state = state;
	}

	private Token getTagToken() {
		removeBlanks();

		char c = data[currentIndex];

		Token token = null;

		if (checkTagEnd()) {
			currentIndex += 2;
			return new Token(TokenType.TAG, new String("$}"));
			
		} else if (c == '@') {
			if (currentIndex + 1 < data.length && Character.isLetter(data[currentIndex + 1])) {
				String ident = getIdent();
				return new Token(TokenType.FUNCTION, "@" + ident);
			}

		} else if (c == '"') {
			token = getTagString();
			
		} else if (Character.isLetter(c)) {
			String ident = getIdent();
			return  new Token(TokenType.IDENT, ident);
			
		} else if (Character.isDigit(c) || c == '-') {
			return getNumberToken();
			
		} else if (isOperator()) {
			Character operator = Character.valueOf(data[currentIndex++]);
			token = new Token(TokenType.SYMBOL, new String(operator.toString()));
			
		}else {
			while(currentIndex < data.length && c)
		}

		if (token == null) {
			throw new LexerException("Invalid tag Expression");
		}
		return token;

	}

	private boolean checkTagEnd() {
		if (data[currentIndex] == '$') {
			if ((currentIndex + 1 < data.length) && data[currentIndex + 1] == '}') {
				currentIndex++;
				return true;
			}
		}
		return false;
	}

	private Token getNumberToken() {
		int startIndex = currentIndex;
		int prefix = 1;
		boolean decimal = false;

		if (data[currentIndex] == '-') {

			prefix = -1;
			currentIndex++;
			
			if (currentIndex == data.length && !Character.isDigit(data[currentIndex]))
				return null;
		}

		while (currentIndex < data.length) {
			char c = data[currentIndex];

			if (c == ' ' || c == '"' || c == '$') {
				String s = new String(data, startIndex, currentIndex - startIndex);

				if (decimal) {
					return new Token(TokenType.DOUBLE, new ElementConstantDouble(prefix*Double.parseDouble(s)));
					
				} else {
					return new Token(TokenType.INTEGER, new ElementConstantInteger(prefix*Integer.parseInt(s)));
				}

			}

			if (!decimal && c == '.') {
				decimal = true;

			} else if (!Character.isDigit(c)) {
				String s = new String(data,startIndex,currentIndex-startIndex+1): 
				throw new LexerException("Expected number. Got "+s);
			}

			currentIndex++;
		}

		return null;

	}

	private String getIdent() {
		int startIndex = currentIndex;

		while (currentIndex < data.length) {
			char c = data[currentIndex];

			if (c == ' ' || c == '"' || c == '$') {
				return new String(data, startIndex, currentIndex - startIndex);
			}

			if (!Character.isLetter(c) && !Character.isDigit(c) && c != '_') {
				break;
			}
			currentIndex++;
		}

		currentIndex = startIndex;
		return null;
	}

	private Token getTagString() {
		boolean escape = false;
		int startIndex = currentIndex++;

		if (currentIndex == data.length && !Character.isLetter(data[currentIndex]))
			return null;

		while (currentIndex < data.length) {
			char c = data[currentIndex];

			if (!escape && c == '"') {
				currentIndex++;
				return new Token(TokenType.TEXT,
						new ElementFunction(new String(data, startIndex, currentIndex - startIndex).replace("\\", "")));
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
		if (operators.contains(c.toString())) {
			return true;
		}
		return false;
	}

	private Token getTextToken() {
		int startIndex = currentIndex;
		boolean escape = false;

		while (currentIndex < data.length) {
			char c = data[currentIndex];

			if (c == '{' && currentIndex + 1 < data.length && data[currentIndex + 1] == '$') {
				currentIndex += 2;
				return new Token(TokenType.TEXT, new String(data, startIndex, currentIndex - startIndex));
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

		return new Token(TokenType.TEXT, new String(data, startIndex, currentIndex - startIndex));
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

	public static void main(String[] args) {
		String s = "This is sam{p$$le text.\n" + "{$ FOR i @sin \"Ovo je \\\"citat\\\"\" 1 $}\n"
				+ "This is {$= i $}-th time this message is generated.\n" + "{$END$}\n" + "{$FOR i 0 10 2 $}\n"
				+ "sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n" + "{$END$}";
		System.out.println(s);
		Lexer lex = new Lexer(s);
		System.out.println("-----------------------------");
		System.out.println(lex.nextToken().getValue().asText() + " prvi");
		lex.setState(LexerState.TAG_STATE);
		System.out.println(lex.nextToken().getValue().asText() + " drugi");
		System.out.println(lex.nextToken().getValue().asText() + " treci");
		System.out.println(lex.nextToken().getValue().asText() + " cetvrti");
		System.out.println(lex.nextToken().getValue().asText() + " peti");
		System.out.println(lex.nextToken().getValue().asText() + " sesti");
		System.out.println(lex.nextToken().getValue().asText() + " sedmi");
		lex.setState(LexerState.TEXT_STATE);
		System.out.println(lex.nextToken().getValue().asText() + " osmi");
		lex.setState(LexerState.TAG_STATE);
		System.out.println(lex.nextToken().getValue().asText() + " deveti");
		System.out.println(lex.nextToken().getValue().asText() + " deseti");
		System.out.println(lex.nextToken().getValue().asText() + " jedanaesti");
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
