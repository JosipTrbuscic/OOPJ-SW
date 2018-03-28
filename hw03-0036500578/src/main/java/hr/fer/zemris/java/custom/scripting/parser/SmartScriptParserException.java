package hr.fer.zemris.java.custom.scripting.parser;

public class SmartScriptParserException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an {@code SmartScriptParserException} with no detail message.
	 */
	public SmartScriptParserException() {

	}

	/**
	 * Constructs an {@code SmartScriptParserException} with the specified detail message.
	 *
	 * @param message
	 *            the detail message
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified cause
	 * 
	 * @param cause
	 *            - the cause
	 */
	public SmartScriptParserException(Throwable cause) {
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
	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}
}
