package hr.fer.zemris.java.gui.calc.unaryoperation;

import hr.fer.zemris.java.gui.calc.CalcModel;

public class MulInverse implements UnaryOperation{

	@Override
	public void doOperation(CalcModel model, boolean inv) {
		double value = model.getValue();
		
		if (!inv) {
			if(value == 0) throw new ArithmeticException("Division by zero is not alowed");
			model.setValue(1/value);
		}

	}
}
