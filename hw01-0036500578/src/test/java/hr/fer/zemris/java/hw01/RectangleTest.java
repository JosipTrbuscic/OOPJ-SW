package hr.fer.zemris.java.hw01;

import static org.junit.Assert.*;
import static hr.fer.zemris.java.hw01.Rectangle.calculateCircumference;
import static hr.fer.zemris.java.hw01.Rectangle.calculateArea;

import org.junit.Test;


public class RectangleTest {
	private static final double DELTA = 1e-10;
	
	@Test
	public void circumferencePositiveDouble() {
		assertEquals(7.5, calculateCircumference(2.5, 1.25),DELTA);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void circumferenceFirstNegative() {
		calculateCircumference(-2.5, 1.25);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void circumferenceSecondNegative() {
		calculateCircumference(2.5, -1.25);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void circumferenceBothNegative() {
		calculateCircumference(-2.5, -1.25);
	}
	
	@Test
	public void areaPositiveDouble() {
		assertEquals(3.125, calculateArea(2.5, 1.25),DELTA);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void areaFirstNegative() {
		calculateArea(-2.5, 1.25);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void areaSecondNegative() {
		calculateArea(2.5, -1.25);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void areaBothNegative() {
		calculateArea(-2.5, -1.25);
	}

}
