package hr.fer.zemris.java.custom.collections;

public class ArrayIndexCollection extends Collection {
	private final static int DEFAULT_ARRAY_CAPPACITY = 16;
	private int size = 0;
	private int capacity; // = DEFAULT_ARRAY_CAPPACITY
	private Object[] elements; // = DEFAULT_ARRAY_CAPPACITY

	public ArrayIndexCollection() {
		this(DEFAULT_ARRAY_CAPPACITY);
	}

	public ArrayIndexCollection(int initialCapacity) {
		if (initialCapacity < 1)
			throw new IllegalArgumentException("Inital array size should be greater than 0");
		
		this.capacity = initialCapacity;
		elements = new Object[initialCapacity];
	}
	
	public ArrayIndexCollection(Collection other) {
		this(other, other.size());	
	}

	public ArrayIndexCollection(Collection other, int initialCapacity) {
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
		
		for(int i=index;i<size-1;--i) {
			elements[i]=elements[i+1];
		}
		size--;
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

	public int getCapacity() {
		return capacity;
	}

	public Object[] getElements() {
		return elements;
	}

}
