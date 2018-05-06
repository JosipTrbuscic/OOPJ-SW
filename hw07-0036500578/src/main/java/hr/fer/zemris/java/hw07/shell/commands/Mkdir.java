package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command used to create the directory
 * or directories, if they do not already exist.
 * @author Josip Trbuscic
 *
 */
public class Mkdir implements ShellCommand{
	/**
	 * Command name
	 */
	private String name;
	
	/**
	 * Command description
	 */
	private List<String> description;

	
	/**
	 * Construct new mkdir command
	 */
	public Mkdir() {
		name = "mkdir";
		description = Arrays.asList("Creates directory structure");
	}
	
	/**
	 * Creates new directory and its parents if needed
	 * @param env - shell environment
	 * @param arguments - directory path
	 * @return Status of the shell
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String dirString = CommandsUtil.extractPath(arguments);

		if (dirString == null) {
			env.writeln("Invalid ls command argument");
		}

		File dirFile = env.getCurrentDirectory().resolve(dirString).toFile();

		boolean created = dirFile.mkdirs();
		if(!created) {
			env.writeln("Could not create directory");
		}
		
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
