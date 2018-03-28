package hr.fer.zemris.java.custom.scripting.elems;
/*
 * Class represents valid function which consists of function name. 
 * Valid function name stars with '@' followed by letter and then follows 
 * zero or more letters, numbers or underscores
 */
public class ElementFunction extends Element{
	private String name; //Stores variable name
	
	/**
	 * Constructs new {@code ElementFunction} specified 
	 * by valid name;
	 * @param name - function name
	 */
	public ElementFunction(String name) {
		if(name == null) throw new NullPointerException("Cannot construct function with null value");
		
		this.name = name;
	
	}
	
	/**
	 * Returns String representation of 
	 * {@code ElementFunction} object
	 */
	@Override
	public String asText() {
		return name;
	}
	/**
	 * @return function name
	 */
	public String getName() {
		return name;
	}
}
