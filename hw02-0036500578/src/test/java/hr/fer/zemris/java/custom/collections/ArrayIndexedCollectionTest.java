package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ArrayIndexedCollectionTest {
	
	ArrayIndexedCollection col;
	//add,get,clear
	@Before
	public void setup(){
		col = new ArrayIndexedCollection(50);
		col.add(11);col.add(14);col.add(82);
		col.add(44);col.add(21);col.add(51);
		col.add(96);col.add(87);col.add(51);
		col.add(28);col.add(43);col.add(33);
		col.add(37);col.add(6);col.add(75);

	}
	
	@Test
	public void sizeTest() {
		assertEquals(15, col.size());
	}
	
	@Test
	public void addOneElement(){
		col.add(1);
		assertEquals(1,col.get(col.size()-1));
	}
	
	@Test
	public void addThreeElements(){
		col.add(1);
		col.add(5);
		col.add(6);
		assertEquals(6,col.get(col.size()-1));
		assertEquals(5,col.get(col.size()-2));
		assertEquals(1,col.get(col.size()-3));
	}
	
	@Test(expected=NullPointerException.class)
	public void addNull(){
		Integer n = null;
		col.add(n);
	}
	
	@Test
	public void getMultiple(){
		assertEquals(21,col.get(4));
		assertEquals(87,col.get(7));
		assertEquals(82,col.get(2));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getOutOfBounds(){
		col.get(25);
	}
	
	@Test
	public void clearSize(){
		col.clear();
		assertEquals(0, col.size());
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void clearGetOutOfBounds(){
		col.clear();
		col.get(0);
	}
	
	@Test
	public void insertFirst() {
		col.insert(3, 0);
		assertEquals(3, col.get(0));
	}
	
	@Test
	public void insertMiddle() {
		col.insert(3, 7);
		assertEquals(3, col.get(7));
	}
	
	@Test
	public void insertLast() {
		col.insert(3, col.size());
		assertEquals(3, col.get(col.size()-1));
	}
	
	@Test
	public void containsTrue() {
		assertEquals(true, col.contains(96));
	}
	
	@Test
	public void containsFalse() {
		assertEquals(false, col.contains(100));
	}
	
	@Test
	public void indexOfFirst() {
		assertEquals(0, col.indexOf(11));
	}
	
	@Test
	public void indexOfMiddle() {
		assertEquals(7, col.indexOf(87));
	}
	
	@Test
	public void indexOfLast() {
		assertEquals(col.size()-1, col.indexOf(75));
	}
	
	@Test
	public void indexOfDuplicate() {
		col.add(87);
		assertEquals(7, col.indexOf(87));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void removeIndexOutOfBounds() {
		col.remove(24);
	}
	
	@Test
	public void removeIndexFirst() {
		col.remove(0);
		assertEquals(14, col.size());
		assertEquals(14, col.get(0));
	}
	
	@Test
	public void removeIndexMiddle() {
		col.remove(10);
		assertEquals(14, col.size());
		assertEquals(33, col.get(10));
	}
	
	@Test
	public void removeIndexLast() {
		col.remove(col.size()-1);
		assertEquals(14, col.size());
	}
	
	@Test
	public void removeObjectNotPresent(){
		assertEquals(false, col.remove(Integer.valueOf(101)));
	}
	
	@Test
	public void removeObjectFirst(){
		col.remove(Integer.valueOf(11));
		assertEquals(14, col.size());
		assertEquals(14, col.get(0));
	}
	
	@Test
	public void removeObjectMiddle(){
		col.remove(Integer.valueOf(96));
		assertEquals(14, col.size());
		assertEquals(87, col.get(6));
	}
	
	@Test
	public void removeObjectDuplicate(){
		col.add(51);
		col.remove(Integer.valueOf(51));
		assertEquals(15, col.size());
		assertEquals(96, col.get(5));
	}
	
	@Test
	public void removeObject(){
		col.add(51);
		col.remove(Integer.valueOf(51));
		assertEquals(15, col.size());
		assertEquals(96, col.get(5));
	}
	
	@Test
	public void toArray(){
		Object[] array = col.toArray();
		
		assertEquals(15, array.length);
		assertEquals(37, array[12]);
	}

}
