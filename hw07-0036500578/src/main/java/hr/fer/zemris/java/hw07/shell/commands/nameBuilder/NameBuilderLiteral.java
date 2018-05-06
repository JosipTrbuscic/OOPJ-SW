package hr.fer.zemris.java.hw07.shell.commands.nameBuilder;

/**
 * Implementation of {@link NameBuilder} interface
 * which stores string literal of new file name
 * @author Josip Trbuscic
 *
 */
public class NameBuilderLiteral implements NameBuilder {
	/**
	 * String literal
	 */
	private String literal;
	
	/**
	 * Constructs new instance of this class 
	 * and stores given string as internal variable
	 * @param literal - string literal
	 */
	public NameBuilderLiteral(String literal) {
		this.literal = literal;
	}
	
	/**
	 * Appends stored string to given string builder
	 * @param info - string builder
	 */
	@Override
	public void execute(NameBuilderInfo info) {
		info.getStringBuilder().append(literal);
	}

}
