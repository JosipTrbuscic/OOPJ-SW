package hr.fer.zemris.java.hw07.shell.commands.nameBuilder;

/**
 * Interface implemented by classes which 
 * contain formated string to be printed
 * 
 * @author Josip Trbuscic
 *
 */
public interface NameBuilderInfo {

	/**
	 * Returns string builder containing 
	 * formated string
	 * @return string builder containing 
	 * formated string
	 */
	StringBuilder getStringBuilder();
	
	/**
	 * Returns matcher group specified by given
	 * index
	 * @param index - index of a group 
	 * @return matcher group specified by given
	 * index
	 */
	String getGroup(int index);
}
