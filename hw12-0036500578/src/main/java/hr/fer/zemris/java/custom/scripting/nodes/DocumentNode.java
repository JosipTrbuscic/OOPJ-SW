package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * This class represents head of parsed document tree
 * @author Josip Trbuscic
 *
 */
public class DocumentNode extends Node {

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while(i < numberOfChildren()) {
			sb.append(getChild(i).toString());
			i++;
		}
		return sb.toString();
	}
	
}
