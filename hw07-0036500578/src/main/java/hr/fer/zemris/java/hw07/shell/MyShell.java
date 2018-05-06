package hr.fer.zemris.java.hw07.shell;

import java.nio.file.Paths;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw07.shell.commands.Cat;
import hr.fer.zemris.java.hw07.shell.commands.Cd;
import hr.fer.zemris.java.hw07.shell.commands.Charsets;
import hr.fer.zemris.java.hw07.shell.commands.Copy;
import hr.fer.zemris.java.hw07.shell.commands.Cptree;
import hr.fer.zemris.java.hw07.shell.commands.Dropd;
import hr.fer.zemris.java.hw07.shell.commands.Exit;
import hr.fer.zemris.java.hw07.shell.commands.Help;
import hr.fer.zemris.java.hw07.shell.commands.Hexdump;
import hr.fer.zemris.java.hw07.shell.commands.Listd;
import hr.fer.zemris.java.hw07.shell.commands.Ls;
import hr.fer.zemris.java.hw07.shell.commands.Massrename;
import hr.fer.zemris.java.hw07.shell.commands.Mkdir;
import hr.fer.zemris.java.hw07.shell.commands.Popd;
import hr.fer.zemris.java.hw07.shell.commands.Pushd;
import hr.fer.zemris.java.hw07.shell.commands.Pwd;
import hr.fer.zemris.java.hw07.shell.commands.Rmtree;
import hr.fer.zemris.java.hw07.shell.commands.Symbol;
import hr.fer.zemris.java.hw07.shell.commands.Tree;

/**
 * Emulator of a simple shell which offers user to do 
 * basic shell prompts 
 * @author Josip Trbuscic
 *
 */
public class MyShell {

	/**
	 * Main method of program 
	 * @param args - command line arguments.
	 * 				Not used here
	 */
	public static void main(String[] args) {
		Environment env = initEnv();
		env.writeln("Welcome to MyShell v 1.0");
		env.writeln(env.getCurrentDirectory().toString());
		while (true) {
			StringBuilder sb = new StringBuilder();
			env.write(env.getPromptSymbol().toString());
			String line = env.readLine();
			String commandString;

			if (line.endsWith(env.getMorelinesSymbol().toString())) {
				while (line.endsWith(env.getMorelinesSymbol().toString())) {
					sb.append(" " + line.substring(0, line.length() - 1));
					env.write(env.getMultilineSymbol().toString() + " ");
					line = env.readLine();
				}
				sb.append(" " + line);
				commandString = sb.toString();
			} else {
				commandString = line;
			}

			String[] parts = commandString.trim().split("\\s+");
			ShellCommand command = env.commands().get(parts[0].toLowerCase());
			if (command == null) {
				env.writeln("Invalid command keyword");
				continue;
			}

			try {
				ShellStatus status = command.executeCommand(env, join(parts, 1, parts.length).trim());

				if (status == ShellStatus.TERMINATE) break;
			} catch (ShellIOException e) {
				System.err.println("Error occured. Terminating..");
				System.exit(0);
			}

		}

	}

	/**
	 * Creates new shell environment and fills map
	 * of commands  
	 * @return new environment
	 */
	private static Environment initEnv() {
		SortedMap<String, ShellCommand> commands = new TreeMap<>();

		commands.put("cat", new Cat());
		commands.put("charsets", new Charsets());
		commands.put("copy", new Copy());
		commands.put("exit", new Exit());
		commands.put("help", new Help());
		commands.put("hexdump", new Hexdump());
		commands.put("ls", new Ls());
		commands.put("tree", new Tree());
		commands.put("mkdir", new Mkdir());
		commands.put("symbol", new Symbol());
		commands.put("pwd", new Pwd());
		commands.put("cd", new Cd());
		commands.put("pushd", new Pushd());
		commands.put("popd", new Popd());
		commands.put("dropd", new Dropd());
		commands.put("rmtree", new Rmtree());
		commands.put("cptree", new Cptree());
		commands.put("listd", new Listd());
		commands.put("massrename", new Massrename());

		Environment env = new MyEnvironment(commands);
		env.setCurrentDirectory(Paths.get(".").toAbsolutePath());
		
		return env;
	}

	/**
	 * Joins parts of string array from start to end-1 indexes
	 * 
	 * @param parts
	 *            - array of strings
	 * @param start
	 *            - starting index
	 * @param end
	 *            - end index
	 * @return joined String from parts
	 */
	private static String join(String[] parts, int start, int end) {
		String result = "";

		for (int i = start; i < end; ++i) {
			result += " " + parts[i];
		}

		return result.trim();
	}
}
