package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class ConditionalExpressionTest {
	List<StudentRecord> list;
	ConditionalExpression expr;
	
	@Before
	public void setup() {
		list = new ArrayList<>();
		list.add(new StudentRecord("0000000001",	"Akšamović",	"Marin",	"2"));
		list.add(new StudentRecord("0000000002",	"Bakamović",	"Petra",	"2"));
		list.add(new StudentRecord("0000000005",	"Jurina",	"Filip",	"2"));
		
		expr = new ConditionalExpression(
				  FieldValueGetters.LAST_NAME,
				  "*ić",
				  ComparisonOperators.LIKE
				);
	}
	
	@Test
	public void fieldGetter() {
		assertEquals("Akšamović", expr.getFieldGetter().get(list.get(0)));
		assertEquals("Bakamović", expr.getFieldGetter().get(list.get(1)));
		assertEquals("Jurina", expr.getFieldGetter().get(list.get(2)));
	}
	
	@Test
	public void comparisonOperator() {
		assertEquals(true, expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(list.get(0)),
				expr.getStringLiteral())
				);
		
		assertEquals(true, expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(list.get(1)),
				expr.getStringLiteral())
				);
		
		assertEquals(false, expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(list.get(2)),
				expr.getStringLiteral())
				);
	}
	
	@Test
	public void literalGetter() {
		assertEquals("*ić", expr.getStringLiteral());
	}
	
	

}
