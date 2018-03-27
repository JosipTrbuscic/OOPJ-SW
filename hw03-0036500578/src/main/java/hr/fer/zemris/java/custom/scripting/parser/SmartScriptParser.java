package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.ObjectStack;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

public class SmartScriptParser {
	private DocumentNode document;
	
	public SmartScriptParser(String document) {
		if(document == null) throw new NullPointerException();
		
		this.document = new DocumentNode(document);
		parse(new Lexer(document));
	}
	
	public void parse(Lexer lexer) {
		ObjectStack stack = new ObjectStack();
		stack.push(document);
		
		while(lexer.getToken().getType() != TokenType.EOF) {
			Token token = lexer.nextToken();			
			Node node;
			
			if(token.getType() == TokenType.TEXT) {
				node = new TextNode((String)token.getValue());
				Node parent = (Node) stack.peek();
				
				parent.addChildNode(node);
			} else if (token.getType() == TokenType.)
		}
	}
	
	public DocumentNode getDocumentNode() {
		return document;
	}
	
}
