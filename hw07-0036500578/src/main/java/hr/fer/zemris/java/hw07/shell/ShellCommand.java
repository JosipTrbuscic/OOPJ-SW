package hr.fer.zemris.java.hw07.shell;

import java.util.List;

/**
 * Interfaces which defines methods of a 
 * shell command 
 * @author Josip Trbuscic
 *
 */
public interface ShellCommand {
	/**
	 * Executes command in a given environment with
	 * given arguments 
	 * @param env - environment
	 * @param arguments
	 * @return Status of the shell
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Return name of the command
	 * @return command name
	 */
	String getCommandName();
	
	/**
	 * Returns command description
	 * @return command description
	 */
	List<String> getCommandDescription();
}
