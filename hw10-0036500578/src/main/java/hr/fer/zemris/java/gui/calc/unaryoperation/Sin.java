package hr.fer.zemris.java.gui.calc.unaryoperation;


import hr.fer.zemris.java.gui.calc.CalcModel;

public class Sin implements UnaryOperation {

	@Override
	public void doOperation(CalcModel model, boolean inv) {
		double value = model.getValue();
		if (inv) {
			if(value < -1 || value > 1)
				throw new ArithmeticException("ArcSin not defined for " + value);
			model.setValue(Math.asin(value));
		} else {
			model.setValue(Math.sin(value));
		}

	}

}
