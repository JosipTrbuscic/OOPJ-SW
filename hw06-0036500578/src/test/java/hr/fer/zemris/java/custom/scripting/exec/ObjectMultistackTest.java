package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;


public class ObjectMultistackTest {
	public ObjectMultistack col;
	@Before
	public void setup() {
		col = new ObjectMultistack();
		col.push("prvi", new ValueWrapper(11));col.push("sesti", new ValueWrapper(14));col.push("drugi", new ValueWrapper(82));
		col.push("drugi", new ValueWrapper(44));col.push("sedmi",new ValueWrapper(34));col.push("treci", new ValueWrapper(52));
		col.push("treci", new ValueWrapper(56));col.push("osmi", new ValueWrapper(87));col.push("cetvrti", new ValueWrapper(49));
		col.push("cetvrti", new ValueWrapper(28));col.push("deveti", new ValueWrapper(43));col.push("peti", new ValueWrapper(33));
		col.push("peti", new ValueWrapper(37));col.push("prvi", new ValueWrapper(6));col.push("sesti", new ValueWrapper(75));
	}
	
	@Test
	public void isEmptyTest() {
		assertEquals(false, col.isEmpty("peti"));
		assertEquals(false, col.isEmpty("osmi"));
		assertEquals(true, col.isEmpty("test"));
		
	}
	
	@Test
	public void peekTest() {
		assertEquals(6, col.peek("prvi").getValue());
		assertEquals(28, col.peek("cetvrti").getValue());
		assertEquals(43, col.peek("deveti").getValue());
		
		col.push("cetvrti", new ValueWrapper(101));
		
		assertEquals(101, col.peek("cetvrti").getValue());
	}
	
	@Test(expected=NoSuchElementException.class)
	public void peekEmptyStackTest() {
		col.peek("test");
	}
	
	@Test(expected=NullPointerException.class)
	public void peekNullTest() {
		col.peek(null);
	}
	
	@Test
	public void popTest() {
		assertEquals(6, col.pop("prvi").getValue());
		assertEquals(28, col.pop("cetvrti").getValue());
		assertEquals(43, col.pop("deveti").getValue());
		
		assertEquals(false, col.isEmpty("prvi"));
		assertEquals(false, col.isEmpty("cetvrti"));
		assertEquals(true, col.isEmpty("deveti"));
		
		assertEquals(11, col.pop("prvi").getValue());
		assertEquals(true, col.isEmpty("prvi"));
		
		col.push("cetvrti", new ValueWrapper(101));
		
		assertEquals(101, col.pop("cetvrti").getValue());
	}
	
	@Test(expected=NoSuchElementException.class)
	public void popEmptyStackTest() {
		col.pop("test");
	}
	
	@Test(expected=NullPointerException.class)
	public void popNullTest() {
		col.pop(null);
	}

}
