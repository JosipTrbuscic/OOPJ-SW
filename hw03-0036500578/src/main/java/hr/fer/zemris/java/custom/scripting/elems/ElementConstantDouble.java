package hr.fer.zemris.java.custom.scripting.elems;
/**
 * The {@code ElementConstantDouble} class wraps primitive type of primitive double value.
 * Class provides method for converting double value to String
 * @author Josip Trbuscic
 *
 */
public class ElementConstantDouble extends Element {

	private double value; //Stores value of double type
	
	/**
	 * Constructs new {@code ElementConstantDouble}
	 * with given double value
	 * @param value
	 */
	public ElementConstantDouble(double value) {
		this.value = value;

	}
	
	/**
	 * Returns String representation of 
	 * {@code ElementConstantDouble} object
	 * 
	 */
	@Override
	public String asText() {
		return Double.valueOf(value).toString();
	}
	
	/**
	 * @return current value
	 */
	public double getValue() {
		return value;
	}

}
