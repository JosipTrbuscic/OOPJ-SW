package hr.fer.zemris.java.custom.scripting.parser;

import java.util.Arrays;
import java.util.EmptyStackException;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.ObjectStack;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

public class SmartScriptParser {
	private DocumentNode document;
	private ObjectStack stack = new ObjectStack();

	public SmartScriptParser(String document) {
		if (document == null)
			throw new NullPointerException();

		this.document = new DocumentNode();
		parse(new Lexer(document));
	}

	public void parse(Lexer lexer) {
		stack.push(document);

		while (true) {
			Token token = lexer.nextToken();
			Node node;

			if (token.getType().equals(TokenType.EOF)) {
				break;
			}

			if (token.getType().equals(TokenType.TEXT)) {
				String s = (String) token.getValue();
				ElementString el = new ElementString(s);

				node = new TextNode(el);
				Node parent = (Node) stack.peek();

				parent.addChildNode(node);

				continue;

			} else if (token.getType().equals(TokenType.SOT)) {
				lexer.setState(LexerState.TAG_STATE);
				parseTag(lexer);
				lexer.setState(LexerState.TEXT_STATE);
			}else {
				throw new SmartScriptParserException("Invalid document");
			}
		}

	}

	private void parseTag(Lexer lexer) {
		ArrayIndexedCollection params = new ArrayIndexedCollection();
		Node node;
		
		Token token = lexer.nextToken();
		while (!token.getType().equals(TokenType.EOT)) {
			params.add(token);
			token = lexer.nextToken();
		}

		Token tagName = (Token) params.get(0);

		if (tagName.getValue().equals("=")) {
			node = parseEmptyTag(params);
			
			Node parent = (Node) stack.peek();

			parent.addChildNode(node);
		} else if (tagName.getValue().toString().toLowerCase().equals("for")) {
			node = parseForTag(params);
			
			Node parent = (Node) stack.peek();

			parent.addChildNode(node);
			stack.push(node);

		} else if (tagName.getValue().toString().toLowerCase().equals("end")) {
			if (params.size() != 1)
				throw new SmartScriptParserException();

			try {
				stack.pop();
				if (stack.isEmpty()) {
					throw new SmartScriptParserException("Too many END tags");
				}
			} catch (EmptyStackException ex) {
				throw new SmartScriptParserException("Too many END tags");
			}

		} else {
			throw new SmartScriptParserException();
		}

	}

	private Node parseEmptyTag(ArrayIndexedCollection params) {
		Element[] elements = new Element[params.size()];

		for (int i = 1, size = params.size(); i < size; ++i) {
			Token token = (Token) params.get(i);

			if (token.getType().equals(TokenType.DOUBLE)) {
				elements[i] = new ElementConstantDouble((Double) token.getValue());
				
			} else if (token.getType().equals(TokenType.INTEGER)) {
				elements[i] = new ElementConstantInteger((Integer) token.getValue());
				
			} else if (token.getType().equals(TokenType.FUNCTION)) {
				elements[i] = new ElementFunction((String) token.getValue());
				
			} else if (token.getType().equals(TokenType.OPERATOR)) {
				elements[i] = new ElementOperator((String) token.getValue());
				
			} else if (token.getType().equals(TokenType.TEXT)) {
				elements[i] = new ElementString((String) token.getValue());
				
			} else if (token.getType().equals(TokenType.VARIABLE)) {
				elements[i] = new ElementVariable((String) token.getValue());
				
			} else {
				throw new SmartScriptParserException("Illegal Empty tag argument");
			}
		}
		
		return new EchoNode(elements);

	}

	private ForLoopNode parseForTag(ArrayIndexedCollection params) {
		if (params.size() != 4 && params.size() != 5)
			throw new SmartScriptParserException("Invalid FOR parameters count");

		if (checkForArgs(params, params.size())) {
			Token first = (Token) params.get(1);
			Token second = (Token) params.get(2);
			Token third = (Token) params.get(3);
			Token fourth = null;

			if (params.size() == 4) {;

				return new ForLoopNode((ElementVariable) first.getValue(), (Element) second.getValue(),
						(Element) third.getValue());
			} else {
				fourth = (Token) params.get(4);

				return new ForLoopNode((ElementVariable) first.getValue(), (Element) second.getValue(),
						(Element) third.getValue(), (Element) fourth.getValue());
			}
		}
		throw new SmartScriptParserException("Invalid FOR parameters");

	}

	private boolean checkForArgs(ArrayIndexedCollection params, int size) {

		for (int i = 1; i < size; ++i) {
			Token token = (Token) params.get(i);

			if (i == 1 && token.getType().equals(TokenType.VARIABLE)) {
				throw new SmartScriptParserException("Invalid FOR argument");
			}

			if (token.getType().equals(TokenType.OPERATOR) || token.getType().equals(TokenType.FUNCTION)) {
				throw new SmartScriptParserException("Invalid FOR argument");
			}
		}

		return true;
	}
	
	
	
	public DocumentNode getDocumentNode() {
		return document;
	}

}
