package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Interface which defines methods which enables implementing class 
 * to process nodes generated by {@link SmartScriptParser}
 * @author Josip Trbuscic
 *
 */
public interface INodeVisitor {
	
	/**
	 * Processes given {@link TextNode}
	 * @param node - node which is being visited
	 */
	public void visitTextNode(TextNode node);
	
	/**
	 * Processes given {@link ForLoopNode}
	 * @param node - node which is being visited
	 */
	public void visitForLoopNode(ForLoopNode node);
	
	/**
	 * Processes given {@link EchoNode}
	 * @param node - node which is being visited
	 */
	public void visitEchoNode(EchoNode node);
	
	/**
	 * Processes given {@link DocumentNode}
	 * @param node - node which is being visited
	 */
	public void visitDocumentNode(DocumentNode node);
}
