package hr.fer.zemris.java.custom.scripting.lexer;

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

		if (checkTagEnd()) {
			currentIndex += 2;
			return new Token(TokenType.EOT, new String("$}"));
			
		} else if (c == '@' && currentIndex + 1 < data.length && Character.isLetter(data[currentIndex + 1])) {
			currentIndex++;
			String ident = getIdent();
			return new Token(TokenType.FUNCTION, "@" + ident);
		} else if (c == '"') {
			return new Token(TokenType.TEXT,getTagString());
			
		} else if (Character.isLetter(c)) {
			String ident = getIdent();
			return  new Token(TokenType.VARIABLE, ident);
			
		} else if (Character.isDigit(c) ) {
				return getNumberToken();
		}else if(c == '-'){
			if(currentIndex+1 < data.length && Character.isDigit(data[currentIndex+1])) {
				return getNumberToken();
			}else {
				return new Token(TokenType.OPERATOR, new String("-"));
			}
		}else if (isOperator()) {
			Character operator = Character.valueOf(data[currentIndex++]);
			return new Token(TokenType.OPERATOR, new String(operator.toString()));
			
		} else {
			Character symbol = Character.valueOf(data[currentIndex++]);
			return new Token(TokenType.SYMBOL, new String(symbol.toString()));
		}
				
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
	
	private boolean tagStart() {
		char c = data[currentIndex];
		
		if (c == '{' && currentIndex + 1 < data.length && data[currentIndex + 1] == '$') {
			return true;
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
		}

		while (currentIndex < data.length) {
			char c = data[currentIndex];

			if (c == ' ' || c == '"' || c == '$') {
				String s = new String(data, startIndex, currentIndex - startIndex);

				if (decimal) {
					return new Token(TokenType.DOUBLE, prefix*Double.parseDouble(s));
					
				} else {
					return new Token(TokenType.INTEGER, prefix*Integer.parseInt(s));
				}

			}

			if (!decimal && c == '.') {
				decimal = true;

			} else if (!Character.isDigit(c)) {
				break;
			}
			currentIndex++;
		}
		String s = new String(data,startIndex,currentIndex-startIndex+1);
		throw new LexerException("Expected number. Got "+s);
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

		throw new LexerException("Invalid identificator");
	}

	private String getTagString() {
		boolean escape = false;
		int startIndex = currentIndex++;

		while (currentIndex < data.length) {
			char c = data[currentIndex];

			if (!escape && c == '"') {
				currentIndex++;
				return new String(data, startIndex, currentIndex - startIndex).replace("\\", "");
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
		String s = new String(data, startIndex, currentIndex - startIndex);
		throw new LexerException(s+" is invalid tag string.");
	}

	private boolean isOperator() {
		Character c = Character.valueOf(data[currentIndex]);
		String operators = "+-*^\\";
		if (operators.contains(c.toString())) {
			if(currentIndex < data.length && (data[currentIndex+1] == '$'));
			return true;
		}
		return false;
	}

	private Token getTextToken() {
		int startIndex = currentIndex;
		boolean escape = false;
		
		if(tagStart()) {
			currentIndex+=2;
			return new Token(TokenType.SOT, new String("{$"));
		}
		
		while (currentIndex < data.length) {
			char c = data[currentIndex];

			if (c == '{' && currentIndex + 1 < data.length && data[currentIndex + 1] == '$') {
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
		String s = 
				"This is sam{p$$le text."
		+ "{$ FOR i @sin \"Ovo je \\\"citat\\\"\" 1 $}\n"
				+ "This is {$= i $}-th time this message is generated.\n" + "{$END$}\n" + "{$FOR i 0 10 2 $}\n"
				+ "sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n" + "{$END$}";
		System.out.println(s);
		Lexer lex = new Lexer(s);
		System.out.println("-----------------------------");
		System.out.println(lex.nextToken().getValue() + " prvi");
		System.out.println(lex.nextToken().getValue() + " drugi");
		
//		System.out.println(lex.nextToken().getValue() + " treci");
//		System.out.println(lex.nextToken().getValue()+ " cetvrti");
//		System.out.println(lex.nextToken().getValue() + " peti");
//		System.out.println(lex.nextToken().getValue() + " sesti");
//		System.out.println(lex.nextToken().getValue() + " sedmi");
//		lex.setState(LexerState.TEXT_STATE);
//		System.out.println(lex.nextToken().getValue() + " osmi");
//		lex.setState(LexerState.TAG_STATE);
//		System.out.println(lex.nextToken().getValue() + " deveti");
//		System.out.println(lex.nextToken().getValue() + " deseti");
//		System.out.println(lex.nextToken().getValue() + " jedanaesti");
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
