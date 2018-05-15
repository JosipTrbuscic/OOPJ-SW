package hr.fer.zemris.java.gui.calc.unaryoperation;

import hr.fer.zemris.java.gui.calc.CalcModel;

public interface UnaryOperation {
	
	void doOperation(CalcModel model, boolean inv);
}
