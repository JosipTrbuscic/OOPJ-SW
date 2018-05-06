package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command used to recursively delete directory and 
 * all of its content
 * @author Josip Trbuscic
 *
 */
public class Rmtree implements ShellCommand{
	/**
	 * Command name
	 */
	private String name;
	
	/**
	 * Command description
	 */
	private List<String> description;
	
	/**
	 * Constructs new rmtree command
	 */
	public Rmtree() {
		name = "rmtree";
		description = Arrays.asList("Removes contents of the directory recursively");
	}
	
	/**
	 * Recursively deletes directory and 
	 * all of its content
	 * @param env - shell environment
	 * @param arguments - directory path
	 * @return Status of the shell
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {		
		String pathString = CommandsUtil.extractPath(arguments);
		
		if(pathString == null) {
			env.writeln("Invalid command argument");
			return ShellStatus.CONTINUE;
		}
		
		Path path = Paths.get(pathString);
		path = env.getCurrentDirectory().resolve(path);
		
		if(!path.toFile().isDirectory()) {
			env.writeln("Invalid directory path");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Files.walkFileTree(path.normalize(), new RmtreeFileVisitor());
		} catch (IOException e) {
			env.writeln("Error occured in process of deleting files");
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
	
	/**
	 * File visitor class which offers methods to recursively
	 * walk directory tree and delete its content
	 * @author Josip Trbuscic
	 *
	 */
	private class RmtreeFileVisitor extends SimpleFileVisitor<Path>{
		
		/**
		 * Deletes file
		 */
		@Override
	    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
	        throws IOException
		{
	        Objects.requireNonNull(file);
	        Objects.requireNonNull(attrs);
	        
	        Files.delete(file);
	        return FileVisitResult.CONTINUE;
	    }
		
		/**
		 * Deletes directory
		 */
		@Override
	    public FileVisitResult postVisitDirectory(Path dir, IOException exc)
	        throws IOException
	    {
	        Objects.requireNonNull(dir);
	        Files.delete(dir);
	        return FileVisitResult.CONTINUE;
	    }
		
	}

}
