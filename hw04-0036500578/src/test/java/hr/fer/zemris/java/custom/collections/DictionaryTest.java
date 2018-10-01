package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DictionaryTest {
	public Dictionary dict;
	public Dictionary emptyDict;
	
	@Before
	public void setup(){
		emptyDict = new Dictionary();
		dict = new Dictionary();
		
		dict.put("prvi", 11);dict.put("sesti", 14);dict.put("jedanaesti", 82);
		dict.put("drugi", 44);dict.put("sedmi", 21);dict.put("dvanaesti", 51);
		dict.put("treci", 96);dict.put("osmi", 87);dict.put("trinaesti", 51);
		dict.put("cetvrti", 28);dict.put("deveti", 43);dict.put("cetrnaesti", 33);
		dict.put("peti", 37);dict.put("deseti", 6);dict.put("petnaesti", 75);

	}

	@Test
	public void isEmptyTest() {
		assertEquals(true, emptyDict.isEmpty());
		assertEquals(false, dict.isEmpty());
	}
	
	@Test
	public void sizeTest() {
		assertEquals(0, emptyDict.size());
		assertEquals(15,dict.size());
	}
	
	@Test
	public void putValidTest() {
		assertEquals(15,dict.size());
		
		dict.put("sesnaesti", 43);
		assertEquals(16, dict.size());
		
		dict.put("sesnaesti", 45);
		assertEquals(16, dict.size());
		assertEquals(45, dict.get("sesnaesti"));
		
		dict.put("nullKey", null);
		assertEquals(null, dict.get("nullKey"));
	}
	
	@Test
	public void putMultipleTest() {
		
		dict.put("test1", 54);
		dict.put("test2", 54);
		assertEquals(true, dict.get("test1").equals(dict.get("test2")));
		assertEquals(17, dict.size());
		
		dict.put("key",102);
		dict.put("test1",79);
		assertEquals(18, dict.size());
	}
	@Test(expected=NullPointerException.class)
	public void putNullTest() {
		dict.put(null,99);
	}
	
	@Test
	public void getTest() {
		assertEquals(44, dict.get("drugi"));
		
		assertEquals(null, dict.get("nullKey"));
		
		dict.put("drugi", 102);
		assertEquals(102, dict.get("drugi"));
	}
	
	@Test
	public void clearTest() {
		assertEquals(15, dict.size());
		assertEquals(false, dict.isEmpty());
		
		dict.clear();
		assertEquals(0, dict.size());
		assertEquals(true, dict.isEmpty());

	}



}
