package hr.fer.zemris.java.gui.calc.binaryoperation;

import hr.fer.zemris.java.gui.calc.CalcModel;

public class Division extends BinaryOperation{

	@Override
	public void doOperation(CalcModel model) {
		calculatePending(model);
		model.setPendingBinaryOperation((arg1,arg2)->{
			if(arg2 == 0)throw new ArithmeticException("Division by zero");
			return arg1/arg2;
		});
	}

}
