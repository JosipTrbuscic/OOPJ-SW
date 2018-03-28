package hr.fer.zemris.java.custom.scripting.elems;
/**
 * Class represents a valid operator +, -, *, /, or ^.
 * @author Josip Trbuscic
 *
 */
public class ElementOperator extends Element{
	private String symbol; //Stores operator as string
	
	/**
	 * Constructs new {@code ElementOperator} 
	 * specified by valid symbol
	 * @param symbol
	 */
	public ElementOperator(String symbol) {
		if(symbol == null) throw new NullPointerException("Cannot construct operator with null value");
		this.symbol = symbol;
	}

	/**
	 * Returns String representation of 
	 * {@code ElementOperator} object
	 */
	@Override
	public String asText() {
		return symbol;
	}
	
	/**
	 * @return operator
	 */
	public String getSymbol() {
		return symbol;
	}
}
