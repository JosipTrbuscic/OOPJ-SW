package hr.fer.zemris.java.hw03.prob1;


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

	public Token getToken() {
		return token;
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

	public void setState(LexerState state) {
		if(state == null) throw new IllegalArgumentException();
		this.state = state;
	}

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
			throw new LexerException();
		return new Token(TokenType.WORD, word.toString());
	}

	private Token numberToken() {
		long result = 0;

		while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {

			result = result * 10 + Character.getNumericValue(data[currentIndex]);
			if (result < 0) {
				throw new LexerException("Invalid string");
			}
			currentIndex++;
		}

		return new Token(TokenType.NUMBER, result);
	}
}
