package hr.fer.zemris.java.hw07.shell;

/**
 * Enumeration containing shell status.
 * This enumeration is used to check if 
 * shell should be terminated or not
 * @author Josip Trbuscic
 *
 */
public enum ShellStatus {
	
	/**
	 * Status used to indicate shell should not 
	 * be terminated
	 */
	CONTINUE,
	
	/**
	 * Status used to indicate shell should
	 * be terminated
	 */
	TERMINATE;
}
