package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command used to print all available charsets
 * @author Josip Trbuscic
 *
 */
public class Charsets implements ShellCommand{
	/**
	 * Command description;
	 */
	private List<String> description;
	
	/**
	 * Command name;
	 */
	private String name;
	
	/**
	 * Constructs new charsets command
	 */
	public Charsets() {
		name = "charsets";
		description = Arrays.asList("Lists names of all supported "
									+ "charsets by this shell");
	}
	
	/**
	 * Prints all available charsets to the shell
	 * @param env - shell environment
	 * @param arguments - command arguments. This command does not 
	 * 					use arguments
	 * @return Status of the shell
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Charset.availableCharsets()
		.keySet()
		.forEach(c -> env.writeln(c.toString()));
		
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
