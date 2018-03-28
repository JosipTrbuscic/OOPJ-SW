package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.ElementString;

public class TextNode extends Node {
	private ElementString text;
	
	public TextNode(ElementString text) {
		this.text = text;
	}
	
	public ElementString getText() {
		return text;
	}
}
