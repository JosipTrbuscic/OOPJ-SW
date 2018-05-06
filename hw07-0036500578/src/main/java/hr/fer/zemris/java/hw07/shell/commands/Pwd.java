package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command used to print current/working directory
 * @author Josip Trbuscic
 *
 */
public class Pwd implements ShellCommand {
	/**
	 * Command name
	 */
	private String name;
	
	/**
	 * Command description
	 */
	private List<String> description;
	
	/**
	 * Constructs new pwd command
	 */
	public Pwd() {
		name = "pwd";
		description = Arrays.asList("print name of current/working directory");
	}
	
	/**
	 * Prints current/working directory
	 * @param env - shell environment
	 * @param arguments - command arguments. Not used by
	 * 						this command
	 * @return Status of the shell
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		env.writeln(env.getCurrentDirectory().toString());
		return ShellStatus.CONTINUE;
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
