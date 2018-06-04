package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
/**
 * This class represents node of parsed document tree.
 * Class contains {@code ElementVariable}, {@code Element},
 * {@code Element} and {@code Element} attributes.
 *@author Josip Trbuscic
 */
public class ForLoopNode extends Node {
	private ElementVariable variable; // For loop variable
	private Element startExpression; // For loop start expression
	private Element endExpression; // For loop end expression
	private Element stepExpression; // For loop step expression
	
	/**
	 * Constructs new {@code ForLoopNode} specified by
	 * 4 arguments. Step expression can be null 
	 * @param variable 
	 * @param startExpression - start expression
	 * @param endExpression - end expression
	 * @param stepExpression - step expression
	 * @throws NullPointerException if invalid argument is given.
	 */
	public ForLoopNode(ElementVariable variable,
						Element startExpression,
						Element endExpression,
						Element stepExpression) {
		
		if(variable == null ||
		   startExpression == null ||
		   endExpression == null) {
			throw new NullPointerException();
		}
		
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * Constructs new {@code ForLoopNode} specified by
	 * 3 arguments.
	 * @param variable 
	 * @param startExpression - start expression
	 * @param endExpression - end expression
	 * @throws NullPointerException if invalid argument is given.
	 */
	public ForLoopNode(ElementVariable variable,
						Element startExpression,
						Element endExpression) {
		
		this(variable, startExpression, endExpression, null);
	}
	
	/**
	 * @return variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	
	/**
	 * @return start expression
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	
	/**
	 * @return end expression
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	
	/**
	 * @return step expression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	/**
	 * Returns string representation of this node
	 * @return string representation of this node
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$ FOR");
		sb.append(" " + getVariable().asText());
		sb.append(" " + getStartExpression().asText());
		sb.append(" " + getEndExpression().asText());

		if (getStepExpression() != null) {
			sb.append(" " + getStepExpression().asText());
		}
		sb.append(" $}");

		int i = 0;
		while (i < numberOfChildren()) {
			Node child = getChild(i);

			sb.append(child.toString());
			i++;
		}
		sb.append("{$ END $}");

		return sb.toString();
	}
	
}
