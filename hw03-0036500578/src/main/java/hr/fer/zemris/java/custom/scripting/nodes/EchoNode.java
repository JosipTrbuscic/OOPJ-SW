package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * This class represents node of parsed document tree.
 * Class contains any number of {@code Element}s stored in internal 
 * array.
 * @author Josip Trbuscic
 *
 */
public class EchoNode extends Node{
	private Element[] elements; // Stores elements
	
	/**
	 * Constructs new {@code EchoNode} with given array
	 * of {@code Element}s
	 * @param elements array
	 * @throws NullPointerException if given array is null
	 */
	public EchoNode(Element[] elements) {
		if(elements == null) throw new NullPointerException("Cannot create EchonNode with null argument");
		
		this.elements = elements;
	}
	
	/**
	 * @return array of {@code Element}s
	 */
	public Element[] getElements() {
		return elements;
	}
	
	/**
	 * Returns string representation of this node
	 * @return returns string representation of this node
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{$");
		for (Element el : elements) {
			sb.append(" " + el.asText());
		}

		sb.append(" $}");
		return sb.toString();
	}
}
