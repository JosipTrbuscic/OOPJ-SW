package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.ElementString;

/**
 * This class represents node of parsed document tree.
 * This node contains {@code ElementString} attribute.
 * @author Josip Trbuscic
 *
 */
public class TextNode extends Node {
	private ElementString text; // Stores string
	
	/**
	 *Constructs new {@code TextNode}  specified by 
	 *text argument
	 * @param text
	 */
	public TextNode(ElementString text) {
		this.text = text;
	}
	
	/**
	 * @return This nodes text
	 */
	public ElementString getText() {
		return text;
	}
	
	/**
	 * Returns string representation of text this node contains
	 * @return text this node contains
	 */
	@Override
	public String toString() {
		return text.asText();
	}
}
