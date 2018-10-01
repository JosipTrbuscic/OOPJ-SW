package hr.fer.zemris.math;

import static org.junit.Assert.*;

import org.junit.Test;

public class Vector3Test {
	private static final double DELTA = 1E-4;

	@Test
	public void normTest() {
		Vector3 v1 = new Vector3(0, 0, 0);
		Vector3 v2 = new Vector3(4.57, 12, 1);
		Vector3 v3 = new Vector3(-1.54, 2, -5.4);
		Vector3 v4 = new Vector3(-7, -5.89, -2);
		Vector3 v5 = new Vector3(1, 1, 1);
		
		assertEquals(0, v1.norm(), DELTA);
		assertEquals(12.8796, v2.norm(), DELTA);
		assertEquals(5.96084, v3.norm(), DELTA);
		assertEquals(9.36441, v4.norm(), DELTA);
		assertEquals(Math.sqrt(3), v5.norm(), DELTA);
	}
	
	@Test
	public void normalizedTest() {
		Vector3 v1 = new Vector3(0, 0, 0);
		Vector3 v2 = new Vector3(4.57, 12, 1);
		Vector3 v3 = new Vector3(-1.54, 2, -5.4);
		Vector3 v4 = new Vector3(-7, -5.89, -2);
		Vector3 v5 = new Vector3(1, 1, 1);
		
		assertEquals(new Vector3(0, 0, 0), v1.normalized());
		assertEquals(new Vector3(0.354824, 0.931704, 0.077642), v2.normalized());
		assertEquals(new Vector3(-0.258353, 0.335523, -0.905913), v3.normalized());
		assertEquals(new Vector3(-0.747511, -0.628977, -0.213575), v4.normalized());
		assertEquals(new Vector3(1/Math.sqrt(3), 1/Math.sqrt(3), 1/Math.sqrt(3)), v5.normalized());
	}
	
	@Test
	public void addTest() {
		Vector3 v1 = new Vector3(0, 0, 0);
		Vector3 v2 = new Vector3(4.57, 12, 1);
		Vector3 v3 = new Vector3(-1.54, 2, -5.4);
		Vector3 v4 = new Vector3(-7, -5.89, -2);
		Vector3 v5 = new Vector3(1, 1, 1);
		
		assertEquals(new Vector3(0, 0, 0), v1.add(v1));
		assertEquals(new Vector3(1, 1, 1), v1.add(v5));
		assertEquals(new Vector3(3.03, 14, -4.4), v2.add(v3));
		assertEquals(new Vector3(-2.43, 6.11, -1), v2.add(v4));
		assertEquals(new Vector3(-6, -4.89, -1), v4.add(v5));
	}
	
	@Test
	public void subTest() {
		Vector3 v1 = new Vector3(0, 0, 0);
		Vector3 v2 = new Vector3(4.57, 12, 1);
		Vector3 v3 = new Vector3(-1.54, 2, -5.4);
		Vector3 v4 = new Vector3(-7, -5.89, -2);
		Vector3 v5 = new Vector3(1, 1, 1);
		
		assertEquals(new Vector3(0, 0, 0), v1.sub(v1));
		assertEquals(new Vector3(-1, -1, -1), v1.sub(v5));
		assertEquals(new Vector3(6.11, 10, 6.4), v2.sub(v3));
		assertEquals(new Vector3(11.57, 17.89, 3), v2.sub(v4));
		assertEquals(new Vector3(-8, -6.89, -3), v4.sub(v5));
	}
	
	@Test
	public void dotTest() {
		Vector3 v1 = new Vector3(0, 0, 0);
		Vector3 v2 = new Vector3(4.57, 12, 1);
		Vector3 v3 = new Vector3(-1.54, 2, -5.4);
		Vector3 v4 = new Vector3(-7, -5.89, -2);
		Vector3 v5 = new Vector3(1, 1, 1);
		
		assertEquals(0, v1.dot(v1), DELTA);
		assertEquals(0, v1.dot(v5), DELTA);
		assertEquals(11.5622, v2.dot(v3), DELTA);
		assertEquals(-104.67, v2.dot(v4), DELTA);
		assertEquals(-14.89, v4.dot(v5), DELTA);
	}
	
	@Test
	public void crossTest() {
		Vector3 v1 = new Vector3(0, 0, 0);
		Vector3 v2 = new Vector3(4.57, 12, 1);
		Vector3 v3 = new Vector3(-1.54, 2, -5.4);
		Vector3 v4 = new Vector3(-7, -5.89, -2);
		Vector3 v5 = new Vector3(1, 1, 1);
		
		assertEquals(new Vector3(0, 0, 0), v1.cross(v1));
		assertEquals(new Vector3(0, 0, 0), v1.cross(v5));
		assertEquals(new Vector3(-66.8, 23.138, 27.62), v2.cross(v3));
		assertEquals(new Vector3(-18.11, 2.14, 57.0827), v2.cross(v4));
		assertEquals(new Vector3(-3.89, 5, -1.11), v4.cross(v5));
	}
	
	@Test
	public void scaleTest() {
		Vector3 v1 = new Vector3(0, 0, 0);
		Vector3 v2 = new Vector3(4.57, 12, 1);
		Vector3 v3 = new Vector3(-1.54, 2, -5.4);
		Vector3 v4 = new Vector3(-7, -5.89, -2);
		Vector3 v5 = new Vector3(1, 1, 1);
		
		assertEquals(new Vector3(0, 0, 0), v1.scale(4.7));
		assertEquals(new Vector3(14.624, 38.4, 3.2), v2.scale(3.2));
		assertEquals(new Vector3(0, 0, 0), v3.scale(0));
		assertEquals(new Vector3(3.5, 2.945, 1), v4.scale(-0.5));
		assertEquals(new Vector3(-1, -1, -1), v5.scale(-1));
	}

	@Test
	public void cosAngleTest() {
		Vector3 v1 = new Vector3(0, 0, 0);
		Vector3 v2 = new Vector3(4.57, 12, 1);
		Vector3 v3 = new Vector3(-1.54, 2, -5.4);
		Vector3 v4 = new Vector3(-7, -5.89, -2);
		Vector3 v5 = new Vector3(1, 1, 1);
		
		assertEquals(0, v1.cosAngle(v1), DELTA);
		assertEquals(0, v1.cosAngle(v5), DELTA);
		assertEquals(0.1506011, v2.cosAngle(v3), DELTA);
		assertEquals(-0.86783, v2.cosAngle(v4), DELTA);
		assertEquals(-0.918022, v4.cosAngle(v5), DELTA);
	}
}
