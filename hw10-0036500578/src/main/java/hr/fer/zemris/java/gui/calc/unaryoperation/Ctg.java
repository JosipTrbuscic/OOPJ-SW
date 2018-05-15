package hr.fer.zemris.java.gui.calc.unaryoperation;

import hr.fer.zemris.java.gui.calc.CalcModel;

public class Ctg implements UnaryOperation{

	@Override
	public void doOperation(CalcModel model, boolean inv) {
		double value = model.getValue();
		if (inv) {
			model.setValue(1/Math.atan(value));
		} else {
			model.setValue(1/Math.tan(value));
		}

	}
}
