package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command used to remove path from the top of the stack
 * and set the directory represented by the path as 
 * current/working directory
 * @author Josip Trbuscic
 *
 */
public class Popd implements ShellCommand{
	/**
	 * Command name
	 */
	private String name;
	
	/**
	 * Command description
	 */
	private List<String> description;
	
	/**
	 * Constructs new popd command
	 */
	public Popd() {
		name = "popd";
		description = Arrays.asList("Removes the directory from the stack and sets it as the "
									+ "current/working directory");
	}
	
	/**
	 * Removes path from the top of the stack
	 * and set the directory represented by the path as 
	 * current/working directory
	 * @param env - shell environment
	 * @param arguments - command arguments. Not used
	 * 						by this command
	 * @return Status of the shell
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length() > 0) {
			env.writeln("Popd command accepts no arguments");
		}
		
		@SuppressWarnings("unchecked")
		List<Path> stack = (List<Path>) env.getSharedData("cdstack");
		
		if(stack == null) {
			env.writeln("No paths stored on stack");
			return ShellStatus.CONTINUE;
		}
		
		File file = stack.get(stack.size()-1).toFile();
		stack.remove(stack.size()-1);
		
		if(file.exists()) {
			env.setCurrentDirectory(file.toPath());
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
