package hr.fer.zemris.java.hw07.shell.commands.nameBuilder;

import java.util.regex.Matcher;

/**
 * Class containing concatenated strings 
 * of every {@link NameBuilder} and 
 * matcher which contains original file name
 * @author Josip Trbuscic
 *
 */
public class NameBuilderInfoImpl implements NameBuilderInfo{
	
	/**
	 *String builder containing formated file name 
	 */
	private StringBuilder name;
	
	/**
	 * Matcher containing original file name
	 */
	private Matcher matcher;
	
	/**
	 * Constructs new instance of this class
	 * and stores given matcher as internal 
	 * variable
	 * @param matcher
	 */
	public NameBuilderInfoImpl(Matcher matcher) {
		this.matcher = matcher;
		name  = new StringBuilder();
	}
	
	/**
	 * Returns stored string builder
	 * @return stored string builder
	 */
	@Override
	public StringBuilder getStringBuilder() {
		return name;
	}

	/**
	 * Returns matcher group specified
	 * by given index
	 * @param index - group index
	 * @return matcher group
	 */
	@Override
	public String getGroup(int index) {
		if(index > matcher.groupCount()) {
			throw new IllegalArgumentException("Group index out of range");
		}
		return matcher.group(index);
	}

}
