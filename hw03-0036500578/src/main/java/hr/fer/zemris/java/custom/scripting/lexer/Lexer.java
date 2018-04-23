package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.hw03.prob1.LexerException;

/**
 * Simple lexer used to generate tokens from given string. Lexer will analize
 * string reading character by character and group them accordingly.
 * @author Josip Trbuscic
 *
 */
public class Lexer {
	private char[] data; //Array where string is stored.
	private Token token; //Token that was last read
	private int currentIndex; //Current position in array
	private LexerState state; //State of lexer

	/**
	 * Constructs new {@code Lexer} with given string
	 * that will be processed
	 * @param text - String to process
	 */
	public Lexer(String text) {
		if (text == null)
			throw new IllegalArgumentException("Null cannot be processed by lexer");

		data = text.toCharArray();
		currentIndex = 0;
		state = LexerState.TEXT_STATE;
	}

	/**
	 * Extracts next token from source string  
	 * @return next found token
	 * @throws LexerException if character sequence doesn't
	 * 			represent valid token
	 */
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
	/**
	 * @return previous token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Sets lexer state
	 * @param state - lexer state
	 */
	public void setState(LexerState state) {
		if (state == null)
			throw new NullPointerException("Must specify valid lexer state");
		this.state = state;
	}
	
	/**
	 * Processes character sequence in {@code TAG_STATE} lexer state and returns next
	 * valid token.
	 * @return next valid token
	 * @throws LexerException if character sequence doesn't
	 * 			represent valid token
	 */
	private Token getTagToken() {
		removeBlanks();

		char c = data[currentIndex];

		if (checkTagEnd()) {
			currentIndex += 2;
			return new Token(TokenType.EOT, new String("$}"));
			
		} else if (c == '@' && currentIndex + 1 < data.length && Character.isLetter(data[currentIndex + 1])) {
			currentIndex++;
			String ident = getVariable();
			return new Token(TokenType.FUNCTION, "@" + ident);
		} else if (c == '"') {
			return new Token(TokenType.TEXT,getTagString());
			
		} else if (Character.isLetter(c)) {
			String ident = getVariable();
			return  new Token(TokenType.VARIABLE, ident);
			
		} else if (Character.isDigit(c) ) {
				return getNumber();
		}else if(c == '-'){
			if(currentIndex+1 < data.length && Character.isDigit(data[currentIndex+1])) {
				return getNumber();
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

	/**
	 * If next two characters in string are "&}" returns true
	 * @return true if character sequence represents
	 * 			end of {@code TAG_STATE}, false otherwise
	 */
	private boolean checkTagEnd() {
		if (data[currentIndex] == '$') {
			if ((currentIndex + 1 < data.length) && data[currentIndex + 1] == '}') {
				return true;
			}
		}
		return false;
	}
	/**
	 * If next two characters in string are "{$" returns true
	 * @return true if character sequence represents
	 * 			start of {@code TAG_STATE}, false otherwise
	 */
	private boolean tagStart() {
		char c = data[currentIndex];
		
		if (c == '{' && currentIndex + 1 < data.length && data[currentIndex + 1] == '$') {
			return true;
		}
		return false;
	}
	
	/**
	 * Private method that returns next token which
	 * represents sequence of digits.
	 * @return number token 
	 * @throws LexerException if sequence does not represent number
	 */
	private Token getNumber() {
		int startIndex = currentIndex;
		boolean decimal = false;

		if (data[currentIndex] == '-') {
			currentIndex++;
		}

		while (currentIndex < data.length) {
			char c = data[currentIndex];

			if (c == ' ' || c == '"' || c == '$') {
				String s = new String(data, startIndex, currentIndex - startIndex);

				if (decimal) {
					return new Token(TokenType.DOUBLE,Double.parseDouble(s));
					
				} else {
					return new Token(TokenType.INTEGER, Integer.parseInt(s));
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
	
	/**
	 * Private method that returns String which
	 * represents valid variable name.
	 * @return variable name 
	 * @throws LexerException if sequence does not represent 
	 *			valid variable name
	 */
	private String getVariable() {
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
	
	/**
	 * Private method that returns next character which
	 * represents valid String in {@code TAG_STATE} lexer state;
	 * @return valid string  
	 * @throws LexerException if sequence does not represent
	 *			valid string
	 */
	private String getTagString() {
		boolean escape = false;
		int startIndex = currentIndex++;

		while (currentIndex < data.length) {
			char c = data[currentIndex];

			if (!escape && c == '"') {
				currentIndex++;
				return new String(data, startIndex, currentIndex - startIndex);
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
	
	/**
	 * Returns true if next character is valid operator
	 * @return true if valid operator is found, false otherwise
	 */
	private boolean isOperator() {
		Character c = Character.valueOf(data[currentIndex]);
		String operators = "+-*^\\";
		if (operators.contains(c.toString())) {
			if(currentIndex < data.length && (data[currentIndex+1] == '$'));
			return true;
		}
		return false;
	}

	/**
	 * Private method that returns next token which
	 * represents String which contains of all string characters
	 * until start of tag or end of string is reached.
	 * @return Document text token 
	 * @throws LexerException if sequence is not valid word
	 */
	private Token getTextToken() {
		int startIndex = currentIndex;
		boolean escape = false;
		
		if(tagStart()) {
			currentIndex+=2;
			return new Token(TokenType.SOT, new String("{$"));
		}
		
		while (currentIndex < data.length) {
			char c = data[currentIndex];

			if (c == '{' && currentIndex + 1 < data.length && data[currentIndex + 1] == '$' && !escape) {
				return new Token(TokenType.TEXT, new String(data, startIndex, currentIndex - startIndex));
			}

			if (!escape && c == '\\') {
				escape = true;
				currentIndex++;
				continue;
			}

			if (escape && c != '{' && c != '\\') {
				throw new LexerException("Invalid document text");
			}

			escape = false;
			currentIndex++;
		}

		return new Token(TokenType.TEXT, new String(data, startIndex, currentIndex - startIndex));
	}

	/**
	 * Moves current index over any whitespaces, tabulators or 
	 * new line characters.
	 */
	private void removeBlanks() {

		while (currentIndex < data.length) {
			char c = data[currentIndex];

			if (c == '\r' || c == '\n' || c == '\t' || c == ' ')
				currentIndex++;
			else
				break;
		}
	}
}
