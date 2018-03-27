package hr.fer.zemris.java.custom.scripting.nodes;

public class DocumentNode extends Node {
	private String document;
	
	public DocumentNode(String documnet) {
		if(documnet == null) throw new NullPointerException("Document cannot be null");
		
		this.document = documnet;
	}
	
	
}
