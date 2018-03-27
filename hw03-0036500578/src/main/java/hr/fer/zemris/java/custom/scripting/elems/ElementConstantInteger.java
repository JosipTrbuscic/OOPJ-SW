package hr.fer.zemris.java.custom.scripting.elems;

public class ElementConstantInteger extends Element {

	private int value;
	
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return Integer.valueOf(value).toString();
	}
	
	public int getValue() {
		return value;
	}
	
}