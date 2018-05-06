package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command used to recursively copy all contents of 
 * one directory to another
 * @author Josip Trbuscic
 *
 */
public class Cptree implements ShellCommand{
	
	/**
	 * Command name
	 */
	private String name;
	
	/**
	 * Command description
	 */
	private List<String> description;
	
	/**
	 * Constructs new cptree command
	 */
	public Cptree() {
		name = "cptree";
		description = Arrays.asList("Recursively copies files and directories");
	}
	
	/**
	 * Copies content of a directory to another one. Source and 
	 * destination paths are given as argument in a single string.
	 * @param env - shell environment
	 * @param arguments - source and destination pathss
	 * @return Status of the shell
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> parts = CommandsUtil.extractTwoPaths(arguments);

		if (parts == null) {
			env.writeln("Invalid copy command arguments");
			return ShellStatus.CONTINUE;
		}
		
		Path from = env.getCurrentDirectory().resolve(parts.get(0));
		Path dest = env.getCurrentDirectory().resolve(parts.get(1));
		
		if(!from.toFile().isDirectory()) {
			env.writeln("Path to copy from doesn't exist or doesn't reperesent a directory");
			return ShellStatus.CONTINUE;
		}
		
		if(from.normalize().equals(dest.normalize())) {
			env.writeln("Cannot copy a directory into itself");
			return ShellStatus.CONTINUE;
		}
		
		if(dest.toFile().isDirectory()) {
			try {
				Files.walkFileTree(from, new CptreeFileVisitor(dest));
			} catch (IOException e) {
				env.writeln("Error occured during copying");
			}
			
		}else if(dest.toFile().exists()) {
			env.writeln("Destination path must represent a direcotry");
		}else if(dest.toFile().getParent() != null) {
			try {
				Files.createDirectory(dest);
				for(File child: from.toFile().listFiles()) {
					if(child.isDirectory()) {
						Files.walkFileTree(child.toPath(), new CptreeFileVisitor(dest));
					} else {
						Files.copy(child.toPath(), Paths.get(dest+"/"+child.getName()));
					}
				}
				
			} catch (IOException e) {
				env.writeln("Error occured during copying2nd");
			}
			
		}else {
			env.writeln("Invalid destination path");
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
	 * walk directory tree and copy its contents
	 * @author Josip Trbuscic
	 *
	 */
	private class CptreeFileVisitor extends SimpleFileVisitor<Path>{
		
		/**
		 * Destination path
		 */
		private Path dest;
		
		/**
		 * Constructs new file walker 
		 * @param dest - destination path
		 */
		public CptreeFileVisitor(Path dest) {
			this.dest = dest;
		}
		
		/**
		 * Copies file to the destination directory
		 * @param file - source path
		 */
		@Override
	    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
	        throws IOException
		{
	        Objects.requireNonNull(file);
	        Objects.requireNonNull(attrs);
	        dest = Paths.get(dest+"/"+file.getFileName());
	        Files.copy(file, dest, StandardCopyOption.REPLACE_EXISTING);
	        dest = dest.getParent();
	        return FileVisitResult.CONTINUE;
	    }
		
		/**
		 * Creates new directory in destination directory and 
		 * concatenates its name to the destination path
		 * @param dir - source path
		 */
		@Override
	    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
	        throws IOException
	    {
	        Objects.requireNonNull(dir);
	        Objects.requireNonNull(attrs);
	        
	        dest = Paths.get(dest.toString()+"/"+dir.getFileName().toString()+"/");
	        Files.createDirectory(dest);
	        
	        return FileVisitResult.CONTINUE;
	    }
		
		/**
		 * Sets path of parent directory as destination path
		 * @param dir - source path
		 */
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc)
	        throws IOException
	    {
	        Objects.requireNonNull(dir);
	        dest = dest.getParent();
	        
	        return FileVisitResult.CONTINUE;
	    }

		
	}

}
