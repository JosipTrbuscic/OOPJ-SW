package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * Collection  implemented as doubly linked list. The {@code add}, {@code size} and {@code clear} 
 * operations perform in constant time. The {@code get}, {@code insert} and {@code remove} operations run in n/2 +1 complexity.
 * The {@code indexOf}, {@code contains}, {@code toArray} and {@code forEach} operations run in complexity O(n).
 * @author Josip Trbuscic
 */
public class LinkedListIndexedCollection extends Collection {
	private int size = 0;
	private ListNode first;
	private ListNode last;
	/**
	 * Structure of the list node
	 */
	private static class ListNode{
		Object value;
		ListNode next;
		ListNode previous;
	}
	/**
	 * Constructs an empty list
	 */
	public LinkedListIndexedCollection() {
		first = null;
		last = null;
	}
	/**
	 * Constructs a list containing the elements of the specified collection
	 * @param other - the collection whose elements will be copied
	 */
	public LinkedListIndexedCollection(Collection other) {
		if (other == null)
			throw new NullPointerException("Other Collection should not be null");
		
		this.addAll(other);
	}
	/**
	 * Returns the number of elements in this collection.
	 */
	public int size() {
		return size;
	}
	/**
	 * Appends specified element to the end of this collection
	 * in complexity O(1). {@code null} cannot be added to the 
	 * collection.
	 * @param value - element to be added to this collection
	 * @throws NullPointerException if specified object is {@code null}
	 */
	@Override
	public void add(Object value) {
		if(value == null ) throw new NullPointerException("Null cannot be added to collection");
		
		if(size == 0) {
			first = new ListNode();
			first.value = value;
			last=first;
		} else {
			last.next = new ListNode();
			last.next.value = value; 
			last = last.next;
		}
		size++;
	}
	/**
	 * Returns the element at the specified position in 
	 * the list if position is valid. Complexity O(n).
	 * @param index of the element to return
	 * @return element at the position {@code index}
	 * @throws 	IndexOutOfBoundsException if specified index
	 * 			is not in valid range
	 */
	public Object get(int index) {
		if(index<0 || index>this.size-1) {
			throw new IndexOutOfBoundsException(
					"Valid range of index: 0-" +(this.size-1)+". You entered: "+index); 
		}
		
		return iterateToIndex(index).value;
	}
	/**
	 * Removes all elements from the collection
	 */
	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}
	/**
	 * Inserts element at the specified position in collection. Shifts 
	 * element currently at that position and all remaining elements.
	 * @param value - element to be added 
	 * @param position - position at which element will be added
	 */
	public void insert(Object value, int position) {
		if(position<0 || position>this.size) {
			throw new IndexOutOfBoundsException(
					"Valid range of index: 0-" +this.size+". You entered: "+position); 
		}
		
		if(size == 0 || position == size) {
			this.add(value);
			return;
		} else if (position == 0) {
			first.previous = new ListNode();
			first.previous.value = value;
			first = first.previous;
		} else {
			ListNode temp = iterateToIndex(position);
			ListNode newNode = new ListNode(); 
			
			newNode.value = value;
			newNode.previous = temp.previous;
			newNode.next = temp;
			temp.previous.next = newNode;
			temp.previous = newNode;
		}
		size++;
	}
	/**
	 * Searches the collection for specified element
	 * and returns index of the first occurrence if found
	 * @param value - element to search for
	 * @return  the index of the first occurrence of the specified element,
	 *  		or -1 if this collection does not contain the element
	 */
	public int indexOf(Object value) {
		ListNode temp = first;
		for(int i = 0;i<size;++i) {
			if(temp.value.equals(value)) {
				return i;
			}
		}
		
		System.out.println("No such element");
		return -1;
		
	}
	/**
	 * Removes the element at the specified index
	 * @param 	index of the element to be removed
	 * @throws 	IndexOutOfBoundsException if specified index
	 * 			is not in valid range
	 */
	public void remove(int index) {
		if(index<0 || index>this.size-1) {
			throw new IndexOutOfBoundsException(
					"Valid range of index: 0-" +(this.size-1)+". You entered: "+index); 
		}
		
		if(size == 1) {
			clear();
		} else if(index == size-1){
			last = last.previous;
			last.next = null;
		} else if(index == 0) {
			first = first.next;
			first.previous = null;
		} else {
			ListNode temp = iterateToIndex(index);
			
			temp.next.previous = temp.previous;
			temp.previous.next = temp.next;
		}
		
		size--;
	}
	/**
	 * Returns true if collection contains
	 * specified element.
	 * @param 	value of the element to search for
	 * @return 	{@code true} if element is present
	 * 			in the collection
	 */
	@Override
	public boolean contains(Object value) {
		ListNode temp = first;
		for(int i = 0;i<size;++i) {
			if(temp.value.equals(value)) {
				return true;
			}
			temp=temp.next;
		}
		return false;
	}
	/**
	 * Returns new array which contains
	 * all elements from collection. Order
	 * of element will not be changed.
	 * @return Array with all elements from collection
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		ListNode temp = first;
		for(int i = 0;i<size;++i) {
			array[i]=temp.value;
			temp=temp.next;
		}
		return array;
	}
	/**
	 * Processes every collection element individually
	 * in a way specified by Processor argument.
	 * @param 	processor which will process every
	 * 			collection argument
	 */
	@Override
	public void forEach(Processor processor) {
		ListNode temp = first;
		for(int i = 0;i<size;++i) {
			processor.process(temp.value);
			temp = temp.next;
		}
	}
	/**
	 * Removes first instance of the specified element from this collection.
	 * If element is not present return false and collection remains unchanged.
	 * @param o - object to be removed
	 * @return {@code true} if element is removed from collection, {@code false} otherwise
	 */
	@Override
	public boolean remove(Object o) {
		ListNode temp = first;
		for(int i=0;i<size;++i) {
			if(temp.value.equals(o)) {
				temp.next.previous = temp.previous;
				temp.previous.next = temp.next;
				
				size--;
				
				return true;
			}
		}
		
		return false;
	}
	/**
	 * Iterates through the list to the position specified
	 * by {@code index} and returns node at that index
	 * @param 	index of the required element
	 * @return	element of the list at specified index
	 */
	private ListNode iterateToIndex(int index) {
		ListNode node;

		if(index <= size/2) {
			node = first;
			for(int counter = 0;counter<index;counter++) {
				node = node.next;
			}
		} else {
			node = last;
			for(int counter = size - index;counter<index;counter++) {
				node = node.previous;
			}
		}
		
		return node;
	}
	
}
