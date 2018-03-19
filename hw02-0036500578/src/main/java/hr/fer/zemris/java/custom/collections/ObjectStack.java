package hr.fer.zemris.java.custom.collections;

public class ObjectStack {
	private ArrayIndexedCollection stack;
	
	public ObjectStack() {
		stack = new ArrayIndexedCollection();
	}

	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
	public int size() {
		return stack.size();
	}
	
	public void push(Object value) {
		stack.add(value);
	}
	
	public Object pop() {
		if(isEmpty()) throw new EmptyStackException("Cannot return element from an empty stack");
		
		Object obj = peek();//stack.get(size()-1); 
		stack.remove(size()-1);
		
		return obj;
	}
	
	public Object peek() {
		if(isEmpty()) throw new EmptyStackException("Cannot return element from an empty stack");

		return stack.get(size()-1);
	}
	
	public void clear() {
		stack.clear();
	}
}
