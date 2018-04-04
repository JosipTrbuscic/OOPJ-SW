package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Instances of this class are used for storing {@code TurtleState}s.
 *  States are stored on a stack. Class offers basic stack
 * operations like push, pop and peek
 * @author Josip Trbuscic
 *
 */
public class Context {
	/**
	 * Stack in which {@code TurtleState}s are stored 
	 */
	private ObjectStack stack;
	
	/**
	 * Constructs new {@code ObjectStack} with default size
	 */
	public Context() {
		stack = new ObjectStack();
	}
	
	/**
	 * Returns {@code TurtleState} from top of the stack
	 * without removing it
	 * @return {@code TurtleState} from top of a stack
	 * @throws EmptyStackException if stack is empty
	 */
	public TurtleState getCurrentState() {
		return (TurtleState) stack.peek();
	}
	
	/**
	 * Adds given {@code TurtleState} to the top of the stack
	 * @param state which will be added
	 * @throws NullPointerException if given argument is null
	 */
	public void pushState(TurtleState state) {
		if(state == null) throw new NullPointerException("Turtle state cannot be null");
		
		stack.push(state);
	}
	
	/**
	 * Removes the first {@code TurtleState} from top of the stack.
	 */
	public void popState() {
		stack.pop();
	}
}
