package hr.fer.zemris.java.dao;

/**
 * Exception thrown when error in persistence layer occurs
 * @author Josip Trbuscic
 *
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DAOException() {
	}

	/**
	 * Construct DAOException with given message and cause
	 * @param message - exception message
	 * @param cause - exception cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Construct DAOException with given message
	 * @param message - exception message
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Construct DAOException with given cause
	 * @param cause - exception cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}