package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static hr.fer.zemris.java.hw07.crypto.Util.byteToHex;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command used to print content of a 
 * file in hexadecimal format
 * @author Josip Trbuscic
 *
 */
public class Hexdump implements ShellCommand {

	/**
	 * Command Name
	 */
	private String name;

	/**
	 * Command description
	 */
	private List<String> description;

	/**
	 * Constructs new hexdump command
	 */
	public Hexdump() {
		name = "hexdump";
		description = Arrays.asList("Displays contents of file in hexadecimal notation");
	}

	/**
	 * Prints contents of a file in a hexadecimal format
	 * @param env - shell environment
	 * @param arguments - file path
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

		if (dirFile.isDirectory()) {
			env.write("Invalid file path");
			return ShellStatus.CONTINUE;
		}

		try (InputStream is = new BufferedInputStream(Files.newInputStream(dirFile.toPath()))) {
			byte[] buffer = new byte[1024];
			int rowNumber = 0;
			while (true) {
				int r = is.read(buffer);
				if (r < 1)
					break;

				buildOutput(buffer, r, rowNumber, env);
				rowNumber += r;
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

	/**
	 * Prints buffered array of bytes in hexadecimal representation
	 * 
	 * @param buffer
	 *            - array of bytes
	 * @param len
	 *            - array length
	 * @param bufferNumber
	 *            - how many buffers have been printed
	 * @param env
	 *            - shell
	 */
	private static void buildOutput(byte[] buffer, int len, int rowNumber, Environment env) {

		for (int i = 0; i < len; i += 16) {
			// for row number and hexadecimal digits
			StringBuilder sbHex = new StringBuilder();
			// for characters
			StringBuilder sbCharacters = new StringBuilder();

			sbHex.append(rowNumberHex(rowNumber));
			sbCharacters.append("| ");

			for (int j = 0; j < 16; ++j) {
				byte[] b = new byte[1];
				b[0] = buffer[i + j];

				if (i + j >= len) {
					sbHex.append("   ");
				} else {
					sbHex.append(byteToHex(b) + " ");
				}

				if (j == 7) {
					sbHex.append("|");
				} else {
					sbHex.append(" ");
				}

				if (i + j >= len) {
					sbCharacters.append(' ');
				} else if (buffer[i + j] >= 32 && buffer[i + j] <= 127) {
					sbCharacters.append((char) buffer[i + j]);
				} else {
					sbCharacters.append('.');
				}
			}

			sbHex.append(sbCharacters.toString() + "\n");
			env.write(sbHex.toString());
			rowNumber += 16;
		}

	}

	/**
	 * Generates hexadecimal row number as 8 character string 
	 * @param num - decimal row number
	 * @return String representation of row number
	 * 			 in hexadecimal format
	 */
	private static String rowNumberHex(int num) {
		int rowNumberLength = 8;
		String hex = Integer.toHexString(num);
		StringBuilder sb = new StringBuilder();

		for (int i = 0, len = hex.length(); i < rowNumberLength - len; ++i) {
			sb.append("0");
		}

		return sb.append(hex + " ").toString();
	}
}
