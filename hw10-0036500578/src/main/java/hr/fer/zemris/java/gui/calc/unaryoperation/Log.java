package hr.fer.zemris.java.gui.calc.unaryoperation;

import hr.fer.zemris.java.gui.calc.CalcModel;

public class Log implements UnaryOperation{

	@Override
	public void doOperation(CalcModel model, boolean inv) {
		double value = model.getValue();
		
		if(inv) {
			model.setValue(Math.pow(10, value));
		} else {
			model.setValue(Math.log10(value));
		}
	}

}
