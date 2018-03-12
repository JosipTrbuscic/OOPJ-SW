package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;
/**
 * Testovi za metodu koja racuna n faktorijela
 * 
 * @author josipTrbuscic
 *
 */
public class FactorialTest {

	@Test
	public void positiveInteger() {
		Assert.assertEquals(120, Factorial.calculateFactorial(5));
	}
	
	@Test
	public void zero() {
		Assert.assertEquals(1, Factorial.calculateFactorial(0));

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void negativeInteger() {
		Factorial.calculateFactorial(-5);
	}

}
