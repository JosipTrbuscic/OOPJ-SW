package hr.fer.zemris.java.hw07.crypto;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class UtilTest {

	@Test
	public void hexStringToByte() {
		String test = "4558";
		byte[] data = Util.hexToByte(test);
		assertEquals(true, data[0] == 69);
		assertEquals(true, data[1] == 88);
		
		test = "2f32";
		data = Util.hexToByte(test);
		assertEquals(true, data[0] == 47);
		assertEquals(true, data[1] == 50);
		
		test = "2d39000a";
		data = Util.hexToByte(test);
		assertEquals(true, data[0] == 45);
		assertEquals(true, data[1] == 57);
		assertEquals(true, data[2] == 0);
		assertEquals(true, data[3] == 10);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments() {
		String test = "test";
		Util.hexToByte(test);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments2() {
		String test = "12afd";
		Util.hexToByte(test);
	}


	@Test
	public void byteToString() {
		byte[] test = new byte[2];
		test[0] = 47;
		test[1] = 50;
		assertEquals("2f32", Util.byteToHex(test));
		
		byte[] test2 = new byte[4];
		test2[0] = 0;
		test2[1] = 0;
		test2[2] = 0;
		test2[3] = 0;
		assertEquals("00000000", Util.byteToHex(test2));
		
		byte[] test3 = new byte[4];
		test3[0] = 45;
		test3[1] = 57;
		test3[2] = 0;
		test3[3] = 10;
		assertEquals("2d39000a", Util.byteToHex(test3));
	}
	
	

}
