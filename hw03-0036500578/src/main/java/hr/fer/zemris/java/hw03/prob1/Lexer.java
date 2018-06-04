package hr.fer.zemris.java.hw03.prob1;

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
		state = LexerState.BASIC;
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

		removeBlanks();
		
		if (currentIndex == data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}


		if (state.equals(LexerState.EXTENDED)) {
			if(data[currentIndex]=='#') {
				currentIndex++;
				return new Token(TokenType.SYMBOL, Character.valueOf('#'));
			}
			StringBuilder sb = new StringBuilder();

			while (currentIndex < data.length) {
				char c = data[currentIndex];
				
				if (c == ' ' || c == '#') {
					return new Token(TokenType.WORD, sb.toString());
				}  else {
					sb.append(c);
				}
				
				currentIndex++;
			}

		}

		
		if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
			token = wordToken();

			return token;
		} else if (Character.isDigit(data[currentIndex])) {
			token = numberToken();

			return token;

		} else {
			token = new Token(TokenType.SYMBOL, Character.valueOf(data[currentIndex]));
			currentIndex++;

			return token;

		}

	}
	
	/**
	 * Returns last found token
	 * @return last found token
	 */
	public Token getToken() {
		return token;
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
	
	/**
	 * Sets lexer's state to the specified one
	 * @param state to be set
	 */
	public void setState(LexerState state) {
		if(state == null) throw new NullPointerException("Lexer state cannot be null");
		this.state = state;
	}

	/**
	 * Private method that returns next token which
	 * represents sequence of letters.
	 * @return letter sequence token 
	 * @throws LexerException if sequence is not valid word
	 */
	private Token wordToken() {
		StringBuilder word = new StringBuilder();
		boolean escape = false;

		while (currentIndex < data.length
				&& (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\' || escape)) {

			char c = data[currentIndex];

			if (Character.isLetter(c) && !escape) {
				word.append(c);
			} else if (c == '\\' && !escape) {
				escape = true;

			} else if (escape && (Character.isDigit(c) || c == '\\')) {
				word.append(c);
				escape = false;

			} else {
				break;
			}

			currentIndex++;
		}

		if (escape)
			throw new LexerException("Invalid letter sequence");
		return new Token(TokenType.WORD, word.toString());
	}
	
	/**
	 * Private method that returns next token which
	 * represents sequence of digits.
	 * @return number token 
	 * @throws LexerException if sequence does not represent number
	 */
	private Token numberToken() {
		long result = 0;

		while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {

			result = result * 10 + Character.getNumericValue(data[currentIndex]);
			if (result < 0) {
				throw new LexerException("Invalid digit sequence");
			}
			currentIndex++;
		}

		return new Token(TokenType.NUMBER, result);
	}
}
