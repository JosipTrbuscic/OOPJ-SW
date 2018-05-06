package hr.fer.zemris.java.hw07.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
/**
 * Implementation of {@link Environment} interface 
 * representing simple shell environment which interacts 
 * with user allowing him to use simple commands
 * @author Josip Trbuscic
 *
 */
public class MyEnvironment implements Environment {
	/**
	 * Input stream which takes user inputs
	 */
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	/**
	 * Output stream where output is printed
	 */
	private BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	/**
	 * Path representing current/working directory
	 */
	private Path currentDirectory;
	
	/**
	 * Map where commands are stored
	 */
	private SortedMap<String, ShellCommand> commands;
	
	/**
	 * Map containing shared data
	 */
	private Map<String, Object> sharedData;
	
	/**
	 * prompt symbol
	 */
	private Character promptSymbol = '>';
	
	/**
	 * Symbol that indicates command will be
	 * written in multiple lines
	 */
	private Character morelinesSymbol = '\\';
	
	/**
	 * Symbol that indicates new line of multiline 
	 * command
	 */
	private Character multilineSymbol = '|';
	
	/**
	 * Constructs new shell environment and stores given
	 * map of commands 
	 * @param commands
	 */
	public MyEnvironment(SortedMap<String, ShellCommand> commands) {
		if(commands == null) throw new NullPointerException("Command map cannot be null");
		
		this.commands = commands;
		sharedData= new HashMap<String,Object>();
	}
	
	/**
	 * Returns line of user input 
	 * @return line of user input
	 * @throws ShellIOException if line 
	 * 			cannot be read
	 */
	@Override
	public String readLine() throws ShellIOException {
		try {
			return br.readLine();
		} catch (IOException e) {
			throw new ShellIOException("Unable to read line");
		}
	}

	/**
	 * Writes string to output stream and does not jump 
	 * to next line
	 * @param text - string to write
	 * @throws ShellIOException if string could not be written
	 */
	@Override
	public void write(String text) throws ShellIOException {
		try {
			bw.write(text);
			bw.flush();
		} catch (IOException e) {
			throw new ShellIOException("Unable to write line");
		}
		
	}

	/**
	 * Writes string to output stream and jumps
	 * to next line
	 * @param text - string to write
	 * @throws ShellIOException if string could not be written
	 */
	@Override
	public void writeln(String text) throws ShellIOException {
		try {
			bw.write(text);
			bw.write("\n");
			bw.flush();
		} catch (IOException e) {
			throw new ShellIOException("Unable to write line");
		}
		
		
	}

	/**
	 * Returns unmodifiable map of commands offered by the shell 
	 * @return map of commands
	 */
	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	/**
	 * Returns symbol which indicates command 
	 * is being written in multiple lines
	 * @return current multiline symbol
	 */
	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	/**
	 * Sets new multiline symbol
	 * @param symbol - new multiline symbol which will be set
	 */
	@Override
	public void setMultilineSymbol(Character symbol) {
		multilineSymbol = symbol;
	}

	/**
	 * Returns symbol which represents start of 
	 * new prompt
	 * @return current prompt symbol
	 */
	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	/**
	 * Sets new prompt symbol
	 * @param symbol - new prompt symbol which will be set
	 */
	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol = symbol;
	}

	/**
	 * Returns symbol which indicates command will be written in
	 * multiple lines
	 * @return current morelines symbol
	 */
	@Override
	public Character getMorelinesSymbol() {
		return morelinesSymbol;
	}

	/**
	 * Sets new morelines symbol
	 * @param symbol - morelines symbol to be set
	 */
	@Override
	public void setMorelinesSymbol(Character symbol) {
		morelinesSymbol = symbol;
	}

	/**
	 * Returns path of current/working directory
	 * @return path of current/working directory
	 */
	@Override
	public Path getCurrentDirectory() {
		return currentDirectory.normalize();
	}


	/**
	 * Sets current/working directory to the 
	 * given one
	 * @param path - path to the new working 
	 * 				directory
	 * @throws IllegalArgumentException if path
	 * 			doesn't represent existing directory
	 */
	@Override
	public void setCurrentDirectory(Path path) {
		if(!path.toFile().isDirectory()) {
			throw new IllegalArgumentException("Invalid direcotry path");
		}
		
		this.currentDirectory = path;
	}

	/**
	 * Returns the value to which the specified key is mapped.
	 * If specified key is not found method returns null
	 * @param key - the key whose value is to be returned.
	 * @return - the value to which the specified key is mapped.
	 */
	@Override
	public Object getSharedData(String key) {
		return sharedData.get(key);
	}

	/**
	 * Maps the specified key to the specified value.
	 * Key cannot be null. New array with double
	 * of its initial capacity will be allocated if needed.
	 * @param key - entry key
	 * @param value - entry value
	 * @throws NullPointerException if specified key is {@code null}
	 */
	@Override
	public void setSharedData(String key, Object value) {
		if(key == null) {
			throw new NullPointerException("Key cannoit be null");
		}
		sharedData.put(key, value);
	}

}
