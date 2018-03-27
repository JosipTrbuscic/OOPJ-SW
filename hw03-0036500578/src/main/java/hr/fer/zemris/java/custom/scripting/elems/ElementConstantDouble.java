package hr.fer.zemris.java.custom.scripting.elems;

public class ElementConstantDouble extends Element {

	private double value;

	public ElementConstantDouble(double value) {
		this.value = value;

	}

	@Override
	public String asText() {
		return Double.valueOf(value).toString();
	}

	public double getValue() {
		return value;
	}

}
