package hr.fer.zemris.java.custom.collections;
/**
 * Stack structure implemented with {@code ArrayIndexedCollection}. This class offers basic stack 
 * operations {@code push}, {@code pop}, {@code peek}, {@code isEmpty}, {@code size} and {@code clear}.
 * @author Josip Trbuscic
 */
public class ObjectStack {
	private ArrayIndexedCollection stack;
	/**
	 * Constructs an empty stack
	 */
	public ObjectStack() {
		stack = new ArrayIndexedCollection();
	}
	/**
	 * Returns true if stack contains 
	 * no elements
	 * @return {@code true} if stack has no elements
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}
	/**
	 * Returns number of elements on stack
	 * @return 	number of elements on stack
	 */
	public int size() {
		return stack.size();
	}
	/**
	 * Adds element to the top of the stack. Method
	 * will assure 
	 * @param value to add on stack
	 */
	public void push(Object value) {
		stack.add(value);
	}
	/**
	 * Removes the first element from the stack
	 * and returns it.
	 * @return first element on the stack
	 */
	public Object pop() {
		if(isEmpty()) throw new EmptyStackException("Cannot return element from an empty stack");
		
		Object obj = stack.get(size()-1); 
		stack.remove(size()-1);
		
		return obj;
	}
	/**
	 * Returns the first element from the stack 
	 * but stack remains unchanged
	 * @return first element on the stack
	 */
	public Object peek() {
		if(isEmpty()) throw new EmptyStackException("Cannot return element from an empty stack");

		return stack.get(size()-1);
	}
	/*
	 * Removes all elements from the stack
	 */
	public void clear() {
		stack.clear();
	}
}
