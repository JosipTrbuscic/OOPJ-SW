package hr.fer.zemris.java.hw05.db;

/**
 * Exception thrown by {@link QueryLexer} if 
 * query is lexicographically invalid
 * @author Josip Trbuscic
 *
 */
public class QueryLexerException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an {@code QueryLexerException} with no detail message.
	 */
	public QueryLexerException() {

	}

	/**
	 * Constructs an {@code QueryLexerException} with the specified detail message.
	 *
	 * @param message
	 *            the detail message
	 */
	public QueryLexerException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified cause
	 * 
	 * @param cause
	 *            - the cause
	 */
	public QueryLexerException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * 
	 * @param message
	 *            - the detailed message
	 * @param cause
	 *            - the cause
	 */
	public QueryLexerException(String message, Throwable cause) {
		super(message, cause);
	}
}
