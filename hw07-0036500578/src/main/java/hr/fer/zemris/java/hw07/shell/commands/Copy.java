package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command used to copy files from one directory to another.
 * @author Josip Trbuscic
 *
 */
public class Copy implements ShellCommand {
	
	/**
	 * Command Name;
	 */
	private String name;
	
	/**
	 * Command description
	 */
	private List<String> description;

	/**
	 * Constructs new copy command
	 */
	public Copy() {
		name = "copy";
		description = Arrays.asList("copy contents of a file to other file");
	}

	/**
	 * Copies file from one directory to another. Source and 
	 * destination paths are given as argument in a single string.
	 * @param env - shell environment
	 * @param arguments - arguments of copy command
	 * @return Status of the shell
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> parts = CommandsUtil.extractTwoPaths(arguments);

		if (parts == null) {
			env.writeln("Invalid copy command arguments");
			return ShellStatus.CONTINUE;
		}
		
		File from = env.getCurrentDirectory().resolve(parts.get(0)).toFile();
		File to = env.getCurrentDirectory().resolve(parts.get(1)).toFile();
		
		if(from.isDirectory()) {
			env.write("Given path does not exist or represents a directory");
			return ShellStatus.CONTINUE;
		}
		
		if(to.isDirectory()) {
			to = Paths.get(to.toString()+"/"+from.getName()).toFile();
		}
		
		if(to.exists()) {
			env.write("Do you want to overwrite exsisting file? Y/N");
			if(env.readLine().toLowerCase().equals("n")) {
				return ShellStatus.CONTINUE;
			}
		}

		try(InputStream is = new BufferedInputStream(
									Files.newInputStream(from.toPath()));
			OutputStream os = new BufferedOutputStream(
									Files.newOutputStream(to.toPath()))){
			
			byte[] buffer = new byte[4096];
			while (true) {
				int r = is.read(buffer);
				if (r < 1) break;

				os.write(buffer,0,r);
			}
			
			os.flush();
		} catch (IOException e) {
			env.writeln("Could not open file");
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
