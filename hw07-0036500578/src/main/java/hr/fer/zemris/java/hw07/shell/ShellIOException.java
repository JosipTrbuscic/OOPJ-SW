package hr.fer.zemris.java.hw07.shell;

/**
 * Exception thrown by the shell when reading or writing 
 * to the standard input fails.
 * @author Josip Trbuscic
 *
 */
public class ShellIOException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an {@code ShellIOException} with no detail message.
	 */
	public ShellIOException() {

	}

	/**
	 * Constructs an {@code ShellIOException} with the specified detail message.
	 *
	 * @param message
	 *            the detail message
	 */
	public ShellIOException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified cause
	 * 
	 * @param cause
	 *            - the cause
	 */
	public ShellIOException(Throwable cause) {
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
	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}
}
