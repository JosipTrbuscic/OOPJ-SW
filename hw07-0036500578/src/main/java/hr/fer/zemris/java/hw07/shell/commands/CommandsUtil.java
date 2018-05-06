package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class offering static methods 
 * for parsing command inputs 
 * @author Josip Trbuscic
 *
 */
public class CommandsUtil {
	/**
	 * Regex used to match two file or directory paths 
	 */
	private static String twoPaths = "\\s*((\"(.+)\")|([\\S]+))\\s+((\"(.+)\")|([\\S]+))\\s*";
	
	/**
	 * Returns file path extracted from from given string if possible 
	 * @param arguments - string containing file path
	 * @return file path extracted from from given string or null
	 * 			if string doesnt represent file path
	 */
	public static String extractPath(String arguments) {
		Pattern p = Pattern.compile("^\\s*((\"(.+)\")|([\\S]+))\\s*$");
		Matcher m = p.matcher(arguments);

		if (!m.find()) {
			return null;
		}

		if (m.group(3) != null) {
			return m.group(3).replace("\\\"", "\"").replace("\\\\", "\\");
		}
		
		return m.group(4);
	}
	
	/**
	 * Returns file path and charset name extracted
	 *  from from given string if possible 
	 * @param arguments - string containing file path and charset
	 * @return file path extracted from from given string or null
	 * 			if string doesn't represent file path
	 */
	public static List<String> extractPathAndCharset(String arguments){
		Pattern p = Pattern.compile("^\\s*((\"(.+)\")|([\\S]+))(\\s+([\\da-zA-Z-]+))?\\s*$");
		Matcher m = p.matcher(arguments);

		if (!m.find()) {
			return null;
		}

		List<String> args = new ArrayList<>();

		if (m.group(3) != null) {
			args.add(m.group(3).replace("\\\"", "\"").replace("\\\\", "\\"));
		} else {
			args.add(m.group(4));
		}

		if (m.group(6) != null) {
			args.add(m.group(6));
		}

		return args;
	}

	/**
	 * Returns two file paths extracted from from given string if possible 
	 * @param arguments - string containing file path
	 * @return list of file paths extracted from from given string or null
	 * 			if string doesn't represent exactly two file paths
	 */
	public static List<String> extractTwoPaths(String arguments) {
		Pattern p = Pattern.compile("^"+twoPaths+"$");
		Matcher m = p.matcher(arguments);
		
		if (!m.find()) {
			return null;
		}
		List<String> args = new ArrayList<>();

		if (m.group(3) != null) {
			args.add(m.group(3).replace("\\\"", "\"").replace("\\\\", "\\"));
		} else {
			args.add(m.group(4));
		}

		if (m.group(7) != null) {
			args.add(m.group(7).replace("\\\"", "\"").replace("\\\\", "\\"));
		} else {
			args.add(m.group(8));
		}

		return args;
	}
	
	/**
	 * Method used to extract arguments for massrename command
	 * @param arguments - string containing massrename arguments
	 * @return list of massrename command arguments, null otherwise
	 */
	public static List<String> extractMassrename(String arguments){
		Pattern paths = Pattern.compile("^"+twoPaths);
		Matcher m = paths.matcher(arguments);
		
		List<String> args = new ArrayList<>();
		
		if (!m.find()) {
			return null;
		}
		
		if (m.group(3) != null) {
			args.add(m.group(3).replace("\\\"", "\"").replace("\\\\", "\\"));
		} else {
			args.add(m.group(4));
		}
		
		if (m.group(7) != null) {
			args.add(m.group(7).replace("\\\"", "\"").replace("\\\\", "\\"));
		} else {
			args.add(m.group(8));
		}
		int pathsEnd = m.end();
		Pattern subcommand = Pattern.compile("^([a-zA-Z]+)\\s*");
		m = subcommand.matcher(arguments.substring(pathsEnd, arguments.length()));
		
		if (!m.find()) {
			throw new IllegalArgumentException("Invalid subcommand name");
		}
		String command = m.group(1).toLowerCase();
		args.add(command);

		if(command.equals("filter") || command.equals("groups")) {
			extractFilterOrGenExpreesion(arguments, pathsEnd +  m.end(), args, false);
			
		} else if(command.equals("show") || command.equals("execute")) {
			int end = extractFilterOrGenExpreesion(arguments, pathsEnd + m.end(), args, false);
			extractFilterOrGenExpreesion(arguments, pathsEnd+m.end()+end, args, true);
			
		} else {
			throw new IllegalArgumentException("Invalid subcommand name");
		}
		
		return args;
	}
	
	/**
	 * Helper method used to extract filter or name generation
	 * expression of massrename command
	 * @param arguments
	 * @param start
	 * @param args
	 * @return
	 */
	private static int extractFilterOrGenExpreesion(
			String arguments, int start, List<String> args, boolean genExp 
			) {
		Pattern pattern;
		if(genExp) {
			pattern  = Pattern.compile("^((\"(.+)\")|([\\S]+))\\s*$");
		} else {
			pattern = Pattern.compile("^((\"(.+)\")|([\\S]+))\\s*");
		}
		Matcher m = pattern.matcher(arguments.substring(start, arguments.length()));
		
		if (!m.find()) {
			throw new IllegalArgumentException("Invalid filter");
		}
		
		if (m.group(3) != null) {
			args.add(m.group(3));
			return m.end();
		}
		args.add(m.group(4));
		return m.end();
	}
}
