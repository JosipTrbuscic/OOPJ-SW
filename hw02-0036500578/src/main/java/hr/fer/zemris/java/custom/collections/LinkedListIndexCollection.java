package hr.fer.zemris.java.custom.collections;

public class LinkedListIndexCollection extends Collection {
	private int size = 0;
	private ListNode first;
	private ListNode last;
	
	private static class ListNode{
		Object value;
		ListNode next;
		ListNode previous;
		
		@Override
		public boolean equals(Object obj) {
			if(obj == null) return false;
			if(!(obj instanceof ListNode)) return false;
			
			ListNode other = (ListNode) obj;
			return this.value.equals(other.value);
		}
	}
	
	public LinkedListIndexCollection() {
		first = null;
		last = null;
	}
	
	public LinkedListIndexCollection(Collection other) {
		if (other == null)
			throw new NullPointerException("Other Collection should not be null");
		
		this.addAll(other);
	}
	/**
	 * Appends specified element to the end of this collection
	 * in complexity O(1). {@code null} cannot be added to the 
	 * collection.
	 * @param value - element to be added to this collection
	 * @throws NullPointerException if specified object is {@code null}
	 */
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
	
	public Object get(int index) {
		if(index<0 || index>this.size-1) {
			throw new IndexOutOfBoundsException(
					"Valid range of index: 0-" +(this.size-1)+". You entered: "+index); 
		}
		
		return iterateToIndex(index).value;
	}
	
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}
	
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
		
		
	}
	
	
	private ListNode iterateToIndex(int index) {
		ListNode node;
		int counter=0;
		boolean reverse=false;
		
		if(index <= size/2) {
			node = first;
		} else {
			node = last;
			counter = size - index;
			reverse = true;
		}
		
		for(;counter<index;counter++) {
			if(reverse) {
				node = node.previous;
			} else {
				node = node.next;
			}
		}
		
		return node;
	}
	
	public static void main(String[] args) {
		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		col.add(new Integer(20));
		col.add("New York");
		col.add("San Francisco"); // here the internal array is reallocated to 4
		System.out.println(col.contains("New York")); // writes: true
		col.remove(1); // removes "New York"; shifts "San Francisco" to position 1
		System.out.println(col.get(1)); // writes: "San Francisco"
		System.out.println(col.size()); // writes: 2
		col.add("Los Angeles");
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col);
		// This is local class representing a Processor which writes objects to System.out
		class P extends Processor {
		public void process(Object o) {
		System.out.println(o);
		}
		};
		System.out.println("col elements:");
		col.forEach(new P());
		System.out.println("col elements again:");
		System.out.println(Arrays.toString(col.toArray()));
		System.out.println("col2 elements:");
		col2.forEach(new P());
		System.out.println("col2 elements again:");
		System.out.println(Arrays.toString(col2.toArray()));
		System.out.println(col.contains(col2.get(1))); // true
		System.out.println(col2.contains(col.get(1))); // true
		col.remove(new Integer(20)); // removes 20 from collection (at position 0).
		
	}
}
