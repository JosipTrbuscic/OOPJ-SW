package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command used to push directory path on the stack
 * @author Josip Trbuscic
 *
 */
public class Pushd implements ShellCommand{
	/**
	 * Command name
	 */
	private String name;
	
	/**
	 * Command description
	 */
	private List<String> description;
	
	/**
	 * Constructs new pushd command
	 */
	public Pushd() {
		name = "pushd";
		description = Arrays.asList("Pushes current/working directory on the stack,",
									"and changes current/working directory");
	}
	
	/**
	 * Checks if given path represents existing directory 
	 * and pushes it on top of the stack
	 * @param env - shell environment
	 * @param arguments - directory path
	 * @return Status of the shell
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length() == 0) {
			env.writeln("Invalid command arguments");
			return ShellStatus.CONTINUE;
		}
		
		File file = env.getCurrentDirectory().resolve(CommandsUtil.extractPath(arguments)).toFile();
		if(!file.isDirectory()) {
			env.writeln("Invalid directory path");
			return ShellStatus.CONTINUE;
		}
		
		if(env.getSharedData("cdstack") == null) {
			env.setSharedData("cdstack", new ArrayList<Path>());
		}
		List<Path> stack = (List<Path>) env.getSharedData("cdstack");
		
		stack.add(file.toPath());
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
