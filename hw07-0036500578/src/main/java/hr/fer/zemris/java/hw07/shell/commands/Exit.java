package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command which exist current shell
 * @author Josip Trbuscic
 *
 */
public class Exit implements ShellCommand{
	/**
	 * Command Name;
	 */
	private String name;
	
	/**
	 * Command description
	 */
	private List<String> description;

	/**
	 * Constructs new exit command
	 */
	public Exit() {
		name = "exit";
		description = Arrays.asList("Exits the shell");
	}

	/**
	 * Exits the shell
	 * @param env - shell environment
	 * @param arguments - command arguments. Not used in this command
	 * @return Status of the shell
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}

	/**
	 * Returns name of the command
	 * @return name of the command
	 */
	@Override
	public String getCommandName() {
		return name;
	}

	/**
	 * Returns description of the command
	 * @return description of the command
	 */
	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);

	}
}
