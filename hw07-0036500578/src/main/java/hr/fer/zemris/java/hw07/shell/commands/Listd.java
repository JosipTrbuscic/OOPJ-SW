package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command used to list all paths 
 * stored on stack
 * @author Josip Trbuscic
 *
 */
public class Listd implements ShellCommand{
	/**
	 * Command name
	 */
	private String name;
	
	/**
	 * Command dscription
	 */
	private List<String> description;

	/**
	 * Constructs new listd command
	 */
	public Listd() {
		name = "listd";
		description = Arrays.asList("Prints all paths stored on stack");
	}
	
	/**
	 * Prints all paths stored on the stack
	 * @param env - shell environment
	 * @param arguments - command arguments. Not used by this command
	 * @return Status of the shell
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		@SuppressWarnings("unchecked")
		List<Path> stack = (List<Path>) env.getSharedData("cdstack");
		
		if(stack == null || stack.isEmpty()) {
			env.writeln("No stored paths");
			return ShellStatus.CONTINUE;
		}
		
		for(int i = stack.size()-1; i>=0; i--) {
			env.writeln(stack.get(i).toString());
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
