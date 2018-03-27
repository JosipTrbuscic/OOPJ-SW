package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.elems.Element;
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

		while (lexer.getToken().getType() != TokenType.EOF) {
			Token token = lexer.nextToken();
			Node node;

			if (token.getType() == TokenType.TEXT) {
				node = new TextNode(token.getValue().asText());
				Node parent = (Node) stack.peek();

				parent.addChildNode(node);
				lexer.setState(LexerState.TAG_STATE);
			} else if (token.getType().equals(TokenType.FOR)) {
				node = parseFor(lexer);
				Node parent = (Node) stack.peek();

				parent.addChildNode(node);
				stack.push(node);
			} else if (token.getType().equals(TokenType.EMPTY)) {
				
				
				
			} else if (token.getType().equals(TokenType.END)) {
				
				
				
			}

		}
	}

	private static ForLoopNode parseFor(Lexer lexer) {
		Element[] parameters = new Element[4];
		Token token;

		token = lexer.nextToken();

		if (token.getType() != TokenType.VARIABLE)
			throw new SmartScriptParserException();

		parameters[0] = lexer.getToken().getValue();

		for (int i = 1; i < 4; ++i) {
			token = lexer.nextToken();

			if (token.getType() == TokenType.OPERATOR || token.getType() == TokenType.FUNCTION) {
				throw new SmartScriptParserException();
			}
			parameters[i] = token.getValue();

		}

		try {
			return new ForLoopNode((ElementVariable) parameters[0], parameters[1], parameters[2], parameters[3]);
		} catch (NullPointerException ex) {
			throw new SmartScriptParserException();
		}
	}

	public DocumentNode getDocumentNode() {
		return document;
	}

}
