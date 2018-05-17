package hr.fer.zemris.java.gui.calc.buttons;

import java.util.function.UnaryOperator;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.gui.calc.CalcModel;

/**
 * Calculator button used for unary operations such as 
 * sin, cos, tan, ctg, log, multiplicative inverse
 * and their inverses.
 * @author Josip Trbuscic
 *
 */
public class UnaryOperationButton extends CalculatorButton{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param name - button name
	 * @param position - position
	 * @param model - calculator model
	 * @param normal - default unary operation
	 * @param inverseOp - inverse of default unary operation
	 * @param inv - check box used to indicate if inverse 
	 * 				operation should be used
	 */
	public UnaryOperationButton(String name, String position, CalcModel model, UnaryOperator<Double> normal, 
									UnaryOperator<Double> inverseOp, JCheckBox inv) {
		super(name, position);
		this.addActionListener(l->{
			try {
				if(inv.isSelected()) {
					model.setValue(inverseOp.apply(model.getValue()));
				}else {
					model.setValue(normal.apply(model.getValue()));
				}
			} catch(ArithmeticException e) {
				JOptionPane.showMessageDialog(this.getParent(), e.getMessage(), "Arithmetic Error", JOptionPane.ERROR_MESSAGE);
				model.clear();
			}
			
		});
	}

}
