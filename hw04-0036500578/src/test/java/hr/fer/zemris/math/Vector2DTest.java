package hr.fer.zemris.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.math.Vector2D;

public class Vector2DTest {
	public static final double DELTA = 1E-6;
	Vector2D vect;
	
	@Before
	public void setup() {
		vect = new Vector2D(4.37, 5.23);
	}

	@Test
	public void translateNormal() {
		Vector2D offset = new Vector2D(4.67, 5.43);
		vect.translate(offset);
		assertEquals(9.04,vect.getX(),DELTA);
		assertEquals(10.66,vect.getY(),DELTA);
	}
	
	@Test
	public void translateNegative() {
		Vector2D offset = new Vector2D(-12.547, -5.678);
		vect.translate(offset);
		assertEquals(-8.177,vect.getX(),DELTA);
		assertEquals(-0.448,vect.getY(),DELTA);
	}
	
	@Test
	public void translateZero() {
		Vector2D offset = new Vector2D(0,0);
		vect.translate(offset);
		assertEquals(4.37,vect.getX(),DELTA);
		assertEquals(5.23,vect.getY(),DELTA);
	}
	
	@Test
	public void rotateHalf() {
		vect.rotate(180);
		assertEquals(-4.37,vect.getX(),DELTA);
		assertEquals(-5.23,vect.getY(),DELTA);
	}
	
	@Test
	public void rotateMultipleCircles() {
		vect.rotate(720);
		assertEquals(4.37,vect.getX(),DELTA);
		assertEquals(5.23,vect.getY(),DELTA);
	}
	
	@Test
	public void rotateNegative() {
		vect.rotate(-90);
		assertEquals(5.23,vect.getX(),DELTA);
		assertEquals(-4.37,vect.getY(),DELTA);
	}
	
	@Test
	public void rotateZero() {
		vect.rotate(0);
		assertEquals(4.37,vect.getX(),DELTA);
		assertEquals(5.23,vect.getY(),DELTA);
	}
	
	@Test
	public void rotateDecimal() {
		vect.rotate(46.56);
		assertEquals(-0.792688,vect.getX(),DELTA);
		assertEquals(6.76915404,vect.getY(),DELTA);
	}
	
	@Test
	public void rotateNegativeDecimal() {
		vect.rotate(-75.5);
		assertEquals(6.15757277,vect.getX(),DELTA);
		assertEquals(-2.9213177,vect.getY(),DELTA);
	}
	
	@Test
	public void scaleInteger() {
		vect.scale(5);
		assertEquals(21.85,vect.getX(),DELTA);
		assertEquals(26.15,vect.getY(),DELTA);
	}
	
	@Test
	public void scaleDouble() {
		vect.scale(3.78);
		assertEquals(16.5186,vect.getX(),DELTA);
		assertEquals(19.7694,vect.getY(),DELTA);
	}
	
	@Test
	public void scaleNegativeDecimal() {
		vect.scale(-2.48);
		assertEquals(-10.8376,vect.getX(),DELTA);
		assertEquals(-12.9704,vect.getY(),DELTA);
	}
	
	@Test
	public void scaleSmaller() {
		vect.scale(-0.25);
		assertEquals(-1.0925,vect.getX(),DELTA);
		assertEquals(-1.3075,vect.getY(),DELTA);
	}
	
	@Test
	public void scaleZero() {
		vect.scale(0);
		assertEquals(0,vect.getX(),DELTA);
		assertEquals(0,vect.getY(),DELTA);
	}
	
	@Test
	public void copyTest() {
		Vector2D v = vect.copy();
		assertEquals(true, vect.equals(v));
	}
	
	
	
}
