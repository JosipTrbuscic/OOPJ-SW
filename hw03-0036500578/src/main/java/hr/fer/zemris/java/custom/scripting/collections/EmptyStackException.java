package hr.fer.zemris.java.custom.scripting.collections;
/**
 * Thrown to indicate that program tried to access the element of an empty stack
 * @author Josip Trbuscic
 *
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	/**
     * Constructs an {@code EmptyStackException} with no detail message.
     */
	public EmptyStackException() {
		
	}
	 /**
     * Constructs an {@code EmptyStackException} with the specified detail
     * message.
     *
     * @param message the detail message
     */
	public EmptyStackException(String message) {
		super(message);
	}
	/**
	 * Constructs a new exception with the specified cause
	 * @param cause - the cause
	 */
	public EmptyStackException(Throwable cause) {
		super(cause);
	}
	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * @param message - the detailed message
	 * @param cause - the cause
	 */
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}
}
