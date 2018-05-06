package hr.fer.zemris.java.hw07.shell;

import java.nio.file.Path;
import java.util.SortedMap;

/**
 * Interface which defines basic methods that shell 
 * emulator needs to implement.
 * @author Josip Trbuscic
 *
 */
public interface Environment {
	
	/**
	 * Reads line from input stream and returns it as string object.
	 * Line is terminated by '\n' character.
	 * @return new String which represents read line
	 * @throws ShellIOException if line could not be read
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Writes string to output stream and does not jump 
	 * to next line
	 * @param text - string to write
	 * @throws ShellIOException if string could not be written
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Writes string to output stream and jumps
	 * to next line
	 * @param text - string to write
	 * @throws ShellIOException if string could not be written
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Returns map of commands offered by the shell 
	 * @return map of commands
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Returns symbol which indicates command 
	 * is being written in multiple lines
	 * @return current multiline symbol
	 */
	Character getMultilineSymbol();
	
	/**
	 * Sets new multiline symbol
	 * @param symbol - new multiline symbol which will be set
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Returns symbol which represents start of 
	 * new prompt
	 * @return current prompt symbol
	 */
	Character getPromptSymbol();
	
	/**
	 * Sets new prompt symbol
	 * @param symbol - new prompt symbol which will be set
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Returns symbol which indicates command will be written in
	 * multiple lines
	 * @return current morelines symbol
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Sets new morelines symbol
	 * @param symbol - morelines symbol to be set
	 */
	void setMorelinesSymbol(Character symbol);
	
	/**
	 * Returns path of current/working directory
	 * @return path of current/working directory
	 */
	Path getCurrentDirectory();
	
	/**
	 * Sets current/working directory to the 
	 * given one
	 * @param path - path to the new working 
	 * 				directory
	 */
	void setCurrentDirectory(Path path);
	
	/**
	 * Returns the value to which the specified key is mapped.
	 * If specified key is not found method returns null
	 * @param key - the key whose value is to be returned.
	 * @return - the value to which the specified key is mapped.
	 */
	Object getSharedData(String key);
	
	/**
	 * Maps the specified key to the specified value.
	 * Key cannot be null. New array with double
	 * of its initial capacity will be allocated if needed.
	 * @param key - entry key
	 * @param value - entry value
	 * @throws NullPointerException if specified key is {@code null}
	 */
	void setSharedData(String key, Object value);
}
