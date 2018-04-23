package hr.fer.zemris.java.hw06.observer2;

import hr.fer.zemris.java.hw06.observer1.IntegerStorage;

/**
 * Implementing this interface will allow observer
 * register observer to register to a subject and listen
 * for changes.
 * The subject automatically notifies all registered
 *  observers by calling method from this
 *  interface on each registered observer. 
 */
public interface IntegerStorageObserver {
	
	/**
	 * Method called by instance of {@link IntegerStorage} when it's 
	 * value is changed.
	 * @param istorage - subject
	 */
	public void valueChanged(IntegerStorageChange istorage);
}
