package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValueWrapperTest {
	@Test
	public void addPositiveIntegers() {
		ValueWrapper v1 = new ValueWrapper(5);
		ValueWrapper v2 = new ValueWrapper(10);
		v1.add(v2.getValue());	
		assertEquals(15, v1.getValue());
		assertEquals(10, v2.getValue());
		assertEquals(Integer.class, v1.getValue().getClass());
		assertEquals(Integer.class, v2.getValue().getClass());
	}
	
	@Test
	public void addNegativeAndPositiveIntegers() {
		ValueWrapper v1 = new ValueWrapper(-15);
		ValueWrapper v2 = new ValueWrapper(30);
		v1.add(v2.getValue());	
		assertEquals(15, v1.getValue());
		assertEquals(30, v2.getValue());
		assertEquals(Integer.class, v1.getValue().getClass());
		assertEquals(Integer.class, v2.getValue().getClass());
	}
	
	@Test
	public void addNegativeIntegers() {
		ValueWrapper v1 = new ValueWrapper(-15);
		ValueWrapper v2 = new ValueWrapper(-40);
		v1.add(v2.getValue());	
		assertEquals(-55, v1.getValue());
		assertEquals(-40, v2.getValue());
		assertEquals(Integer.class, v1.getValue().getClass());
		assertEquals(Integer.class, v2.getValue().getClass());
	}
	
	@Test
	public void addIntegersWhereOneIsZero() {
		ValueWrapper v1 = new ValueWrapper(-15);
		ValueWrapper v2 = new ValueWrapper(0);
		v1.add(v2.getValue());	
		assertEquals(-15, v1.getValue());
		assertEquals(0, v2.getValue());
		assertEquals(Integer.class, v1.getValue().getClass());
		assertEquals(Integer.class, v2.getValue().getClass());
	}
	
	@Test
	public void addIntegersBothZero() {
		ValueWrapper v1 = new ValueWrapper(0);
		ValueWrapper v2 = new ValueWrapper(0);
		v1.add(v2.getValue());	
		assertEquals(0, v1.getValue());
		assertEquals(0, v2.getValue());
		assertEquals(Integer.class, v1.getValue().getClass());
		assertEquals(Integer.class, v2.getValue().getClass());
	}
	
	@Test
	public void addIntegersFirstIsNull() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(5);
		v1.add(v2.getValue());	
		assertEquals(5, v1.getValue());
		assertEquals(5, v2.getValue());
		assertEquals(Integer.class, v1.getValue().getClass());
		assertEquals(Integer.class, v2.getValue().getClass());
		
		ValueWrapper v3 = new ValueWrapper(null);
		ValueWrapper v4 = new ValueWrapper(0);
		v3.add(v4.getValue());	
		assertEquals(0, v3.getValue());
		assertEquals(0, v4.getValue());
		assertEquals(Integer.class, v3.getValue().getClass());
		assertEquals(Integer.class, v4.getValue().getClass());
		
		ValueWrapper v5 = new ValueWrapper(null);
		ValueWrapper v6 = new ValueWrapper(-5);
		v5.add(v6.getValue());	
		assertEquals(-5, v5.getValue());
		assertEquals(-5, v6.getValue());
		assertEquals(Integer.class, v5.getValue().getClass());
		assertEquals(Integer.class, v6.getValue().getClass());
	}
	
	@Test
	public void addIntegersSecondIsNull() {
		ValueWrapper v1 = new ValueWrapper(5);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());	
		assertEquals(5, v1.getValue());
		assertEquals(null, v2.getValue());
		assertEquals(Integer.class, v1.getValue().getClass());
		assertEquals(null, v2.getValue());
		
		ValueWrapper v3 = new ValueWrapper(0);
		ValueWrapper v4 = new ValueWrapper(null);
		v3.add(v4.getValue());	
		assertEquals(0, v3.getValue());
		assertEquals(null, v4.getValue());
		assertEquals(Integer.class, v3.getValue().getClass());
		assertEquals(null, v2.getValue());
		
		ValueWrapper v5 = new ValueWrapper(-5);
		ValueWrapper v6 = new ValueWrapper(null);
		v5.add(v6.getValue());	
		assertEquals(-5, v5.getValue());
		assertEquals(null, v6.getValue());
		assertEquals(Integer.class, v5.getValue().getClass());
		assertEquals(null, v2.getValue());
	}
	
	@Test
	public void addPositiveDoubles() {
		ValueWrapper v1 = new ValueWrapper(5.5);
		ValueWrapper v2 = new ValueWrapper(10.45);
		v1.add(v2.getValue());	
		assertEquals(15.95, v1.getValue());
		assertEquals(10.45, v2.getValue());
		assertEquals(Double.class, v1.getValue().getClass());
		assertEquals(Double.class, v2.getValue().getClass());
	}
	
	@Test
	public void addNegativeAndPositiveDoubles() {
		ValueWrapper v1 = new ValueWrapper(-15.458);
		ValueWrapper v2 = new ValueWrapper(30.46);
		v1.add(v2.getValue());	
		assertEquals(15.002, v1.getValue());
		assertEquals(30.46, v2.getValue());
		assertEquals(Double.class, v1.getValue().getClass());
		assertEquals(Double.class, v2.getValue().getClass());
	}
	
	@Test
	public void addNegativeDoubles() {
		ValueWrapper v1 = new ValueWrapper(-15.89);
		ValueWrapper v2 = new ValueWrapper(-40.13);
		v1.add(v2.getValue());	
		assertEquals(-56.02, v1.getValue());
		assertEquals(-40.13, v2.getValue());
		assertEquals(Double.class, v1.getValue().getClass());
		assertEquals(Double.class, v2.getValue().getClass());
	}
	
	@Test
	public void addDoublesWhereOneIsZero() {
		ValueWrapper v1 = new ValueWrapper(-15.5889);
		ValueWrapper v2 = new ValueWrapper(0.0);
		v1.add(v2.getValue());	
		assertEquals(-15.5889, v1.getValue());
		assertEquals(0.0, v2.getValue());
		assertEquals(Double.class, v1.getValue().getClass());
		assertEquals(Double.class, v2.getValue().getClass());
	}
	
	@Test
	public void addDoublesBothZero() {
		ValueWrapper v1 = new ValueWrapper(0.0);
		ValueWrapper v2 = new ValueWrapper(0.0);
		v1.add(v2.getValue());	
		assertEquals(0.0, v1.getValue());
		assertEquals(0.0, v2.getValue());
		assertEquals(Double.class, v1.getValue().getClass());
		assertEquals(Double.class, v2.getValue().getClass());
	}
	
	@Test
	public void addDoublesFirstIsNull() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(5.7);
		v1.add(v2.getValue());	
		assertEquals(5.7, v1.getValue());
		assertEquals(5.7, v2.getValue());
		assertEquals(Double.class, v1.getValue().getClass());
		assertEquals(Double.class, v2.getValue().getClass());
		
		ValueWrapper v3 = new ValueWrapper(null);
		ValueWrapper v4 = new ValueWrapper(0.0);
		v3.add(v4.getValue());	
		assertEquals(0.0, v3.getValue());
		assertEquals(0.0, v4.getValue());
		assertEquals(Double.class, v3.getValue().getClass());
		assertEquals(Double.class, v4.getValue().getClass());
		
		ValueWrapper v5 = new ValueWrapper(null);
		ValueWrapper v6 = new ValueWrapper(-5.67);
		v5.add(v6.getValue());	
		assertEquals(-5.67, v5.getValue());
		assertEquals(-5.67, v6.getValue());
		assertEquals(Double.class, v5.getValue().getClass());
		assertEquals(Double.class, v6.getValue().getClass());
	}
	
	@Test
	public void addDoublesSecondIsNull() {
		ValueWrapper v1 = new ValueWrapper(5.15);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());	
		assertEquals(5.15, v1.getValue());
		assertEquals(null, v2.getValue());
		assertEquals(Double.class, v1.getValue().getClass());
		assertEquals(null, v2.getValue());
		
		ValueWrapper v3 = new ValueWrapper(0.0);
		ValueWrapper v4 = new ValueWrapper(null);
		v3.add(v4.getValue());	
		assertEquals(0.0, v3.getValue());
		assertEquals(null, v4.getValue());
		assertEquals(Double.class, v3.getValue().getClass());
		assertEquals(null, v2.getValue());
		
		ValueWrapper v5 = new ValueWrapper(-5.35);
		ValueWrapper v6 = new ValueWrapper(null);
		v5.add(v6.getValue());	
		assertEquals(-5.35, v5.getValue());
		assertEquals(null, v6.getValue());
		assertEquals(Double.class, v5.getValue().getClass());
		assertEquals(null, v2.getValue());
	}
	
	@Test
	public void addPositiveDoublePositiveInt() {
		ValueWrapper v1 = new ValueWrapper(5);
		ValueWrapper v2 = new ValueWrapper(10.45);
		v1.add(v2.getValue());	
		assertEquals(15.45, v1.getValue());
		assertEquals(10.45, v2.getValue());
		assertEquals(Double.class, v1.getValue().getClass());
		assertEquals(Double.class, v2.getValue().getClass());
		
		ValueWrapper v3 = new ValueWrapper(10.45);
		ValueWrapper v4 = new ValueWrapper(5);
		v3.add(v4.getValue());	
		assertEquals(15.45, v3.getValue());
		assertEquals(5, v4.getValue());
		assertEquals(Double.class, v3.getValue().getClass());
		assertEquals(Integer.class, v4.getValue().getClass());
	}
	
	@Test
	public void addNegativeAndPositiveIntAndDouble() {
		ValueWrapper v1 = new ValueWrapper(-15);
		ValueWrapper v2 = new ValueWrapper(30.46);
		v1.add(v2.getValue());	
		assertEquals(15.46, v1.getValue());
		assertEquals(30.46, v2.getValue());
		assertEquals(Double.class, v1.getValue().getClass());
		assertEquals(Double.class, v2.getValue().getClass());
		
		ValueWrapper v3 = new ValueWrapper(10.45);
		ValueWrapper v4 = new ValueWrapper(-5);
		v3.add(v4.getValue());	
		assertEquals(Double.valueOf(10.45-5), v3.getValue());
		assertEquals(-5, v4.getValue());
		assertEquals(Double.class, v3.getValue().getClass());
		assertEquals(Integer.class, v4.getValue().getClass());
	}
	
	@Test
	public void addNegativeDoubleAndInt() {
		ValueWrapper v1 = new ValueWrapper(-15);
		ValueWrapper v2 = new ValueWrapper(-40.13);
		v1.add(v2.getValue());	
		assertEquals(-55.13, v1.getValue());
		assertEquals(-40.13, v2.getValue());
		assertEquals(Double.class, v1.getValue().getClass());
		assertEquals(Double.class, v2.getValue().getClass());
		
		ValueWrapper v3 = new ValueWrapper(-10.45);
		ValueWrapper v4 = new ValueWrapper(-5);
		v3.add(v4.getValue());	
		assertEquals(-15.45, v3.getValue());
		assertEquals(-5, v4.getValue());
		assertEquals(Double.class, v3.getValue().getClass());
		assertEquals(Integer.class, v4.getValue().getClass());
	}
	
	@Test
	public void addDoubleZeroAndIntZero() {
		ValueWrapper v1 = new ValueWrapper(0.0);
		ValueWrapper v2 = new ValueWrapper(0);
		v1.add(v2.getValue());	
		assertEquals(0.0, v1.getValue());
		assertEquals(0, v2.getValue());
		assertEquals(Double.class, v1.getValue().getClass());
		assertEquals(Integer.class, v2.getValue().getClass());
	}
	
	@Test
	public void addPositiveIntegersAsString() {
		ValueWrapper v1 = new ValueWrapper("5");
		ValueWrapper v2 = new ValueWrapper("10");
		v1.add(v2.getValue());	
		assertEquals(15, v1.getValue());
		assertEquals("10", v2.getValue());
		assertEquals(Integer.class, v1.getValue().getClass());
		assertEquals(String.class, v2.getValue().getClass());
	}
	
	@Test
	public void addNegativeAndPositiveIntegersAsStrings() {
		ValueWrapper v1 = new ValueWrapper("-15");
		ValueWrapper v2 = new ValueWrapper("30");
		v1.add(v2.getValue());	
		assertEquals(15, v1.getValue());
		assertEquals("30", v2.getValue());
		assertEquals(Integer.class, v1.getValue().getClass());
		assertEquals(String.class, v2.getValue().getClass());
	}
	
	@Test
	public void addNegativeIntegersAsStrings() {
		ValueWrapper v1 = new ValueWrapper("-15");
		ValueWrapper v2 = new ValueWrapper("-40");
		v1.add(v2.getValue());	
		assertEquals(-55, v1.getValue());
		assertEquals("-40", v2.getValue());
		assertEquals(Integer.class, v1.getValue().getClass());
		assertEquals(String.class, v2.getValue().getClass());
	}
	
	@Test
	public void addIntegersWhereOneIsZeroAsStrings() {
		ValueWrapper v1 = new ValueWrapper(-15);
		ValueWrapper v2 = new ValueWrapper("0");
		v1.add(v2.getValue());	
		assertEquals(-15, v1.getValue());
		assertEquals("0", v2.getValue());
		assertEquals(Integer.class, v1.getValue().getClass());
		assertEquals(String.class, v2.getValue().getClass());
	}
	
	@Test
	public void addIntegersFirstIsNullAsStrings() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper("5");
		v1.add(v2.getValue());	
		assertEquals(5, v1.getValue());
		assertEquals("5", v2.getValue());
		assertEquals(Integer.class, v1.getValue().getClass());
		assertEquals(String.class, v2.getValue().getClass());
		
		ValueWrapper v3 = new ValueWrapper(null);
		ValueWrapper v4 = new ValueWrapper("0");
		v3.add(v4.getValue());	
		assertEquals(0, v3.getValue());
		assertEquals("0", v4.getValue());
		assertEquals(Integer.class, v3.getValue().getClass());
		assertEquals(String.class, v4.getValue().getClass());
		
		ValueWrapper v5 = new ValueWrapper(null);
		ValueWrapper v6 = new ValueWrapper("-5");
		v5.add(v6.getValue());	
		assertEquals(-5, v5.getValue());
		assertEquals("-5", v6.getValue());
		assertEquals(Integer.class, v5.getValue().getClass());
		assertEquals(String.class, v6.getValue().getClass());
	}
	
	@Test
	public void addIntegersSecondIsNullasStrings() {
		ValueWrapper v1 = new ValueWrapper("5");
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());	
		assertEquals(5, v1.getValue());
		assertEquals(null, v2.getValue());
		assertEquals(Integer.class, v1.getValue().getClass());
		assertEquals(null, v2.getValue());
		
		ValueWrapper v3 = new ValueWrapper("0");
		ValueWrapper v4 = new ValueWrapper(null);
		v3.add(v4.getValue());	
		assertEquals(0, v3.getValue());
		assertEquals(null, v4.getValue());
		assertEquals(Integer.class, v3.getValue().getClass());
		assertEquals(null, v4.getValue());
		
		ValueWrapper v5 = new ValueWrapper("-5");
		ValueWrapper v6 = new ValueWrapper(null);
		v5.add(v6.getValue());	
		assertEquals(-5, v5.getValue());
		assertEquals(null, v6.getValue());
		assertEquals(Integer.class, v5.getValue().getClass());
		assertEquals(null, v6.getValue());
	}
	
	@Test
	public void addNumbersScientificasStrings() {
		ValueWrapper v1 = new ValueWrapper("5");
		ValueWrapper v2 = new ValueWrapper("0.5E4");
		v1.add(v2.getValue());	
		assertEquals(5005.0, v1.getValue());
		assertEquals("0.5E4", v2.getValue());
		assertEquals(Double.class, v1.getValue().getClass());
		assertEquals(String.class, v2.getValue().getClass());
		
		ValueWrapper v3 = new ValueWrapper("0");
		ValueWrapper v4 = new ValueWrapper("45E2");
		v3.add(v4.getValue());	
		assertEquals(4500.0, v3.getValue());
		assertEquals("45E2", v4.getValue());
		assertEquals(Double.class, v3.getValue().getClass());
		assertEquals(String.class, v4.getValue().getClass());
		
		ValueWrapper v5 = new ValueWrapper("-5");
		ValueWrapper v6 = new ValueWrapper("-5E-1");
		v5.add(v6.getValue());	
		assertEquals(-5.5, v5.getValue());
		assertEquals("-5E-1", v6.getValue());
		assertEquals(Double.class, v5.getValue().getClass());
		assertEquals(String.class, v6.getValue().getClass());
	}
	
	@Test
	public void mulTests() {
		ValueWrapper v1 = new ValueWrapper("5");
		v1.multiply("5.4");
		assertEquals(27.0, v1.getValue());
		assertEquals(Double.class, v1.getValue().getClass());
		
		ValueWrapper v2 = new ValueWrapper("5.5");
		v2.multiply("5.4");
		assertEquals(Double.valueOf(5.5*5.4), v2.getValue());
		assertEquals(Double.class, v2.getValue().getClass());
		
		ValueWrapper v3 = new ValueWrapper(0);
		v3.multiply("12");
		assertEquals(0, v3.getValue());
		assertEquals(Integer.class, v3.getValue().getClass());
		
		ValueWrapper v4 = new ValueWrapper(45.5);
		v4.multiply(null);
		assertEquals(0.0, v4.getValue());
		assertEquals(Double.class, v4.getValue().getClass());
		
		ValueWrapper v5 = new ValueWrapper(5);
		v5.multiply("5.7E2");
		assertEquals(2850.0, v5.getValue());
		assertEquals(Double.class, v5.getValue().getClass());
		
		ValueWrapper v6 = new ValueWrapper(4);
		v6.multiply(3);
		assertEquals(12, v6.getValue());
		assertEquals(Integer.class, v6.getValue().getClass());
		
		ValueWrapper v7 = new ValueWrapper(45);
		v7.multiply(-0.5);
		assertEquals(-22.5, v7.getValue());
		assertEquals(Double.class, v7.getValue().getClass());
	}
	
	@Test
	public void divTests() {
		ValueWrapper v1 = new ValueWrapper("5");
		v1.divide("5.4");
		assertEquals(Double.valueOf(5/5.4), v1.getValue());
		assertEquals(Double.class, v1.getValue().getClass());
		
		ValueWrapper v2 = new ValueWrapper("5.5");
		v2.divide("5.4");
		assertEquals(Double.valueOf(5.5/5.4), v2.getValue());
		assertEquals(Double.class, v2.getValue().getClass());
		
		ValueWrapper v3 = new ValueWrapper(0);
		v3.divide("12");
		assertEquals(0, v3.getValue());
		assertEquals(Integer.class, v3.getValue().getClass());
		
		ValueWrapper v4 = new ValueWrapper(null);
		v4.divide(40);
		assertEquals(0, v4.getValue());
		assertEquals(Integer.class, v4.getValue().getClass());
		
		ValueWrapper v5 = new ValueWrapper(null);
		v5.divide(5E10);
		assertEquals(0.0, v5.getValue());
		assertEquals(Double.class, v5.getValue().getClass());
		
		ValueWrapper v6 = new ValueWrapper(5);
		v6.divide("5.7E1");
		assertEquals(Double.valueOf(5/57.), v6.getValue());
		assertEquals(Double.class, v6.getValue().getClass());
		
		ValueWrapper v8 = new ValueWrapper(4);
		v8.divide(3);
		assertEquals(1, v8.getValue());
		assertEquals(Integer.class, v8.getValue().getClass());
		
		ValueWrapper v9 = new ValueWrapper(45);
		v9.divide(0.5);
		assertEquals(90.0, v9.getValue());
		assertEquals(Double.class, v9.getValue().getClass());
	}
	
	@Test(expected=ArithmeticException.class)
	public void divByZero(){
		ValueWrapper v1 = new ValueWrapper("5");
		v1.divide(null);
	}

	@Test(expected=ArithmeticException.class)
	public void divByZero2(){
		ValueWrapper v1 = new ValueWrapper("5");
		v1.divide("0.0");
	}
	
	@Test(expected=ArithmeticException.class)
	public void divByZero3(){
		ValueWrapper v1 = new ValueWrapper("5");
		v1.divide(0);
	}
	
	@Test
	public void compareTest() {
		ValueWrapper v1 = new ValueWrapper(10.54);
		assertTrue(v1.numCompare(10) > 0);
		assertTrue(v1.numCompare(10.54) == 0);
		assertTrue(v1.numCompare(15) < 0);
		assertTrue(v1.numCompare(5.47) > 0);
		assertTrue(v1.numCompare(null) > 0);
		assertTrue(v1.numCompare("5e-1") > 0);
		
	}
}

