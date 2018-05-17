package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.event.ActionListener;

/**
 * Generic calculator button that offers user to 
 * specify any {@link ActionListener} which will be
 * added to the button
 * @author Josip Trbuscic
 *
 */
public class GenericCalculatorButton extends CalculatorButton{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param name - button name
	 * @param position - button position
	 * @param listener - action listener
	 */
	public GenericCalculatorButton(String name, String position, ActionListener listener) {
		super(name, position);
		this.addActionListener(listener);
	}

}
