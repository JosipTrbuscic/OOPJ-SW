package hr.fer.zemris.java.hw06.observer1;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class represents simple int wrapper 
 * with read-write properties and offers user
 * to register multiple unique observers who will be 
 * notified when value is changed
 * @author Josip Trbuscic
 *
 */
public class IntegerStorage {
	/**
	 * Value
	 */
	private int value;
	
	/**
	 * List of registered observers
	 */
	private List<IntegerStorageObserver> observers;
	
	/**
	 * Constructs new integer storage and empty 
	 * list of observers. Initial value is given
	 * as argument.
	 * @param initialValue - initial value
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new CopyOnWriteArrayList<>();
	}
	
	/**
	 * Registers specified observer to this object 
	 * @param observer - observer to be added
	 * @throws IllegalArgumentException if observer is 
	 * 			already registered
	 * 
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if(observers.contains(observer))
			throw new IllegalArgumentException("Observer already registered");
		observers.add(observer);
	}
	
	/**
	 * Removes specified observer from the list
	 * of registered observers. If specified 
	 * observer is not registered method does nothing
	 * @param observer - observer to remove
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observers.remove(observer);
	}
	
	/**
	 * Removes every observer from 
	 * the list of registered observers
	 */
	public void clearObservers() {
		observers.clear();
	}
	
	/**
	 * Returns current value
	 * @return current value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * If given value is not equals to the current
	 * one new value will be set and every observer
	 * will be notified 
	 * @param value - value to be set
	 */
	public void setValue(int value) {
		
		if(this.value != value) {
			this.value = value;
			
			if(observers != null) {
				for(IntegerStorageObserver observer : observers) {
					observer.valueChanged(this);
				}
			}
		}
	}

}
