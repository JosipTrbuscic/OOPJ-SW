package hr.fer.zemris.java.custom.collections;
/**
 * Dictionary is a {@code Collection} implementation which stores unordered key-value pairs in a {@code ArrayIndexedCollection}.
 * Keys are unique within a dictionary while values may not be. Values are referenced by their key. Key can be paired with null value. 
 * @author Josip Trbuscic
 *
 */
public class Dictionary {
	/**
	 * Array where {@code Dictionary} entries are stored
	 */
	private ArrayIndexedCollection map;
	
	/**
	 *Inner static class that represents key-value pair  
	 */
	private static class Entry{
		private Object key;
		private Object value;
		
		public Entry(Object key, Object value) {
			this.key = key;
			this.value = value;
		}
		
		@Override
		public boolean equals(Object o) {
			if(o == null) return false;
			
			return o.equals(key);
		}
		
		public Object getValue() {
			return value;
		}
	}
	
	/**
	 * Contructs new {@code ArrayIndexedCollection} where dictionary entries
	 * will be stored
	 */
	public Dictionary() {
		map = new ArrayIndexedCollection();
	}
	
	/**
	 * Returns {@code size==0}. 
	 * @return {@code true} if this collection contains no entries.
	 */ 
	public boolean isEmpty() {
		return map.isEmpty();
	}
	
	/**
	 * Returns number of {@code Dictionary} entries
	 * @return number of elements in this collection
	 */
	public int size() {
		return map.size();
	}
	
	/**
	 * Adds key-value pair to the collection. If entry with the equal 
	 * key already exists within collection value will be overwritten 
	 * with the given value.
	 * @param key - key with which the specified value is to be associated
	 * @param value -  value to be associated with the specified key
	 */
	public void put(Object key, Object value) {
		if(key == null) throw new NullPointerException("Key must not be null");
		
		int index = map.indexOf(key);
		
		if(index != -1) {
			map.remove(index);
			map.add(new Entry(key,value));
		} else {
			map.add(new Entry(key,value));
		}
	}
	
	/**
	 * Returns the value to which the specified key is mapped, or null if this
	 *  {@code dictionary} contains no associated value with key. 
	 * @param key - the key whose associated value is to be returned
	 * @return - The value to which the specified key is associated with,
	 * 			 or null if this map contains no mapping for the key
	 */
	public Object get(Object key) {
		if(key == null) throw new NullPointerException("Key must not be null");
		
		int index = map.indexOf(key);
		
		if(index != -1) {
			Entry entry = (Entry) map.get(index);
			
			return entry.getValue();
		} else {
			return null;
		}
	}
	
	/**
	 * Removes all of the entries from this dictionary. 
	 */
	public void clear() {
		map.clear();
	}
}
