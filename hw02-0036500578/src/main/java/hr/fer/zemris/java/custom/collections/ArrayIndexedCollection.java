package hr.fer.zemris.java.custom.collections;
/**
 * Collection which elements are stored in resizable array. The {@code add}, {@code size} and {@code get} 
 * operations perform in constant time. The {@code clear}, {@code insert}, {@code remove}, {@code indexOf},
 *  {@code contains}, {@code toArray} and {@code forEach} operations require O(n) time. Each {@code ArrayIndexedCollection}
 *  has {@code capacity} which is always at least as large as size of the collection
 * @author Josip Trbuscic
 *
 */
public class ArrayIndexedCollection extends Collection {
	private final static int DEFAULT_ARRAY_CAPPACITY = 16;
	private int size = 0;
	private int capacity;
	private Object[] elements;
	/**
	 * Constructs new {@code ArrayIndexedCollection} with initial capacity of 16
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_ARRAY_CAPPACITY);
	}
	/**
	 * Constructs new {@code ArrayIndexedCollection} with specified initial capacity
	 * @param initialCapacity - initial capacity of collection
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1)
			throw new IllegalArgumentException("Inital array size should be greater than 0");
		
		this.capacity = initialCapacity;
		elements = new Object[initialCapacity];
	}
	/**
	 * Constructs new {@code ArrayIndexedCollection} containing the elements
	 * of specified collection
	 * @param other collection whose elements will be added to this collection
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, other.size());	
	}
	/**
	 * Constructs new {@code ArrayIndexedCollection} with specified initial capacity
	 * containing the elements of specified collection. If specified initial capacity
	 *  is not large enough to store elements of other collection capacity will be set 
	 *  to match the size of other collection.
	 * @param other collection whose elements will be added to this collection
	 * @param initialCapacity of collection
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		this(initialCapacity);
		if (other == null)
			throw new NullPointerException("Other Collection should not be null");
		
		if (initialCapacity > other.size()) {
			this.addAll(other);
		} else {
			this.elements = new Object[other.size()];
		}
	}
	
	/**
	 * Returns the number of elements in this collection.
	 * @return number of elements
	 */
	public int size() {
		return size;
	}
	/**
	 * Appends specified element to the end of this collection
	 * in complexity O(1). If array is full it will be reallocated
	 * by doubling its size. {@code null} cannot be added to the 
	 * collection.
	 * @param value - element to be added to this collection
	 * @throws NullPointerException if specified object is {@code null}
	 */
	@Override
	public void add(Object value) {
		if(value == null ) throw new NullPointerException("Null cannot be added to collection");
		this.assureCapacity();
		
		elements[size] = value;
		size++;
		
	}
	/**
	 * Returns the element at the specified position in 
	 * the array if position is valid. Complexity O(1).
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
		return elements[index];
	}
	/**
	 * Removes all elements from the collection.
	 */
	@Override
	public void clear() {
		for(int i=0;i<size;++i) {
			elements[i]=null;
		}
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
		this.assureCapacity();
		
		for(int i=size;i>position;--i) {
			elements[i]=elements[i-1];
		}
		elements[position] = value;
		size++;
		
	}
	/**
	 * Returns true if collection contains
	 * specified element
	 * @param value of the element to search for
	 * @return {@code true} if element is present
	 * 			in the collection
	 */
	@Override
	public boolean contains(Object value) {
		for(int i = 0;i<size;++i) {
			if(elements[i].equals(value)) {
				return true;
			}
		}
		
		return false;
	}
	/**
	 * Searches the collection for specified element
	 * and returns index of the first occurrence if found
	 * @param value - element to search for
	 * @return  the index of the first occurrence of the specified element,
	 *  		or -1 if this collection does not contain the element
	 */
	public int indexOf(Object value) {		
		for(int i = 0;i<size;++i) {
			if(elements[i].equals(value)) {
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
		if(index<0 || index>this.size) {
			throw new IndexOutOfBoundsException(
					"Valid range of index: 0-" +(this.size-1)+". You entered: "+index); 
		}
		
		for(int i=index;i<size-1;i++) {
			elements[i]=elements[i+1];
		}
		size--;
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
		for(int i = 0;i<size;++i) {
			array[i]=elements[i];
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
		for(int i = 0;i<size;++i) {
			processor.process(elements[i]);
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
		for(int i = 0;i<size;++i) {
			if(elements[i].equals(o)) {
				for(int j=i;j<size-1;j++) {
					elements[j]=elements[j+1];
				}
				size--;
				return true;
			}
		}
		
		return false;
	}
	/**
	 *This method will assure there is space for 
	 *next element to be added by doubling array's size.
	 *If needed elements will be copied and order will not be changed.
	 */
	private void assureCapacity() {
		if(size == capacity) {
			Object[] doubleElements = new Object[capacity*2];
			
			for(int i=0;i<size;++i) {
				doubleElements[i] = elements[i];
			}
			elements = doubleElements;
			capacity*=2;
		}
	}
}
