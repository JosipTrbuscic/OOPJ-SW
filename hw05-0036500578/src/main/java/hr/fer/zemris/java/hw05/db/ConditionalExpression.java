package hr.fer.zemris.java.hw05.db;

/**
 * Representation of a conditional expression which consists of a 
 * attribute name, operator and string literal. 
 * @author Josip Trbuscic
 */
public class ConditionalExpression {
	
	/**
	 * Comparison operator
	 */
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Getter of a student record's attribute
	 */
	private IFieldValueGetter fieldGetter;
	
	/**
	 * String literal
	 */
	private String stringLiteral;

	/**
	 * Constructs new conditional expression from given arguments.
	 * @param getter - getter of a student record attribute
	 * @param literal - String literal
	 * @param operator - comparison operator
	 */
	public ConditionalExpression(IFieldValueGetter getter, String literal, IComparisonOperator operator) {
		if(operator == null) throw new NullPointerException("Operator cannot be null");
		if(getter == null) throw new NullPointerException("Getter cannot be null");
		if(literal == null) throw new NullPointerException("Literal cannot be null");
		
		this.comparisonOperator = operator;
		this.fieldGetter = getter;
		this.stringLiteral = literal;
	}
	
	/**
	 * Returns comparison operator
	 * @return comparison operator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	/**
	 * Returns attribute getter
	 * @return attribute gettter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Returns string literal
	 * @return string literal
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}
	
}
