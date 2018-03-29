package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.collections.ArrayIndexedCollection;

/**
 * This class represents node in a parsed document tree. References to 
 * child nodes are stored in {@code ArrayIndexedCollection}. Class provides 
 * methods for getting child node at specified index and method which returns 
 * number of child nodes. 
 * @author Josip Trbuscic
 *
 */
public class Node {
	private ArrayIndexedCollection nodesArray; //Stores child nodes
	
	/**
	 * Adds new child node to the array. First call of this method
	 * will create new array and initialize it with default size.
	 * @param child node to add
	 */
	public void addChildNode(Node child) {
		if(nodesArray == null) {
			nodesArray = new ArrayIndexedCollection();
		}
		
		nodesArray.add(child);
	}
	
	/**
	 * @return number of child nodes 
	 */
	public int numberOfChildren() {
		if(nodesArray==null) return 0; 
		return nodesArray.size();
	}
	
	/**
	 * Returns child node at specified index
	 * @param index of child node
	 * @return child node at specified index
	 * @throws IndexOutOfBoundsException if index is out of array bounds
	 */
	public Node getChild(int index) {
		return (Node)nodesArray.get(index);
	}
	
}
