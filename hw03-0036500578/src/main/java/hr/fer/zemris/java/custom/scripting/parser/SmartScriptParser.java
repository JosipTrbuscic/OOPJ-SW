package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.ObjectStack;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

public class SmartScriptParser {
	private DocumentNode document;

	public SmartScriptParser(String document) {
		if (document == null)
			throw new NullPointerException();

		this.document = new DocumentNode(document);
		parse(new Lexer(document));
	}

	public void parse(Lexer lexer) {
		ObjectStack stack = new ObjectStack();
		stack.push(document);
		
		while(lexer.getToken().getType() != TokenType.EOF) {
			Token token = lexer.nextToken();			
			Node node;
			
			if(token.getType().equals(TokenType.TAG)) {
				if(token.getValue().asText().equals("$}")) {
					lexer.setState(LexerState.TEXT_STATE);
				} else {
					lexer.setState(LexerState.TAG_STATE);
				}
				continue;
			}
			
			if(token.getType() == TokenType.TEXT) {
				node = new TextNode(token.getValue().asText());
				Node parent = (Node) stack.peek();
				
				parent.addChildNode(node);
			} else if (token.getType() == TokenType.FOR) {
				lexer.setState(LexerState.FOR_STATE);
				try {
					node = new ForLoopNode((ElementVariable)lexer.nextToken().getValue()
										, lexer.nextToken().getValue()
										, lexer.nextToken().getValue()
										,lexer.nextToken().getValue());
				} catch (LexerException ex) {
					throw new SmartScriptParserException();
				}
				
			} else if(token.getType().equals(TokenType.FOR)) {
				
				
			}
			
			
			
		}
	}

	public DocumentNode getDocumentNode() {
		return document;
	}

}
