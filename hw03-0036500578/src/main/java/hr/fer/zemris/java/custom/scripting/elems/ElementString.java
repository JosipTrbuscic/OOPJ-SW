package hr.fer.zemris.java.custom.scripting.elems;
/**
 * Class represents character string. Inherits {@code Element} class.
 * string is stored in local variable.
 * @author Josip Trbuscic
 *
 */
public class ElementString extends Element {
	
	private String value; //Stores string value
	
	/**
	 * Constructs new {@code ElementString} specified by 
	 * given value
	 * @param value
	 */
	public ElementString(String value) {
		if(value == null) throw new NullPointerException("Cannot construct string with null value");
		
		this.value = value;
	
	}

	/**
	 * Returns String representation of 
	 * {@code ElementString} object
	 */
	@Override
	public String asText() {
		return value;
	}
	
	/**
	 * Returns string value
	 * @return string value
	 */
	public String getValue() {
		return value;
	}
}
