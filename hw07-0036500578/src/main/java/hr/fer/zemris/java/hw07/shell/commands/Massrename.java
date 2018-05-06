package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.commands.nameBuilder.NameBuilder;
import hr.fer.zemris.java.hw07.shell.commands.nameBuilder.NameBuilderInfo;
import hr.fer.zemris.java.hw07.shell.commands.nameBuilder.NameBuilderInfoImpl;
import hr.fer.zemris.java.hw07.shell.commands.nameBuilder.NameBuilderParser;

/**
 * Command used for renaming/moving files from a directory. 
 * Files are filtered by applying regular expression on every 
 * file name.
 * @author Josip Trbuscic
 *
 */
public class Massrename implements ShellCommand{
	/**
	 * Command name
	 */
	private String name;
	/**
	 * Command description
	 */
	private List<String> description;
	
	/**
	 * Constructs new massrename command
	 */
	public Massrename() {
		name = "massreanme";
		description = Arrays.asList("Command for mass file renaming/moving");
	}
	
	/**
	 * Parses arguments and executes given subcommand
	 * @param env - shell environment
	 * @param arguments - arguments of massrename command
	 * @return Status of the shell
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length() == 0) {
			env.writeln("Invalid command arguments");
			return ShellStatus.CONTINUE;
		}
		
		List<String> parts;
		try {
			parts = CommandsUtil.extractMassrename(arguments);
		} catch(IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		try {
			switch(parts.get(2).toUpperCase()) {
			case "FILTER":
				return printFiltered(env, parts, false);
			case "GROUPS":
				return printFiltered(env, parts, true);
			case "SHOW":
				return showOrExecute(env, parts, false);
			case "EXECUTE":
				return showOrExecute(env, parts, true);
			default:
				env.writeln("Invalid subcommand name");
				return ShellStatus.CONTINUE;
			}
		}catch(IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		
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
	 * Method used by groups and filtered subcommands for filtering 
	 * files from specified directory 
	 * @param env - shell environment
	 * @param args - massrename command arguments
	 * @param printGroups - flag which specifies print format
	 * @return Shell status
	 */
	private static ShellStatus printFiltered(Environment env,List<String> args, boolean printGroups){
		Pattern filter = Pattern.compile(args.get(3));
		
		File source = Paths.get(args.get(0)).toFile();
		File dest = Paths.get(args.get(1)).toFile();
		
		if(!source.isDirectory() || !dest.isDirectory()) {
			env.writeln("Source and destination paths must represent an exsisting directory");
			return ShellStatus.CONTINUE;
		}
		
		Matcher nameMatcher;
		for(File child : source.listFiles()) {
			if(child.isDirectory()) continue;
			
			nameMatcher = filter.matcher(child.getName());
			if(!nameMatcher.find()) continue;

			if(printGroups) {
				env.writeln(buildFilterOutput(child, nameMatcher));
			}else {
				env.writeln(child.getName());
			}
		}
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Method used by show and execute subcommands for filtering 
	 * files from specified directory and moving them to destination 
	 * directory 
	 * @param env - shell environment
	 * @param args - massrename command arguments
	 * @param printGroups - flag which specifies print format
	 * @return Shell status
	 */
	private static ShellStatus showOrExecute(Environment env, List<String> args, boolean execute) {
		Pattern filter = Pattern.compile(args.get(3), Pattern.CASE_INSENSITIVE|Pattern.UNICODE_CASE);
		
		File source = Paths.get(args.get(0)).toFile();
		File dest = Paths.get(args.get(1)).toFile();
		
		if(!source.isDirectory() || !dest.isDirectory()) {
			env.writeln("Source and destination paths must represent an exsisting directory");
			return ShellStatus.CONTINUE;
		}
		
		NameBuilderParser parser = new NameBuilderParser(args.get(4));
		NameBuilder builder = parser.getNameBuilder();
		
		Matcher nameMatcher;
		for(File child : source.listFiles()) {
			if(child.isDirectory()) continue;
			
			nameMatcher = filter.matcher(child.getName());
			if(!nameMatcher.find()) continue;
			
			NameBuilderInfo info = new NameBuilderInfoImpl(nameMatcher);
			builder.execute(info);
			String newName = info.getStringBuilder().toString();
			if(execute) {
				env.writeln(child.getAbsolutePath() +" => "+
						dest.getAbsolutePath()+"/"+newName);
				try {
					Files.move(child.toPath(), Paths.get(dest+"/"+newName),
							StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					//ignorable
				}
			}else {
				env.writeln(child.getName() +" => "+newName);
			}
		}
		return ShellStatus.CONTINUE;
		
	}
	
	/**
	 * Builds output for groups and filter sub commands
	 * @param file - file to build output for
	 * @param m - matcher containing groups
	 * @return formated string 
	 */
	private static String buildFilterOutput(File file, Matcher m) {
		StringBuilder sb = new StringBuilder(file.getName());
		
		for(int i = 0; i<=m.groupCount();++i) {
			sb.append(" "+i+": "+m.group(i));
		}
		
		return sb.toString();
	}
	
}
