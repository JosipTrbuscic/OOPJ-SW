package hr.fer.zemris.java.custom.scripting.elems;
/**
 * The {@code ElementConstantInteger} class wraps primitive type of primitive integer value.
 * Class provides method for converting integer value to String
 * @author Josip Trbuscic
 *
 */
public class ElementConstantInteger extends Element {

	private int value;//Stores value of integer type
	
	/**
	 * Constructs new {@code ElementConstantInteger}
	 * with given integer value
	 * @param value
	 */
	public ElementConstantInteger(int value) {
		
		this.value = value;
	}
	
	/**
	 * Returns String representation of 
	 * {@code ElementConstantInteger} object
	 * 
	 */
	@Override
	public String asText() {
		return Integer.valueOf(value).toString();
	}
	
	/** 
	 * @return current value
	 */
	public int getValue() {
		return value;
	}
	
}
