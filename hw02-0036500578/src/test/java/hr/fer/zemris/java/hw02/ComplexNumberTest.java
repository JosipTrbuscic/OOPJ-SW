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
		ComplexNumber cPostiive = ComplexNumber.fromImaginray(2.99);
		ComplexNumber cPositiveConstructor =  new ComplexNumber(0, 2.99);
		
		assertEquals(true, cPostiive.equals(cPositiveConstructor));
	}
	
	@Test
	public void fromImaginaryNegative() {
		ComplexNumber cNegative = ComplexNumber.fromImaginray(-2.99);
		ComplexNumber cNegativeConstructor =  new ComplexNumber(0,-2.99);
		
		assertEquals(true, cNegative.equals(cNegativeConstructor));
	}
	
	@Test
	public void fromImaginaryZero() {
		ComplexNumber cZero = ComplexNumber.fromImaginray(0);
		
		assertEquals(true, cZero.equals(ComplexNumber.ZERO));
	}
	
	@Test
	public void fromMagnitudeAndAngleFirstQuadrant() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(5, PI/6);
		ComplexNumber cConstructor = new ComplexNumber(4.330127019, 2.5);
		
		assertEquals(true, c.equals(cConstructor));
	}
	

}
