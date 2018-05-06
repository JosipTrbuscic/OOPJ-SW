package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command which prints file, whose path is given as 
 * argument, to output stream 
 * @author Josip Trbuscic
 *
 */
public class Cat implements ShellCommand {
	/**
	 * Command Name;
	 */
	private String name;
	
	/**
	 * Command description
	 */
	private List<String> description;

	/**
	 * Constructs new cat command
	 */
	public Cat() {
		name = "cat";
		description = Arrays.asList("Cat command opens given file and writes" + "its contents to console");
	}

	/**
	 * Parses given argument as path to the file 
	 * and prints its content to the environment.
	 * @param env - shell environment
	 * @param arguments - argument of cat command
	 * @return Status of the shell
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> parts = CommandsUtil.extractPathAndCharset(arguments);
		
		if (parts == null) {
			env.writeln("Invalid cat command arguments");
			return ShellStatus.CONTINUE;
		}

		Path path = env.getCurrentDirectory().resolve(parts.get(0));
		Charset charset = null;
		
		try {
			charset = parts.size() == 1 ? Charset.defaultCharset() : Charset.forName(parts.get(2));
		} catch (IllegalArgumentException e) {
			env.writeln("Invalid charset");
			return ShellStatus.CONTINUE;
		}

		try (BufferedReader br = Files.newBufferedReader(path, charset)) {

			while (true) {
				String line = br.readLine();

				if (line == null)
					break;
				env.writeln(line);
			}
		} catch (IOException e) {
			env.writeln("Cannot open file from given path");
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
