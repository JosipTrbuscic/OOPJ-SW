package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command used to recursively list contents of directory and 
 * its children 
 * @author Josip Trbuscic
 *
 */
public class Tree implements ShellCommand{
	
	/**
	 * Command name
	 */
	private String name;
	
	/**
	 * Command description
	 */
	private List<String> description;
	
	/**
	 * Constructs new tree command
	 */
	public Tree() {
		name = "tree";
		description = Arrays.asList("Recursively lists directory tree "
							+ "and produces depth indented listing of files");
	}
	
	/**
	 * Recursively prints contents of directory and 
	 * its children 
	 * @param env - shell environment
	 * @param arguments - shell symbol name
	 * @return Status of the shell
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String dirString = CommandsUtil.extractPath(arguments);

		if (dirString == null) {
			env.writeln("Invalid ls command argument");
		}

		File dirFile = env.getCurrentDirectory().resolve(dirString).toFile();

		if (!dirFile.isDirectory()) {
			env.writeln("Given path does not represent a valid directory");
			return ShellStatus.CONTINUE;
		}
		
		list(dirFile, 0, env);
		
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
	 * Helper method used to recursively print contents 
	 * of a directory and its children
	 * @param dir - parent directory
	 * @param tab - number of indents
	 * @param env - shell 
	 */
	private static void list(File dir, int tab, Environment env) {
		File[] files = dir.listFiles();
		for(File file : files) {
			for(int i = 0; i<tab;i++) {
				env.write("  ");
			}
			if(file.isDirectory()) {
				env.writeln(file.getName());
				list(file, tab+1, env);
			} else {
				env.writeln(file.getName());
			}
		}
	}
	
}
