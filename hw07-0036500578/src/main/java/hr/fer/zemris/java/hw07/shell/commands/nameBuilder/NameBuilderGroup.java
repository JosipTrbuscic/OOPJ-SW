package hr.fer.zemris.java.hw07.shell.commands.nameBuilder;

/**
 * Implementation of {@link NameBuilder} interface
 * which is used to generate formated string of 
 * regex group
 * @author Josip Trbuscic
 *
 */
public class NameBuilderGroup implements NameBuilder{
	/**
	 * Index of regex group
	 */
	private int groupIndex;
	
	/**
	 * Flag used to indicate a way
	 * of filling empty spaces
	 */
	private boolean zeroes;
	
	/**
	 * String length
	 */
	private int length;
	
	/**
	 * Constructs new instance of this class and 
	 * sets internal variables.
	 * @param groupIndex - index of regex group
	 * @param exp - expression containing additional info
	 */
	public NameBuilderGroup(int groupIndex, String exp) {
		if(exp != null) {
			if(exp.equals("0")) {
				length=0;
				zeroes=false;
			}else if(exp.startsWith("0")) {
				zeroes = true;
				length = Integer.parseInt(exp.substring(1,exp.length()));
			}else {
				zeroes = false;
				length = Integer.parseInt(exp);
			}
		}
		this.groupIndex = groupIndex;
	}
	
	/**
	 * Appends formated string representation of regex group
	 * to given string builder 
	 * @param info - String builder
	 */
	@Override
	public void execute(NameBuilderInfo info) {
		StringBuilder sb = new StringBuilder();
		String group = info.getGroup(groupIndex);
		
		for(int i = length-group.length();i > 0;--i) {
			if(zeroes) {
				sb.append("0");
			}else {
				sb.append(" ");
			}
			
		}
		sb.append(group);
		info.getStringBuilder().append(sb);
	}

}
