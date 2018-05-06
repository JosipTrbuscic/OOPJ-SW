package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command used to print or update specific shell symbol
 * @author Josip Trbuscic
 *
 */
public class Symbol implements ShellCommand {
	/**
	 * Command name
	 */
	private String name;
	
	/**
	 * Command description
	 */
	private List<String> description;

	/**
	 * Construct new symbol command
	 */
	public Symbol() {
		name = "symbol";
		description = Arrays.asList("prints current symbol or sets new one");
	}
	
	/**
	 * Prints or updates specified shell symbol
	 * @param env - shell environment
	 * @param arguments - shell symbol name
	 * @return Status of the shell
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] parts = arguments.split("\\s+");
		if(parts.length < 1 || parts.length > 2) {
			env.writeln("Invalid number of arguments for symbol command");
			return ShellStatus.CONTINUE;
		}
		
		switch(parts[0].toUpperCase()) {
		case"PROMPT":
			if(parts.length == 1) {
				env.writeln("Symbol for PROMPT is '" +env.getPromptSymbol()+"'");
				return ShellStatus.CONTINUE;
			}
			
			if(parts[1].length() != 1) {
				env.writeln("PROMPT symbol must be only 1 character");
				return ShellStatus.CONTINUE;
			}
			
			Character oldPrompt = env.getPromptSymbol();
			env.setPromptSymbol(parts[1].charAt(0));
			
			env.writeln("Symbol for PROMPT changed from '"+oldPrompt+"'"
						+" to '"+ env.getPromptSymbol()+"'");
			
			return ShellStatus.CONTINUE;
		case"MORELINES":
			if(parts.length == 1) {
				env.writeln("Symbol for MORELINES is '" +env.getMorelinesSymbol()+"'");
				return ShellStatus.CONTINUE;
			}
			
			if(parts[1].length() != 1) {
				env.writeln("MORELINES symbol must be only 1 character");
				return ShellStatus.CONTINUE;
			}
			
			Character oldMoreLines = env.getMorelinesSymbol();
			env.setMorelinesSymbol(parts[1].charAt(0));
			
			env.writeln("Symbol for MORELINES changed from '"+oldMoreLines+"'"
						+" to '"+ env.getMorelinesSymbol()+"'");
			
			return ShellStatus.CONTINUE;
			
		case"MULTILINE":
			if(parts.length == 1) {
				env.writeln("Symbol for MULTILINE is '" +env.getMultilineSymbol()+"'");
				return ShellStatus.CONTINUE;
			}
			
			if(parts[1].length() != 1) {
				env.writeln("MULTILINE symbol must be only 1 character");
				return ShellStatus.CONTINUE;
			}
			
			Character oldMultiLine = env.getMultilineSymbol();
			env.setMultilineSymbol(parts[1].charAt(0));
			
			env.writeln("Symbol for MULTILINE changed from '"+oldMultiLine+"'"
						+" to '"+ env.getMultilineSymbol()+"'");
			
			return ShellStatus.CONTINUE;
			
		default:
			env.writeln("Invalid symbol command argument keyword");
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
}
