package hr.fer.zemris.java.hw07.shell.commands.nameBuilder;

/**
 * Implementation of {@link NameBuilder} interface
 * which stores references to all other NameBuilders
 * @author Josip Trbuscic
 *
 */
public interface NameBuilder {

	/**
	 * Appends string to the given string builder
	 * @param info - name builder info
	 */
	void execute(NameBuilderInfo info);
}
