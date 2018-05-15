package hr.fer.zemris.java.gui.calc.unaryoperation;

import hr.fer.zemris.java.gui.calc.CalcModel;

public class Cos implements UnaryOperation{
	
	@Override
	public void doOperation(CalcModel model, boolean inv) {
		double value = model.getValue();
		if (inv) {
			if(value < -1 || value > 1)
				throw new IllegalArgumentException("ArcCos not defined for " + value);
			model.setValue(Math.acos(value));
		} else {
			model.setValue(Math.cos(value));
		}

	}
}
