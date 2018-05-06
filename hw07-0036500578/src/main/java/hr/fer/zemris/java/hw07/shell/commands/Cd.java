package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command used to change current/working directory
 * of the shell. Directory can be given as 
 * absolute or relative path
 * @author Josip Trbuscic
 *
 */
public class Cd implements ShellCommand {
	/**
	 * Command name
	 */
	private String name;
	
	/**
	 * Command description
	 */
	private List<String> description;
	
	/**
	 * Constructs new cd command
	 */
	public Cd() {
		name = "cd";
		description = Arrays.asList("Change the shell working directory");
	}
	
	/**
	 * Changes the current/working directory of the shell to 
	 * the directory whose path will be parsed from given argument
	 * @param env - shell environment
	 * @param arguments - argument of cd command
	 * @return Status of the shell
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length() == 0) {
			env.writeln("Invalid command arguments");
			return ShellStatus.CONTINUE;
		}
		
		Path path = Paths.get(CommandsUtil.extractPath(arguments));

		try {
			env.setCurrentDirectory(env.getCurrentDirectory().resolve(path));
		} catch(IllegalArgumentException e){
			env.writeln("Invalid directory path");
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
