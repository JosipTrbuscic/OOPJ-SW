package hr.fer.zemris.java.hw05.db;

public class QueryParserException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an {@code QueryParserException} with no detail message.
	 */
	public QueryParserException() {

	}

	/**
	 * Constructs an {@code QueryParserException} with the specified detail message.
	 *
	 * @param message
	 *            the detail message
	 */
	public QueryParserException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified cause
	 * 
	 * @param cause
	 *            - the cause
	 */
	public QueryParserException(Throwable cause) {
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
	public QueryParserException(String message, Throwable cause) {
		super(message, cause);
	}
}
