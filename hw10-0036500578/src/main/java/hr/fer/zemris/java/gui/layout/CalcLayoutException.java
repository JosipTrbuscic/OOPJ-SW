package hr.fer.zemris.java.gui.layout;

/**
 * Exception thrown by {@link CalcLayout} when action which is not 
 * allowed is performed
 * @author Josip Trbuscic
 *
 */
public class CalcLayoutException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an {@code CalcLayoutException} with no detail message.
	 */
	public CalcLayoutException() {

	}

	/**
	 * Constructs an {@code CalcLayoutException} with the specified detail message.
	 *
	 * @param message
	 *            the detail message
	 */
	public CalcLayoutException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified cause
	 * 
	 * @param cause
	 *            - the cause
	 */
	public CalcLayoutException(Throwable cause) {
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
	public CalcLayoutException(String message, Throwable cause) {
		super(message, cause);
	}
}
