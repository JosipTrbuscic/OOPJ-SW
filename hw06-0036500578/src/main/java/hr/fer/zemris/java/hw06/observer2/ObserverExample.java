package hr.fer.zemris.java.hw06.observer2;

/**
 * Demonstration program which test basic 
 * functionality
 * @author Josip Trbuscic
 *
 */
public class ObserverExample {
	
	/**
	 * Method where program starts
	 * @param args - command line arguments. Not used here
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		
		IntegerStorageObserver observer = new SquareValue();
		
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));
		
		istorage.addObserver(observer);
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		
		istorage.removeObserver(observer);
		

		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);


	}
}
