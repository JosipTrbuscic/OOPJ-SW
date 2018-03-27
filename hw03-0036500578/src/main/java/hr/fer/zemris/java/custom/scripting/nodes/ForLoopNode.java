package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

public class ForLoopNode extends Node {
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;
	
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
	
	public ForLoopNode(ElementVariable variable,
						Element startExpression,
						Element endExpression) {
		this(variable, startExpression, endExpression, null);
	}
	
	
	public ElementVariable getVariable() {
		return variable;
	}
	public Element getStartExpression() {
		return startExpression;
	}
	public Element getEndExpression() {
		return endExpression;
	}
	public Element getStepExpression() {
		return stepExpression;
	}
	
	
	
}
