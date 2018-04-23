package hr.fer.zemris.java.hw06.observer2;

/**
 * Class used as storage for values when 
 * state of Integer storage is being changed.
 * Instances of this class can be used to give 
 * more detailed info about state change.
 * @author Josip Trbuscic
 *
 */
public class IntegerStorageChange {
	
	/**
	 * Integer storage whose values 
	 * are stored in this class
	 */
	private IntegerStorage istorage;
	
	/**
	 * State of Integer Storage before 
	 * the change
	 */
	private int beforeValue;
	
	/**
	 * State of Integer Storage after
	 * the change
	 */
	private int afterValue;
	
	/**
	 * Constructs new instance of this class
	 * with given parameters
	 * @param istorage - Integer storage
	 * @param beforeValue - value before state change
	 * @param afterValue - value after state change
	 */
	public IntegerStorageChange(IntegerStorage istorage, int beforeValue, int afterValue) {
		if(istorage == null) throw new NullPointerException("Integer storage cannot be null");
		
		this.istorage = istorage;
		this.beforeValue = beforeValue;
		this.afterValue = afterValue;
	}

	/**
	 * Returns integer storage
	 * @return integer storage
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}

	/**
	 * Returns value before the state change
	 * @return value before the state change
	 */
	public int getBeforeValue() {
		return beforeValue;
	}

	/**
	 * Returns value after the state change
	 * @return value after the state change
	 */
	public int getAfterValue() {
		return afterValue;
	}
}
