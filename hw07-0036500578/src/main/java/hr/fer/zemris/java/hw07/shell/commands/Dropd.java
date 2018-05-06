package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command used to remove directory path from
 * the top of the stack
 * @author Josip Trbuscic
 *
 */
public class Dropd implements ShellCommand{
	
	/**
	 * Command name
	 */
	private String name;
	
	/**
	 * Command description
	 */
	private List<String> description;
	
	/**
	 * Constructs new dropd command
	 */
	public Dropd() {
		name = "dropd";
		description = Arrays.asList("Removes the path from the top of the stack");
	}
	
	/**
	 * Removes path from the top of the stack
	 * @param env - shell environment
	 * @param arguments - command arguments. Not used in this command
	 * @return Status of the shell
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		@SuppressWarnings("unchecked")
		List<Path> stack = (List<Path>) env.getSharedData("cdstack");
		
		if(stack == null) {
			env.writeln("Stack is empty");
			return ShellStatus.CONTINUE;
		}
		
		stack.remove(stack.size()-1);
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
