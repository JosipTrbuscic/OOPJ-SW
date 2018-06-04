package hr.fer.zemris.java.custom.scripting.demo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Demonstration program used to demonstrate how visitor design pattern works
 * @author Josip Trbuscic
 *
 */
public class TreeWriter {

	/**
	 * Main method
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if(args.length != 1) {
			System.out.println("This program accepts only one argument");
			System.exit(0);
		}
		
		File file = Paths.get(args[0]).toFile();
		if(!file.exists() && file.isDirectory()) {
			System.out.println("Path must represent an existing file");
			System.exit(0);
		}
		
		String docBody = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
		SmartScriptParser parser = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		parser.getDocumentNode().accept(visitor);
		
	}
	
	/**
	 * Implementation of {@link INodeVisitor} which prints nodes to 
	 * standard output 
	 * @author Josip Trbuscic
	 *
	 */
	private static class WriterVisitor implements INodeVisitor{

		@Override
		public void visitTextNode(TextNode node) {
			System.out.println(node);
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.println(node);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.println(node);
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			System.out.println(node);
		}
		
	}

}
