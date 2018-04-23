package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class ComparisonOperatorsTest {

	@Test
	public void likeNormal() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		
		assertEquals(false,oper.satisfied("Zagreb", "Aba*"));
		assertEquals(false,oper.satisfied("AAA", "AA*AA   "));
		assertEquals(true,oper.satisfied("AAAA", "AA*AA"));
		assertEquals(false,oper.satisfied("test", "Test"));
		assertEquals(true,oper.satisfied("test", "test*"));
		assertEquals(true,oper.satisfied("", "*"));
		assertEquals(true,oper.satisfied("java", "*java"));
		assertEquals(true,oper.satisfied("Simple string", "Simple*"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void multipleAsterisk() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		
		oper.satisfied("SImple string", "**Aba");
	}
	
	@Test
	public void lessNormal() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		
		assertEquals(false,oper.satisfied("Zagreb", "Aba"));
		assertEquals(true,oper.satisfied("AAA", "AAA   "));
		assertEquals(false,oper.satisfied("AAAA", "AAAA"));
		assertEquals(false,oper.satisfied("test", "Test"));
		assertEquals(true,oper.satisfied("", "string"));
		assertEquals(false,oper.satisfied("string", ""));
		assertEquals(false,oper.satisfied("Simple string", "Simple"));
	}
	
	@Test
	public void lessEqualNormal() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		
		assertEquals(false,oper.satisfied("Zagreb", "Aba"));
		assertEquals(true,oper.satisfied("AAA", "AAA   "));
		assertEquals(true,oper.satisfied("AAAA", "AAAA"));
		assertEquals(false,oper.satisfied("test", "Test"));
		assertEquals(true,oper.satisfied("", "string"));
		assertEquals(false,oper.satisfied("string", ""));
		assertEquals(true,oper.satisfied("Simple string", "Simple string"));
	}
	
	@Test
	public void greaterNormal() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		
		assertEquals(true,oper.satisfied("Zagreb", "Aba"));
		assertEquals(false,oper.satisfied("AAA", "AAA   "));
		assertEquals(false,oper.satisfied("AAAA", "AAAA"));
		assertEquals(true,oper.satisfied("test", "Test"));
		assertEquals(false,oper.satisfied("", "string"));
		assertEquals(true,oper.satisfied("string", ""));
		assertEquals(true,oper.satisfied("Simple string", "Simple"));
	}
	
	@Test
	public void greaterEqualsNormal() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		
		assertEquals(true,oper.satisfied("Zagreb", "Aba"));
		assertEquals(false,oper.satisfied("AAA", "AAA   "));
		assertEquals(true,oper.satisfied("AAAA", "AAAA"));
		assertEquals(true,oper.satisfied("test", "Test"));
		assertEquals(false,oper.satisfied("", "string"));
		assertEquals(true,oper.satisfied("string", ""));
		assertEquals(true,oper.satisfied("Simple string", "Simple string"));
	}
	
	@Test
	public void equalsNormal() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		
		assertEquals(true,oper.satisfied("Zagreb", "Zagreb"));
		assertEquals(false,oper.satisfied("AAA", "AAA   "));
		assertEquals(true,oper.satisfied("AAAA", "AAAA"));
		assertEquals(false,oper.satisfied("test", "Test"));
		assertEquals(false,oper.satisfied("", "string"));
		assertEquals(false,oper.satisfied("str ing", ""));
		assertEquals(true,oper.satisfied("Simple string", "Simple string"));
	}
	
	@Test
	public void notEqualsNormal() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		
		assertEquals(false,oper.satisfied("Zagreb", "Zagreb"));
		assertEquals(true,oper.satisfied("AAA", "AAA   "));
		assertEquals(false,oper.satisfied("AAAA", "AAAA"));
		assertEquals(true,oper.satisfied("test", "Test"));
		assertEquals(true,oper.satisfied("", "string"));
		assertEquals(true,oper.satisfied("str ing", ""));
		assertEquals(false,oper.satisfied("Simple string", "Simple string"));
	}

}
