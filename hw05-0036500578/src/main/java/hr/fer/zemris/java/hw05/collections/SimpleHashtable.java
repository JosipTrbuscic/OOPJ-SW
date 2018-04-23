package hr.fer.zemris.java.hw05.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * Data structure used for mapping keys to values. Map cannot contain duplicate keys and one key can map to only one value.
 * Key cannot be {@code null} while value has no restrictions. This implementation offers constant-time performance for
 * get and put operations. Key-value pairs are internally stored in a resizable array. In case of overflow linked list
 * is formed. Adding existing key will just overwrite its value.
 * @author Josip Trbuscic 
 *
 * @param <K> type of key
 * @param <V> type of mapped values
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	/**
     * Load factor.
     */
	static final float LOAD_FACTOR = 0.75f;
	/**
	 * Default capacity of hashtable
	 */
	private static final int DEFAULT_CAPACITY = 16;
	
	/**
	 * Internal array where key-value pairs are stored
	 */
	private TableEntry<K, V>[] table;
	
	/**
	 *Number of entries contained in this map. Initially set to 0
	 */
	private int size = 0;
	
	/**
	 * Counter of modifications. This variable assures
	 * iterators are fast-fail.
	 */
	private int modificationCount = 0;

	/**
	 * A map entry(key-value pair). Class offers getters for key and value while setter is only 
	 * available for value.
	 * @param <K>
	 * @param <V>
	 */
	public static class TableEntry<K, V> {
		
		/**
		 * Mapped key
		 */
		private K key;
		
		/**
		 * Mapped value
		 */
		private V value;
		
		/**
		 * Reference to the next entry in the 
		 * same slot.
		 */
		private TableEntry<K, V> next;
		
		/**
		 * Constructs new entry out of given key and value.
		 * Key cannot be null.
		 * @param key of the entry
		 * @param value of the entry
		 * @param next entry in the list
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			if (key == null)
				throw new NullPointerException("Key cannot be null");

			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Replaces the value corresponding to this entry with the specified value
		 * @param value to be set
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Returns the value corresponding to this entry.
		 * @return value of the entry
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Returns the key corresponding to this entry.
		 * @return entry key
		 */
		public K getKey() {
			return key;
		}

		/**
		 * @return String representation of an entry
		 */
		public String toString() {
			return key + "=" + value;
		}

	}
	
	/**
	 * Constructs new {@code SimpleHashtable} with
	 * default capacity of 16
	 * @throws IllegalArgumentException if specified capacity 
	 * 		   is less than 1
	 */
	public SimpleHashtable() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructs an empty {@code SimpleHashtable}  with the 
	 * specified initial capacity.
	 * @param capacity - the initial capacity
	 * @throws IllegalArgumentException if specified capacity 
	 * 		   is less than 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1)
			throw new IllegalArgumentException("Capacity must be positive");

		while (Integer.bitCount(capacity) != 1) {
			capacity++;
		}

		table = (TableEntry<K, V>[]) new TableEntry[capacity];
	}

	/**
	 * @return Number of entries this map contains
	 */
	public int size() {
		return size;
	}

	/**
	 * @return {@code true} if map contains no entries,
	 * 			{@code false} otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Maps the specified key to the specified value in this
	 * hashtable. Key cannot be null. New array with double
	 * of its initial capacity will be allocated if needed.
	 * @param key - entry key
	 * @param value - entry value
	 * @throws NullPointerException if specified key is {@code null}
	 */
	public void put(K key, V value) {
		if (key == null)
			throw new NullPointerException("Key cannot be null");
		int sizeBefore = size;
		addEntryToTable(new TableEntry<K, V>(key, value, null), table);
		if (sizeBefore != size)
			modificationCount++;
		if (size >= table.length * LOAD_FACTOR) {
			reallocate();
		}
	}

	/**
	 * Clears this hashtable so that it contains no entries
	 */
	public void clear() {

		for (int i = 0; i<table.length; i++) {
			table[i] = null;
		}
		size = 0;
	}
	
	/**
	 * Returns the value to which the specified key is mapped.
	 * If specified key is not found or is {@code null}
	 * returns {@code null}
	 * @param key - the key whose value is to be returned.
	 * @return - the value to which the specified key is mapped.
	 */
	public V get(Object key) {
		TableEntry<K, V> entry = findEntry(key);
		
		if(entry == null) return null;
		return entry.value;
	}

	/**
	 * Tests if the specified object
	 *  is a key in this hashtable.
	 * @param key - the key to be tested
	 * @return {@code true} if map contains specified key,
	 * 			{@code false} otherwise.
	 */
	public boolean containsKey(Object key) {
		return findEntry(key) != null;
	}

	/**
	 * Returns true if this hashtable maps one or more keys to this value. 
	 * @param value - value to be tested
	 * @return {@code true} if this map maps one or more keys to the specified value
	 */
	public boolean containsValue(Object value) {
		for (TableEntry<K, V> e : table) {
			TableEntry<K, V> temp = e;

			while (temp != null) {
				if ((value == null && temp.value == null) || temp.value.equals(value)) {
					return true;
				}
				temp = temp.next;
			}
		}
		return false;
	}

	/**
	 * Removes the entry with specified key from this hashtable.
	 * Does nothing if key is not present
	 * @param key - key of entry to remove
	 */
	public void remove(Object key) {
		@SuppressWarnings("unchecked")
		int slot = tableSlot((K)key, table.length);
		TableEntry<K, V> entry = table[slot];
		if (table[slot].key.equals(key)) {
			table[slot] = table[slot].next;
			size--;
			modificationCount++;
			return;
		}
		while (entry.next != null) {
			if (entry.next.key.equals(key)) {
				entry.next = entry.next.next;
				size--;
				modificationCount++;
				return;
			}
			entry = entry.next;
		}

	}

	/**
	 *@return String representation of this map
	 */
	public String toString() {
		if (isEmpty())
			return "";
		int counter = 0;
		StringBuilder sb = new StringBuilder("[");

		for (TableEntry<K, V> e : table) {
			TableEntry<K, V> temp = e;

			while (temp != null) {
				sb.append(temp.toString());
				if (counter < size - 1) {
					sb.append(", ");
				}
				counter++;
				temp = temp.next;
			}
		}

		sb.append("]");
		return sb.toString();
	}

	/**
	 * Returns the array index where entry with 
	 * specified key is stored
	 * @param key - the key of entry
	 * @param tableLength - capacity of the array
	 * @return
	 */
	private int tableSlot(K key, int tableLength) {
		int hash = key.hashCode();
		return Math.abs(hash % tableLength);
	}

	/**
	 * Allocates new array with double of its
	 * initial size and copies all entries form old
	 * array.
	 */
	private void reallocate() {
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] expTable = (TableEntry<K, V>[]) new TableEntry[table.length * 2];
		int oldSize = size;
		for (TableEntry<K, V> e : table) {
			TableEntry<K, V> temp = e;

			while (temp != null) {
				addEntryToTable(temp, expTable);
				temp = temp.next;
			}
		}
		table = expTable;
		size = oldSize;
	}

	/**
	 * Adds new specified entry to the specified array.
	 * Methods delegating to this one must guarantee.
	 * If key value is already contained value will be 
	 * overwritten and entry will not be added.
	 * {@code null} is not given as argument.
	 * @param entry - entry to be added
	 * @param table - array where entry will be added
	 */
	private void addEntryToTable(TableEntry<K, V> entry, TableEntry<K, V>[] table) {
		int slot = tableSlot(entry.key, table.length);

		if (table[slot] == null) {
			table[slot] = entry;
			size++;
		} else {
			TableEntry<K, V> temp = table[slot];
			while (temp.key != entry.key && temp.next != null) {
				temp = temp.next;
			}

			if (temp.key == entry.key) {
				temp.value = entry.value;
			} else {
				temp.next = entry;
				size++;
			}

		}
	}
	
	/**
	 * Returns entry specified by given key.
	 * If map does not contain such key {@code null}
	 * is returned.
	 * @param key - entry key
	 * @return Entry specified by key, null if such doesn't exist
	 */
	private TableEntry<K,V> findEntry(Object key) {
		if (key == null)
			return null;
		
		@SuppressWarnings("unchecked")
		int slot = tableSlot((K)key, table.length);
		TableEntry<K, V> entry = table[slot];
		while (entry != null) {
			if (entry.key.equals(key)) {
				return entry;
			}
			entry = entry.next;
		}
		return null;
		
	}

	/**
	 * Returns new instance of {@link IteratorImpl} class
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Instances of this class enable safe iterating
	 * over {@code SimpeHashmap}  
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		/**
		 * next entry to return
		 */
		private TableEntry<K, V> next;
		/**
		 * current entry
		 */
		private TableEntry<K, V> current; 
		/**
		 * current index
		 */
		private int index;
		/**
		 * for fail-fast
		 */
		private int expectedModificationCount;

		/**
		 * Constructs new iterator and searches for
		 * next entry
		 */
		public IteratorImpl() {
			index = 0;
			next = current = null;
			expectedModificationCount = modificationCount;

			if (table != null && size > 0) {
				while (index < table.length && table[index] == null) {
					index++;
				}
				if (index < table.length) {
					next = table[index];
				}
			}
		}

		/**
		 * @return {@code true} if map contains more entries
		 */
		@Override
		public boolean hasNext() {
			return next != null;
		}

		/**
		 * @return next entry in the map
		 * @throws ConcurrentModificationException if modification is detected
		 * 			when such is not permitted
		 * @throws NoSuchElementException if there are no more entries to return
		 */
		@Override
		public TableEntry<K, V> next() {
			if (expectedModificationCount != modificationCount)
				throw new ConcurrentModificationException();
			if (next == null)
				throw new NoSuchElementException("No more elements");
			current = next;

			if (next.next != null) {
				next = next.next;
				return current;
			}

			while (index < table.length && table[index++] == null) {
			}
			if (index < table.length) {
				next = table[index];
			} else {
				next = null;
			}
			return current;
		}

		/**
		 * Removes current entry.
		 * @throws IllegalStateException if entry was already removed
		 * @throws ConcurrentModificationException if modification is detected
		 * 			when such is not permitted
		 */
		public void remove() {
			if (current == null)
				throw new IllegalStateException();
			if (expectedModificationCount != modificationCount)
				throw new ConcurrentModificationException();
			SimpleHashtable.this.remove((Object) current.getKey());
			current = null;
			expectedModificationCount = modificationCount;
		}

	}
	
	/**
	 * Test method
	 * @param args
	 */
	public static void main(String[] args) {

		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		// overwrites old grade for
		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}
		 for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
		 for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
		 System.out.printf("(%s => %d) - (%s => %d)%n", pair1.getKey(),
		 pair1.getValue(), pair2.getKey(),
		 pair2.getValue());
		 }
		 }

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove(); // sam iterator kontrolirano uklanja trenutni element
			}
		}
		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}
//		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
//		while (iter.hasNext()) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//			if (pair.getKey().equals("Ivana")) {
//				examMarks.remove("Ivana");
//			}
//		}
	}
}
