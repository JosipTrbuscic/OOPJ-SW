package hr.fer.zemris.java.hw06.observer2;


/**
 * Implementation of {@link IntegerStorageObserver} that 
 * will track how many times state of subject has changed 
 * since registration.
 * @author Josip Trbuscic
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
	
	/**
	 * Number of state changes
	 */
	private int counter = 0;
	
	/**
	 * Prints to standard output number of subject's state changes
	 * since registration of this observer
	 * @param istorage - subject
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorageChange) {
		System.out.println("Number of changes since tracking: " + (++counter));
	}

}
