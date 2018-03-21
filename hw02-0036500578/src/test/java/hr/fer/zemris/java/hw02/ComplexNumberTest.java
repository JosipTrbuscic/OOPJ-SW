package hr.fer.zemris.java.hw02;

import static org.junit.Assert.*;
import static java.lang.Math.PI;

import org.junit.Test;

public class ComplexNumberTest {
	public static final double DELTA = 1e-4;
	@Test
	public void getAngleFirstQuadrant() {
		ComplexNumber c = new ComplexNumber(3, 4);
		assertEquals(0.2951672222*PI, c.getAngle(), DELTA);
	}
	
	@Test
	public void getAngleSecondQuadrant() {
		ComplexNumber c = new ComplexNumber(-3, 4);
		assertEquals(0.70483333*PI, c.getAngle(), DELTA);
	}
	
	@Test
	public void getAngleThirdQuadrant() {
		ComplexNumber c = new ComplexNumber(-3, -4);
		assertEquals(1.2951666667*PI, c.getAngle(), DELTA);
	}
	
	@Test
	public void getAngleFourthQuadrant() {
		ComplexNumber c = new ComplexNumber(3, -4);
		assertEquals(1.704832778*PI, c.getAngle(), DELTA);
	}
	
	@Test
	public void getAngleBounds() {
		ComplexNumber real = new ComplexNumber(3,0);
		ComplexNumber imaginary = new ComplexNumber(0,3);
		ComplexNumber realNegative = new ComplexNumber(-3,0);
		ComplexNumber imaginaryNegative = new ComplexNumber(0,-3);

		assertEquals(0, real.getAngle(), DELTA);
		assertEquals(PI/2, imaginary.getAngle(), DELTA);
		assertEquals(PI, realNegative.getAngle(), DELTA);
		assertEquals(1.5*PI, imaginaryNegative.getAngle(), DELTA);
	}
	
	@Test
	public void fromRealPositive() {
		ComplexNumber rPositive = ComplexNumber.fromReal(3.58);
		ComplexNumber rPositiveConstructor = ComplexNumber.fromReal(3.58);

		assertEquals(true, rPositive.equals(rPositiveConstructor));
	}
	
	@Test
	public void fromRealNegative() {
		ComplexNumber rNegative= ComplexNumber.fromReal(-3.58);
		ComplexNumber rNegativeConstructor = ComplexNumber.fromReal(-3.58);

		assertEquals(true, rNegative.equals(rNegativeConstructor));
	}
	
	@Test
	public void fromRealZero() {
		ComplexNumber rZero = ComplexNumber.fromReal(0);

		assertEquals(true, rZero.equals(ComplexNumber.ZERO));
	}
	
	@Test
	public void fromImaginaryPositive() {
		ComplexNumber cPostiive = ComplexNumber.fromImaginary(2.99);
		ComplexNumber cPositiveConstructor =  new ComplexNumber(0, 2.99);
		
		assertEquals(true, cPostiive.equals(cPositiveConstructor));
	}
	
	@Test
	public void fromImaginaryNegative() {
		ComplexNumber cNegative = ComplexNumber.fromImaginary(-2.99);
		ComplexNumber cNegativeConstructor =  new ComplexNumber(0,-2.99);
		
		assertEquals(true, cNegative.equals(cNegativeConstructor));
	}
	
	@Test
	public void fromImaginaryZero() {
		ComplexNumber cZero = ComplexNumber.fromImaginary(0);
		
		assertEquals(true, cZero.equals(ComplexNumber.ZERO));
	}
	
	@Test
	public void fromMagnitudeAndAngleFirstQuadrant() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(5, PI/6);
		ComplexNumber cConstructor = new ComplexNumber(4.330127019, 2.5);
		
		assertEquals(true, c.equals(cConstructor));
	}
	
	@Test
	public void fromMagnitudeAndAngleSecondQuadrant() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(4.7, PI*2/3);
		ComplexNumber cConstructor = new ComplexNumber(-2.35, 4.070319398);
		
		assertEquals(true, c.equals(cConstructor));
	}
	
	@Test
	public void fromMagnitudeAndAngleThirdQuadrant() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(3.5, PI*4/3);
		ComplexNumber cConstructor = new ComplexNumber(-1.75, -3.031088913);
		
		assertEquals(true, c.equals(cConstructor));
	}
	
	@Test
	public void fromMagnitudeAndAngleFourthQuadrant() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(7.5, PI*11/6);
		ComplexNumber cConstructor = new ComplexNumber(6.495190528, -3.75);
		
		assertEquals(true, c.equals(cConstructor));
	}
	
	@Test
	public void fromMagnitudeAndAngleMagnitudeZero() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(0, PI);
		ComplexNumber zero = ComplexNumber.ZERO; 
		
		assertEquals(true, c.equals(zero));
	}
	
	@Test
	public void addBothPositve() {
		ComplexNumber c1 = new ComplexNumber(4.5, 2.7);
		ComplexNumber c2 = new ComplexNumber(3.1, 2.1);
		ComplexNumber result = new ComplexNumber(7.6, 4.8);
		
		assertEquals(true, c1.add(c2).equals(result));
	}
	
	@Test
	public void addPositiveAndNegative() {
		ComplexNumber c1 = new ComplexNumber(4.5, 2.7);
		ComplexNumber c2 = new ComplexNumber(-3.1, -2.1);
		ComplexNumber result = new ComplexNumber(1.4, 0.6);
		
		assertEquals(true, c1.add(c2).equals(result));
	}
	
	@Test
	public void addBothNegative() {
		ComplexNumber c1 = new ComplexNumber(-4.5, -2.7);
		ComplexNumber c2 = new ComplexNumber(-3.1, -2.1);
		ComplexNumber result = new ComplexNumber(-7.6, -4.8);
		
		assertEquals(true, c1.add(c2).equals(result));
	}
	
	
	@Test
	public void addZero() {
		ComplexNumber c1 = new ComplexNumber(4.5, 2.7);
		ComplexNumber result = new ComplexNumber(4.5, 2.7);
		
		assertEquals(true, c1.add(ComplexNumber.ZERO).equals(result));
	}
	
	@Test
	public void subBothPositive() {
		ComplexNumber c1 = new ComplexNumber(4.5, 3.6);
		ComplexNumber c2 = new ComplexNumber(3.1, 2.1);
		ComplexNumber result = new ComplexNumber(1.4, 1.5);
		
		assertEquals(true, c1.sub(c2).equals(result));
	}
	
	@Test
	public void subPositiveAndNegative() {
		ComplexNumber c1 = new ComplexNumber(4.5, 3.6);
		ComplexNumber c2 = new ComplexNumber(-3.1, -2.1);
		ComplexNumber result = new ComplexNumber(7.6, 5.7);
		
		assertEquals(true, c1.sub(c2).equals(result));
	}
	
	@Test
	public void subBothNegative() {
		ComplexNumber c1 = new ComplexNumber(-4.5, -3.6);
		ComplexNumber c2 = new ComplexNumber(-3.1, -2.1);
		ComplexNumber result = new ComplexNumber(-1.4, -1.5);
		
		assertEquals(true, c1.sub(c2).equals(result));
	}
	
	@Test
	public void subZero() {
		ComplexNumber c1 = new ComplexNumber(-4.5, -3.6);
		ComplexNumber result = new ComplexNumber(-4.5, -3.6);
		
		assertEquals(true, c1.sub(ComplexNumber.ZERO).equals(result));
	}
	
	@Test
	public void divPositive() {
		ComplexNumber c1 = new ComplexNumber(3.5, 1);
		ComplexNumber c2 = new ComplexNumber(1.7,1);
		ComplexNumber result = new ComplexNumber(1.78663239,-0.462724935732);
		
		assertEquals(true, c1.div(c2).equals(result));
	}
	
	@Test
	public void divNegative() {
		ComplexNumber c1 = new ComplexNumber(3.5, 1);
		ComplexNumber c2 = new ComplexNumber(-1.7,-1);
		ComplexNumber result = new ComplexNumber(-1.78663239,+0.462724935732);
		
		assertEquals(true, c1.div(c2).equals(result));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void divByZero() {
		ComplexNumber c1 = new ComplexNumber(3.5, 1);
		c1.div(ComplexNumber.ZERO);
	}
	
	@Test
	public void divZero() {
		ComplexNumber c1 = new ComplexNumber(3.5, 1);
		
		assertEquals(true, ComplexNumber.ZERO.div(c1).equals(ComplexNumber.ZERO));
	}
	
	@Test
	public void powerPositive() {
		ComplexNumber c1 = new ComplexNumber(3.5, 1);
		ComplexNumber result = new ComplexNumber(32.375, 35.75);
		
		assertEquals(true, c1.power(3).equals(result));
	}
	
	@Test
	public void powerZero() {
		ComplexNumber c1 = new ComplexNumber(3.5, 1);
		ComplexNumber result = new ComplexNumber(1, 0);
		
		assertEquals(true, c1.power(0).equals(result));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void powerNegative() {
		ComplexNumber c1 = new ComplexNumber(3.5, 1);
		c1.power(-4);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void rootNegative() {
		ComplexNumber c1 = new ComplexNumber(3.5, 1);
		c1.root(-4);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void rootZero() {
		ComplexNumber c1 = new ComplexNumber(3.5, 1);
		c1.root(0);
	}
	
	@Test
	public void rootPositive() {
		ComplexNumber c1 = new ComplexNumber(3.5, 1);
		ComplexNumber[] result = new ComplexNumber[3];
		result[0]=new ComplexNumber(1.53167, 0.142497);
		result[1]=new ComplexNumber(-0.88924, 1.25522);
		result[2]=new ComplexNumber(-0.642428, -1.3977);
		
		assertEquals(true, c1.root(3)[0].equals(result[0]));
		assertEquals(true, c1.root(3)[1].equals(result[1]));
		assertEquals(true, c1.root(3)[2].equals(result[2]));
	}
	
	@Test
	public void getMagnitudeFirstQuadrant() {
		ComplexNumber c1 = new ComplexNumber(4, 3);
		double result = 5;
		
		assertEquals(result,c1.getMagnitude(),DELTA);
	}
	
	@Test
	public void getMagnitudeSecondQuadrant() {
		ComplexNumber c1 = new ComplexNumber(-4, 3);
		double result = 5;
		
		assertEquals(result,c1.getMagnitude(),DELTA);
	}
	
	@Test
	public void getMagnitudeThirdQuadrant() {
		ComplexNumber c1 = new ComplexNumber(-4, -3);
		double result = 5;
		
		assertEquals(result,c1.getMagnitude(),DELTA);
	}
	
	@Test
	public void getMagnitudeFourthQuadrant() {
		ComplexNumber c1 = new ComplexNumber(4, -3);
		double result = 5;
		
		assertEquals(result,c1.getMagnitude(),DELTA);
	}
	
	@Test
	public void parseValid() {
		ComplexNumber c1 = ComplexNumber.parse("3.51");
		ComplexNumber c2 = ComplexNumber.parse("-3.17");
		ComplexNumber c3 = ComplexNumber.parse("-2.71i");
		ComplexNumber c4 = ComplexNumber.parse("i");
		ComplexNumber c5 = ComplexNumber.parse("1");
		ComplexNumber c6 = ComplexNumber.parse("-2.71-3.51i");
		
		assertEquals(true,c1.equals(ComplexNumber.fromReal(3.51)));
		assertEquals(true,c2.equals(ComplexNumber.fromReal(-3.17)));
		assertEquals(true,c3.equals(ComplexNumber.fromImaginary(-2.71)));
		assertEquals(true,c4.equals(ComplexNumber.fromImaginary(1)));
		assertEquals(true,c5.equals(ComplexNumber.fromReal(1)));
		assertEquals(true,c6.equals(new ComplexNumber(-2.71,-3.51)));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void parseInvalidImag() {
		ComplexNumber.parse("3.51ijk");

	}
	
	@Test(expected=IllegalArgumentException.class)
	public void parseInvalidBoth() {
		ComplexNumber.parse("2.7+-3.51i");

	}
	
	@Test(expected=IllegalArgumentException.class)
	public void parseInvalidReal() {
		ComplexNumber.parse("2.7.4");

	}
	@Test
	public void toStringInteger() {
		ComplexNumber c1 = new ComplexNumber(-4, -3);
		String result = "-4.00 - 3.00i";

		assertEquals(result,c1.toString());
	}
	@Test
	public void toStringReal() {
		ComplexNumber c1 = new ComplexNumber(-4.58, -3.58);
		String result = "-4.58 - 3.58i";

		assertEquals(result,c1.toString());
	}
	
	@Test
	public void toStringRealOnly() {
		ComplexNumber c1 = ComplexNumber.fromReal(2.594);
		String result = "2.594";

		assertEquals(result,c1.toString());
	}
	
	@Test
	public void toStringImagOnly() {
		ComplexNumber c1 = ComplexNumber.fromImaginary(-3.59);
		String result = "-3.59i";

		assertEquals(result,c1.toString());
	}
	
	
}
