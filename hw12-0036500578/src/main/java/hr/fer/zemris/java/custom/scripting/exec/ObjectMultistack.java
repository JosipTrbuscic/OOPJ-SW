package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * This class maps key to values. Each key can hold multiple 
 * values which are stored in stack structure. Duplicate key
 * are not allowed. Class offers basic stack operations like
 * pop, push and peek which operate in constant time.
 * @author Josip Trbuscic
 *
 */
public class ObjectMultistack {
	
	/**
	 * Map of keys mapped to values which are connected
	 *  in single-linked list if there is more than one
	 *  value mapped to the same key.
	 */
	Map<String, MultistackEntry> map;
	
	/**
	 * Constructs new instance of this class and
	 * new empty map.
	 */
	public ObjectMultistack() {
		map = new HashMap<>();
	}
	
	/**
	 * Wrapper class for value of key-value pair.  It 
	 * acts like node of single-linked list mapped 
	 * to the key.
	 */
	private static class MultistackEntry {
		
		/**
		 * Value of node.
		 */
		private ValueWrapper value;
		
		/**
		 * Next node in the list.
		 */
		private  MultistackEntry next ;
		
		/**
		 * Constructs new instance of this class 
		 * with specified value
		 * @param value - value of the node
		 */
		public MultistackEntry(ValueWrapper value) {
			this.value = value;
		}
		
		/**
		 * Returns value of this node
		 * @return value of this node
		 */
		public ValueWrapper getValue() {
			return value;
		}
		
		/**
		 * Returns next node in the list
		 * @return next node in the list
		 */
		@SuppressWarnings("unused")
		public ValueWrapper getNext() {
			return value;
		}
	}
	
	/**
	 * Pushes specified value wrapper on the stack which 
	 * maps to the given key 
	 * @param name - key 
	 * @param valueWrapper - value to be added to the stack
	 * @throws NullPointerException if given key or value is null
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		if(name == null) 
			throw new NullPointerException("Key cannot be null");
		if(valueWrapper == null)
			throw new NullPointerException("Value cannot be null");
		
		MultistackEntry head = map.get(name);
		
		if(head == null) {
			//Create new head
			MultistackEntry newHead = new MultistackEntry(null);
			map.put(name,newHead);
			
			newHead.next = new MultistackEntry(valueWrapper);
		}else {
			MultistackEntry newEntry = new MultistackEntry(valueWrapper); 
			newEntry.next = head.next;
			head.next = newEntry;
		}
		
	}
	
	/**
	 * Returns value from top of the stack. If stack is empty 
	 * or key is not contained exception is thrown.
	 * @param name - key 
	 * @return value from top of the stack
	 * @throws NullPointerException if given key is null
	 * @throws NoSuchElementException if key is not mapped
	 */
	public ValueWrapper peek(String name) {
		if(name == null)
			throw new NullPointerException("Key cannot be null");
		if(!map.containsKey(name))
			throw new NoSuchElementException("No value mapped for \""+name+"\" key");

		return map.get(name).next.getValue();
	}
	
	/**
	 * Returns value from top of the stack and removes 
	 * it from the stack. If stack is empty 
	 * or key is not contained exception is thrown.
	 * @param name - key 
	 * @return value from top of the stack
	 * @throws NullPointerException if given key is null
	 * @throws NoSuchElementException if key is not mapped
	 */
	public ValueWrapper pop(String name) {
		if(name == null)
			throw new NullPointerException("Key cannot be null");
		if(!map.containsKey(name))
			throw new NoSuchElementException("No value mapped for \""+name+"\" key");
		
		MultistackEntry head = map.get(name);
		if(head == null) return null;
		
		MultistackEntry toReturn = head.next;
		head.next = head.next.next;
		
		//if stack is empty, remove key-value pair from map
		if(head.next == null) {
			map.remove(name);
		}
		return toReturn.value;
	}
	
	/**
	 * Returns true if stack mapped with the 
	 * given key does not exist.
	 * @param name - key
	 * @return true if stack mapped with the 
	 * given key does not exist, false otherwise.
	 */
	public boolean isEmpty(String name) {
		return !map.containsKey(name);
	}
	
}
