package hr.fer.zemris.java.hw03;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Tester for parser that reads string from given file
 * 
 * @author Josip Trbuscic
 *
 */
public class SmartScriptTester {

	/**
	 * Reads file from given path and starts the parser
	 * 
	 * @throws IOException
	 *             if file with given name cannot be read
	 */
	// public static void main(String[] args) throws IOException {
	// String docBody = new
	// String(Files.readAllBytes(Paths.get("src/test/resources/test3.txt")),
	// StandardCharsets.UTF_8);
	//
	// SmartScriptParser parser = null;
	// try {
	// parser = new SmartScriptParser(docBody);
	// } catch (
	//
	// SmartScriptParserException e) {
	// System.out.println("Unable to parse document!");
	// System.exit(-1);
	// } catch (Exception e) {
	// System.out.println("If this line ever executes, you have failed this
	// class!");
	// System.exit(-1);
	// }
	// DocumentNode document = parser.getDocumentNode();
	// String originalDocumentBody = createOriginalDocumentBody(document);
	// System.out.println(originalDocumentBody);
	// }
	public static void main(String[] args) throws IOException {

		String docBody = new String(Files.readAllBytes(Paths.get("src/test/resources/test.txt")),
				StandardCharsets.UTF_8);

		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (

		SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception ex) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody);
		System.out.println("");

		System.out.println();

		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBody2 = createOriginalDocumentBody(document2);
		System.out.println(originalDocumentBody2);

		System.out.println("The document and the text from the parsed document are the same:  "
				+ originalDocumentBody.equals(originalDocumentBody2));
	}

	/**
	 * Method will create recreate original document from tree that contains parsed
	 * parts
	 * 
	 * @param node
	 *            - head of tree
	 * @return original document
	 */
	public static String createOriginalDocumentBody(Node node) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while (i < node.numberOfChildren()) {
			Node child = node.getChild(i);

			if (child instanceof ForLoopNode) {
				sb.append(buildFor((ForLoopNode) child));
			} else if (child instanceof TextNode) {
				sb.append(buildText((TextNode) child));
			} else {
				sb.append(buildEcho((EchoNode) child));
			}

			i++;
		}
		return sb.toString();
	}

	/**
	 * Method that creates string that represents For tag of given node
	 * 
	 * @param node
	 *            to recreate
	 * @return string representation of given node
	 */
	private static String buildFor(ForLoopNode node) {
		StringBuilder sb = new StringBuilder();
		sb.append("{$ FOR");
		sb.append(" " + node.getVariable().asText());
		sb.append(" " + node.getStartExpression().asText());
		sb.append(" " + node.getEndExpression().asText());

		if (node.getStepExpression() != null) {
			sb.append(" " + node.getStepExpression().asText());
		}
		sb.append(" $}");

		int i = 0;
		while (i < node.numberOfChildren()) {
			Node child = node.getChild(i);

			if (child instanceof ForLoopNode) {
				sb.append(buildFor((ForLoopNode) child));
			} else if (child instanceof TextNode) {
				sb.append(buildText((TextNode) child));
			} else {
				sb.append(buildEcho((EchoNode) child));
			}
			i++;
		}
		sb.append("{$ END $}");

		return sb.toString();
	}

	/**
	 *
	 * Method that creates string that represents Empty tag of given node
	 * 
	 * @param node
	 *            to recreate
	 * @return string representation of given node
	 */
	private static String buildEcho(EchoNode node) {
		StringBuilder sb = new StringBuilder("{$");
		for (Element el : node.getElements()) {
			sb.append(" " + el.asText());

		}

		sb.append(" $}");
		return sb.toString();
	}

	/**
	 *
	 * Method that creates string that represents Empty tag of given node
	 * 
	 * @param node
	 *            to recreate
	 * @return string representation of given node
	 */
	private static String buildText(TextNode node) {
		String s = node.getText().asText().replace("\\", "\\");
		s = node.getText().asText().replace("{$", "{$");
		return s;
	}

}
