package hr.fer.zemris.java.gui.calc.buttons;

import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.CalcModel;

/**
 * Calculator button used for arithmetic binary operations such as 
 * addition, division, multiplication and subtraction.
 * @author Josip Trbuscic
 *
 */
public class BinaryOperationButton extends CalculatorButton{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param name - button name
	 * @param position - button position
	 * @param model - calculator model
	 * @param operation - binary operation
	 */
	public BinaryOperationButton(String name, String position,CalcModel model, DoubleBinaryOperator operation) {
		super(name, position);
		this.addActionListener(l->{
			double result = model.getValue();
			if(model.isActiveOperandSet()) {
				result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(),model.getValue());
			}
			model.clearAll();
			model.setActiveOperand(result);
			model.setPendingBinaryOperation(operation);
		});
	}

}
