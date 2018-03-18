package hr.fer.zemris.java.hw01;

import static hr.fer.zemris.java.hw01.Factorial.calculateFactorial;
import org.junit.Assert;
import org.junit.Test;

public class FactorialTest {

	@Test
	public void positiveInteger() {
		Assert.assertEquals(120, calculateFactorial(5));
	}
	
	@Test
	public void zero() {
		Assert.assertEquals(1, calculateFactorial(0));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void negativeInteger() {
		calculateFactorial(-5);
	}
	
}
