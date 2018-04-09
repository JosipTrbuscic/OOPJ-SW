package hr.fer.zemris.java.hw05.db;

public class ConditionalExpression {
	private IComparisonOperator comparisonOperator;
	private IFieldValueGetter fieldGetter;
	private String stringLiteral;

	public ConditionalExpression(IFieldValueGetter getter, String literal, IComparisonOperator operator) {
		if(operator == null) throw new NullPointerException("Operator cannot be null");
		if(getter == null) throw new NullPointerException("Getter cannot be null");
		if(literal == null) throw new NullPointerException("Literal cannot be null");
		
		this.comparisonOperator = operator;
		this.fieldGetter = getter;
		this.stringLiteral = literal;
	}

	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	public String getStringLiteral() {
		return stringLiteral;
	}
	
	public static void main(String[] args) {
		ConditionalExpression expr = new ConditionalExpression(
				  FieldValueGetters.LAST_NAME,
				  "Bos*",
				  ComparisonOperators.LIKE
				);
				StudentRecord record = new StudentRecord("0036500578", "BosTrbuscic", "Josip", "1");
				boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				  expr.getFieldGetter().get(record),  // returns lastName from given record
				  expr.getStringLiteral()             // returns "Bos*"
				);
				System.out.println(recordSatisfies);
	}
}
