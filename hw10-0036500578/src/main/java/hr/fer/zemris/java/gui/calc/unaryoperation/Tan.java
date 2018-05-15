package hr.fer.zemris.java.gui.calc.unaryoperation;

import hr.fer.zemris.java.gui.calc.CalcModel;

public class Tan implements UnaryOperation{

	@Override
	public void doOperation(CalcModel model, boolean inv) {
		double value = model.getValue();
		if (inv) {
			model.setValue(Math.atan(value));
		} else {
			model.setValue(Math.tan(value));
		}

	}
}
