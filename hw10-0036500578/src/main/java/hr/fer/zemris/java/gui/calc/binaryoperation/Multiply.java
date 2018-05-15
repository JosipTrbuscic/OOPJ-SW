package hr.fer.zemris.java.gui.calc.binaryoperation;

import hr.fer.zemris.java.gui.calc.CalcModel;

public class Multiply extends BinaryOperation{
	@Override
	public void doOperation(CalcModel model) {
		calculatePending(model);
		model.setPendingBinaryOperation((arg1,arg2)->arg1*arg2);
	}
}
