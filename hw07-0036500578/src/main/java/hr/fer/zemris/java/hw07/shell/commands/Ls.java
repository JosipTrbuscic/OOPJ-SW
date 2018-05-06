package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command used to print information about contents 
 * of a directory
 * @author Josip Trbuscic
 *
 */
public class Ls implements ShellCommand{
	/**
	 * Command name
	 */
	private String name;
	
	/**
	 * Command description
	 */
	private List<String> description;

	/**
	 * Constructs new ls command
	 */
	public Ls() {
		name = "ls";
		description = Arrays.asList("Writes a directory listing");
	}

	/**
	 * Prints information about contents 
	 * of a directory. Directory path is given
	 * as argument.
	 * @param env - shell environment
	 * @param arguments - directory path
	 * @return Status of the shell
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String dirString = CommandsUtil.extractPath(arguments);

		if (dirString == null) {
			env.writeln("Invalid ls command argument");
			return ShellStatus.CONTINUE;
		}

		File dirFile = env.getCurrentDirectory().resolve(dirString).toFile();

		if (!dirFile.isDirectory()) {
			env.writeln("Given path does not represent a valid directory");
			return ShellStatus.CONTINUE;
		}

		for (File file : dirFile.listFiles()) {
			try {
				env.writeln(buildOutput(file));
			} catch (IOException e) {
				//ignorable
			}

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
	 * Returns formated string representation of
	 * file or directory informations 
	 * @param file - path to file 
	 * @return String containing file info
	 * @throws IOException if path doesn't represent
	 * 				existing file or directory 
	 */
	private static String buildOutput(File file) throws IOException {
		StringBuilder sb = new StringBuilder();
		
		sb.append(file.isDirectory() ? "d" : "-");
		sb.append(file.canRead() ? "r" : "-");
		sb.append(file.canWrite() ? "w" : "-");
		sb.append(file.canExecute() ? "x" : "-");
		
		sb.append(String.format("%10d ", file.length()));
		
		sb.append(getTime(file)+" ");
		
		sb.append(file.getName());

		return sb.toString();
	}

	/**
	 * Returns string representation of time when 
	 * file or directory was created
	 * @param file - file path
	 * @return string representation of time when 
	 * 			file or directory was created
	 * @throws IOException if path doesn't represent
	 * 				existing file or directory 
	 */
	private static String getTime(File file) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Path path = Paths.get("/home/josip/skripta.sh");
		BasicFileAttributeView faView = Files.getFileAttributeView(path,
				BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		
		BasicFileAttributes attributes = faView.readAttributes();
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		return formattedDateTime;
	}
}
