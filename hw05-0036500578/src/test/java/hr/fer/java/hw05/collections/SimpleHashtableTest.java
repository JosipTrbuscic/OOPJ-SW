package hr.fer.java.hw05.collections;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

@SuppressWarnings("javadoc")
public class SimpleHashtableTest {
	public SimpleHashtable<String,Integer> table;
	@Before
	public void setup() {
		table = new SimpleHashtable<String,Integer>(14);
		table.put("prvi", 11);table.put("sesti", 14);table.put("jedanaesti", 82);
		table.put("drugi", 44);table.put("sedmi", 21);table.put("dvanaesti", 51);
		table.put("treci", 96);table.put("osmi", 87);table.put("trinaesti", 51);
		table.put("cetvrti", 28);table.put("deveti", 43);table.put("cetrnaesti", 33);
		table.put("peti", 37);table.put("deseti", 6);table.put("petnaesti", 75);
	}
	
	@Test
	public void isEmptyTest() {
		SimpleHashtable<String, String> test = new SimpleHashtable<>();
		
		assertEquals(true, test.isEmpty());
		assertEquals(false, table.isEmpty());
		
	}
	
	@Test
	public void putNormal() {
		assertEquals(15, table.size());
		table.put("test", 102);
		
		assertEquals(16, table.size());
		assertEquals(true, table.containsKey("test"));
		assertEquals(true, table.containsValue(102));
	}
	
	@Test
	public void putNull() {
		assertEquals(15, table.size());
		table.put("test", null);
		
		assertEquals(16, table.size());
		assertEquals(true, table.containsKey("test"));
		assertEquals(true, table.containsValue(null));
	}
	
	@Test
	public void putMultiple() {
		assertEquals(15, table.size());
		table.put("test", 106);
		table.put("test2", 268);
		table.put("test3", 149);
		
		assertEquals(18, table.size());
	}
	
	@Test
	public void putSameKey() {
		assertEquals(15, table.size());
		table.put("test", 106);
		table.put("test", 268);
		table.put("test2", 149);
		
		assertEquals(17, table.size());
		assertEquals(Integer.valueOf(268), table.get("test"));
	}
	
	@Test
	public void getExisting() {
		assertEquals(Integer.valueOf(28), table.get("cetvrti"));
		assertEquals(Integer.valueOf(87), table.get("osmi"));
		assertEquals(Integer.valueOf(51), table.get("trinaesti"));
		assertEquals(Integer.valueOf(51), table.get("dvanaesti"));
	}
	
	@Test
	public void getNulls() {
		assertEquals(null, table.get("asd"));
		assertEquals(null, table.get("test"));
		assertEquals(null, table.get(null));
	}
	
	@Test
	public void containsKey() {
		assertEquals(true, table.containsKey("prvi"));
		assertEquals(true, table.containsKey("peti"));
		assertEquals(true, table.containsKey("deseti"));
		assertEquals(false, table.containsKey("test"));
		assertEquals(false, table.containsKey("java"));
	}
	
	@Test
	public void containsKeyNull() {
		assertEquals(false, table.containsKey(null));
	}
	
	@Test
	public void containsValue() {
		assertEquals(true, table.containsValue(14));
		assertEquals(true, table.containsValue(28));
		assertEquals(true, table.containsValue(33));
		assertEquals(false, table.containsValue(101));
		assertEquals(false, table.containsValue("java"));
	}
	
	@Test
	public void remove() {
		assertEquals(true, table.containsValue(14));
		table.remove("sesti");
		assertEquals(false, table.containsValue(14));
		assertEquals(false, table.containsValue("sesti"));
		assertEquals(14, table.size());
	}
	
	@Test
	public void removeFromOverflowList() {
		assertEquals(true, table.containsValue(14));
		table.remove("sedmi");
		assertEquals(false, table.containsValue(21));
		assertEquals(false, table.containsValue("sedmi"));
		assertEquals(14, table.size());
	}

}
