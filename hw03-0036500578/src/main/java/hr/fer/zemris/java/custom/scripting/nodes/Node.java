package hr.fer.zemris.java.custom.scripting.nodes;


public class Node {
	private ArrayIndexedCollection nodesArray;
	
	public void addChildNode(Node child) {
		if(nodesArray == null) {
			nodesArray = new ArrayIndexedCollection();
		}
		
		nodesArray.add(child);
	}
	
	public int numberOfChildren() {
		if(nodesArray==null) return 0; 
		return nodesArray.size();
	}
	
	public Node getChild(int index) {
		return (Node)nodesArray.get(index);
	}
	
}
