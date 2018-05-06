package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command used to get informations
 * about other commands
 * @author Josip Trbuscic
 *
 */
public class Help implements ShellCommand{
	
	/**
	 * Command Name;
	 */
	private String name;
	
	/**
	 * Command description
	 */
	private List<String> description;

	/**
	 * Constructs new help command
	 */
	public Help() {
		name = "help";
		description = Arrays.asList("Prints command description");
	}
	
	/**
	 * Prints description of the command whose name is given 
	 * as argument. If no arguments are given print description
	 * of every shell command
	 * @param env - shell environment
	 * @param arguments - command arguments. Not used in this command
	 * @return Status of the shell
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] parts = arguments.split("\\s+");

		if(parts.length > 1) {
			env.writeln("Invalid number of arguments");
			return ShellStatus.CONTINUE;
		}
		
		if(parts[0].isEmpty()) {
			for(ShellCommand command : env.commands().values()) {
				printCommand(command, env);
			}
			return ShellStatus.CONTINUE;
		}
		
		ShellCommand command = env.commands().get(arguments);
		if(command == null) {
			env.writeln("Command "+arguments+" does not exist");
		}
		
		printCommand(command, env);
		
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
	
	/**
	 * Prints command name and description
	 * @param command - command to be printed
	 * @param env - shell environment
	 */
	public static void printCommand(ShellCommand command, Environment env) {
		env.writeln(command.getCommandName());
		
		for(String line : command.getCommandDescription()) {
			env.writeln(line);
		}
	}
	
}
