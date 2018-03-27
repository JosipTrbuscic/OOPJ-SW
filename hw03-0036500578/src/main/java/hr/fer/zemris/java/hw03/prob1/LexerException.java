package hr.fer.zemris.java.hw03.prob1;

public class LexerException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an {@code LexerException} with no detail message.
	 */
	public LexerException() {

	}

	/**
	 * Constructs an {@code LexerException} with the specified detail message.
	 *
	 * @param message
	 *            the detail message
	 */
	public LexerException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified cause
	 * 
	 * @param cause
	 *            - the cause
	 */
	public LexerException(Throwable cause) {
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
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}
}
