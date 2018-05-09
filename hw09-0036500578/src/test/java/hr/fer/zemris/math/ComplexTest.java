package hr.fer.zemris.math;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class ComplexTest {
	private static final double DELTA = 1E-2;
	
	@Test
	public void addBothPositve() {
		Complex c1 = new Complex(4.5, 2.7);
		Complex c2 = new Complex(3.1, 2.1);
		Complex result = new Complex(7.6, 4.8);
		
		assertEquals(true, c1.add(c2).equals(result));
	}
	
	@Test
	public void addPositiveAndNegative() {
		Complex c1 = new Complex(4.5, 2.7);
		Complex c2 = new Complex(-3.1, -2.1);
		Complex result = new Complex(1.4, 0.6);
		
		assertEquals(true, c1.add(c2).equals(result));
	}
	
	@Test
	public void addBothNegative() {
		Complex c1 = new Complex(-4.5, -2.7);
		Complex c2 = new Complex(-3.1, -2.1);
		Complex result = new Complex(-7.6, -4.8);
		
		assertEquals(true, c1.add(c2).equals(result));
	}
	
	
	@Test
	public void addZero() {
		Complex c1 = new Complex(4.5, 2.7);
		Complex result = new Complex(4.5, 2.7);
		
		assertEquals(true, c1.add(Complex.ZERO).equals(result));
	}
	
	@Test
	public void subBothPositive() {
		Complex c1 = new Complex(4.5, 3.6);
		Complex c2 = new Complex(3.1, 2.1);
		Complex result = new Complex(1.4, 1.5);
		
		assertEquals(true, c1.sub(c2).equals(result));
	}
	
	@Test
	public void subPositiveAndNegative() {
		Complex c1 = new Complex(4.5, 3.6);
		Complex c2 = new Complex(-3.1, -2.1);
		Complex result = new Complex(7.6, 5.7);
		
		assertEquals(true, c1.sub(c2).equals(result));
	}
	
	@Test
	public void subBothNegative() {
		Complex c1 = new Complex(-4.5, -3.6);
		Complex c2 = new Complex(-3.1, -2.1);
		Complex result = new Complex(-1.4, -1.5);
		
		assertEquals(true, c1.sub(c2).equals(result));
	}
	
	@Test
	public void subZero() {
		Complex c1 = new Complex(-4.5, -3.6);
		Complex result = new Complex(-4.5, -3.6);
		
		assertEquals(true, c1.sub(Complex.ZERO).equals(result));
	}
	
	@Test
	public void divPositive() {
		Complex c1 = new Complex(3.5, 1);
		Complex c2 = new Complex(1.7,1);
		Complex result = new Complex(1.78663239,-0.462724935732);
		
		assertEquals(result, c1.divide(c2));
	}
	
	@Test
	public void divNegative() {
		Complex c1 = new Complex(3.5, 1);
		Complex c2 = new Complex(-1.7,-1);
		Complex result = new Complex(-1.78663239,+0.462724935732);
		
		assertEquals(true, c1.divide(c2).equals(result));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void divByZero() {
		Complex c1 = new Complex(3.5, 1);
		c1.divide(Complex.ZERO);
	}
	
	@Test
	public void divZero() {
		Complex c1 = new Complex(3.5, 1);
		
		assertEquals(Complex.ZERO, Complex.ZERO.divide(c1));
	}
	
	@Test
	public void powerPositive() {
		Complex c1 = new Complex(3.5, 1);
		Complex result = new Complex(32.375, 35.75);
		
		assertEquals(true, c1.power(3).equals(result));
	}
	
	@Test
	public void powerZero() {
		Complex c1 = new Complex(3.5, 1);
		Complex result = new Complex(1, 0);
		
		assertEquals(true, c1.power(0).equals(result));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void powerNegative() {
		Complex c1 = new Complex(3.5, 1);
		c1.power(-4);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void rootNegative() {
		Complex c1 = new Complex(3.5, 1);
		c1.roots(-4);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void rootZero() {
		Complex c1 = new Complex(3.5, 1);
		c1.roots(0);
	}
	
	@Test
	public void rootPositive() {
		Complex c1 = new Complex(3.5, 1);
		Complex[] result = new Complex[3];
		result[0]=new Complex(1.53167, 0.142497);
		result[1]=new Complex(-0.88924, 1.25522);
		result[2]=new Complex(-0.642428, -1.397712);
		List<Complex> roots = c1.roots(3);

		assertEquals(result[0], roots.get(0));
		assertEquals(result[1], roots.get(1));
		assertEquals(result[2], roots.get(2));
	}
	
	@Test
	public void getMagnitudeFirstQuadrant() {
		Complex c1 = new Complex(4, 3);
		double result = 5;
		
		assertEquals(result,c1.module(),DELTA);
	}
	
	@Test
	public void getMagnitudeSecondQuadrant() {
		Complex c1 = new Complex(-4, 3);
		double result = 5;
		
		assertEquals(result,c1.module(),DELTA);
	}
	
	@Test
	public void getMagnitudeThirdQuadrant() {
		Complex c1 = new Complex(-4, -3);
		double result = 5;
		
		assertEquals(result,c1.module(),DELTA);
	}
	
	@Test
	public void getMagnitudeFourthQuadrant() {
		Complex c1 = new Complex(4, -3);
		double result = 5;
		
		assertEquals(result,c1.module(),DELTA);
	}

	@Test
	public void toStringInteger() {
		Complex c1 = new Complex(-4, -3);
		String result = "-4.00 - 3.00i";

		assertEquals(result,c1.toString());
	}
	@Test
	public void toStringReal() {
		Complex c1 = new Complex(-4.58, -3.58);
		String result = "-4.58 - 3.58i";

		assertEquals(result,c1.toString());
	}
	
	@Test
	public void toStringRealOnly() {
		Complex c1 = new Complex(2.594,0);
		String result = "2.594";

		assertEquals(result,c1.toString());
	}
	
	@Test
	public void toStringImagOnly() {
		Complex c1 = new Complex(0, -3.59);
		String result = "-3.59i";

		assertEquals(result,c1.toString());
	}
	

}
