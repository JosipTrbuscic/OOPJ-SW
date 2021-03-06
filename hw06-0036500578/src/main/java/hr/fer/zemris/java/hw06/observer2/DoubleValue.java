package hr.fer.zemris.java.hw06.observer2;

/**
 * Implementation of {@link IntegerStorageObserver} that 
 * will print double of last value set in the subject for
 * n times before removing itself from list of registered 
 * observers.
 * @author Josip Trbuscic
 *
 */
public class DoubleValue implements IntegerStorageObserver {
	
	/**
	 * Remaining number of usages
	 */
	int counter;
	
	/**
	 * Constructs new DoubleValueObserver 
	 * and sets number of usages
	 * @param counter - number of usages
	 * @throws IllegalArgumentException if
	 * 			number of usages is not positive
	 */
	public DoubleValue(int counter) {
		if(counter < 1) throw new IllegalArgumentException("Counter must be positive");
		
		this.counter = counter;
	}
	
	/**
	 * Action performed when value of subject has changed. 
	 * Prints double of last value set in the subject and 
	 * unregisters itself if number of remaining usages 
	 * reaches zero
	 * @param istorageChange - subject  
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorageChange) {
		System.out.println("Double value: " + istorageChange.getAfterValue()*2);
		
		if(--counter == 0 ) {
			istorageChange.getIstorage().removeObserver(this);
		}
	}

}
