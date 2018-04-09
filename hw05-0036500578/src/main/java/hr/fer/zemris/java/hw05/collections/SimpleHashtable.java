package hr.fer.zemris.java.hw05.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	public static final int DEFAULT_SIZE = 16;
	private TableEntry<K, V>[] table;
	private int size = 0;
	private int modificationCount = 0;

	public static class TableEntry<K, V> {
		private K key;
		private V value;
		private TableEntry<K, V> next;

		public TableEntry(K key, V value, TableEntry<K, V> next) {
			if (key == null)
				throw new NullPointerException("Key cannot be null");

			this.key = key;
			this.value = value;
			this.next = next;
		}

		public void setValue(V value) {
			this.value = value;
		}

		public V getValue() {
			return value;
		}

		public K getKey() {
			return key;
		}

		public String toString() {
			return key + "=" + value;
		}

	}

	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		this(DEFAULT_SIZE);
	}

	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1)
			throw new IllegalArgumentException("Capacity must be positive");

		while (Integer.bitCount(capacity) != 1) {
			capacity++;
		}

		table = (TableEntry<K, V>[]) new TableEntry[capacity];
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void put(K key, V value) {
		if (key == null)
			throw new NullPointerException("Key cannot be null");
		int sizeBefore = size;
		addEntryToTable(new TableEntry<K, V>(key, value, null), table, true);
		if (sizeBefore != size)
			modificationCount++;
		if (size >= table.length * 0.75) {
			reallocate();
		}
	}

	public void clear() {

		for (TableEntry<K, V> e : table) {
			e = null;
		}
		size = 0;
	}

	public V get(Object key) {
		if (key == null)
			return null;

		int slot = tableSlot(key, table.length);
		TableEntry<K, V> entry = table[slot];
		while (entry != null) {
			if (entry.key.equals(key)) {
				return entry.value;
			}
			entry = entry.next;
		}
		return null;
	}

	public boolean containsKey(Object key) {
		if (key == null)
			return false;

		int slot = tableSlot(key, table.length);
		TableEntry<K, V> entry = table[slot];
		while (entry != null) {
			if (entry.key.equals(key)) {
				return true;
			}
			entry = entry.next;
		}
		return false;

	}

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

	public void remove(Object key) {
		int slot = tableSlot(key, table.length);
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

	private int tableSlot(Object key, int tableLength) {
		int hash = key.hashCode();
		if (hash == Integer.MIN_VALUE)
			throw new ArithmeticException("Index out of range");

		return Math.abs(hash) % tableLength;
	}

	private void reallocate() {
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] expTable = (TableEntry<K, V>[]) new TableEntry[table.length * 2];
		for (TableEntry<K, V> e : table) {
			TableEntry<K, V> temp = e;

			while (temp != null) {
				addEntryToTable(temp, expTable, false);
				temp = temp.next;
			}
		}
		table = expTable;
	}

	private void addEntryToTable(TableEntry<K, V> entry, TableEntry<K, V>[] table, boolean incrementSize) {
		int slot = tableSlot(entry.key, table.length);

		if (table[slot] == null) {
			table[slot] = entry;
			if (incrementSize) {
				size++;
			}
		} else {
			TableEntry<K, V> temp = table[slot];
			while (temp.key != entry.key && temp.next != null) {
				temp = temp.next;
			}

			if (temp.key == entry.key) {
				temp.value = entry.value;
			} else {
				temp.next = entry;
				if (incrementSize) {
					size++;
				}
			}

		}
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		private TableEntry<K, V> next;
		private TableEntry<K, V> current;
		private int index;
		private int expectedModificationCount;

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

		@Override
		public boolean hasNext() {
			return next != null;
		}

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
		// for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
		// for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
		// System.out.printf("(%s => %d) - (%s => %d)%n", pair1.getKey(),
		// pair1.getValue(), pair2.getKey(),
		// pair2.getValue());
		// }
		// }

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
