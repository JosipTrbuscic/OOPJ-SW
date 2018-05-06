package hr.fer.zemris.java.hw07.shell.commands.nameBuilder;

import java.util.List;

/**
 * Implementation of {@link NameBuilder} interface
 * which stores references to all other NameBuilders
 * @author Josip Trbuscic
 *
 */
public class NameBuilderFinal implements NameBuilder{
	
	/**
	 * List of all NameBuilders
	 */
	private List<NameBuilder> list;

	/**
	 * Constructs new instance of this class 
	 * and stores given list as internal variable
	 * @param list - list of NameBuilders
	 */
	public NameBuilderFinal(List<NameBuilder> list) {
		this.list = list;
	}
	
	/**
	 * Invokes execute method of every NameBuilder in stored list
	 */
	@Override
	public void execute(NameBuilderInfo info) {
		list.forEach(n->n.execute(info));
	}
	
}
