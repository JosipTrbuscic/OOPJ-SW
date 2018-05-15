package hr.fer.zemris.java.gui.calc.binaryoperation;

import hr.fer.zemris.java.gui.calc.CalcModel;

public abstract class BinaryOperation {
	
	public abstract void doOperation(CalcModel model);
	
	protected void calculatePending(CalcModel model) {
		double result = model.getValue();
		if(model.isActiveOperandSet()) {
			result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(),model.getValue());
		}
		model.clearAll();
		model.setActiveOperand(result);
	}
}
