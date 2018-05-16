package hr.fer.zemris.java.gui.calc.binaryoperation;

import hr.fer.zemris.java.gui.calc.CalcModel;

public class Power extends BinaryOperation{

	@Override
	public void doOperation(CalcModel model) {
		calculatePending(model);
		model.setPendingBinaryOperation((arg1,arg2)->Math.pow(arg1, arg2));
	}

}
