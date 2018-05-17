package hr.fer.zemris.java.gui.calc.buttons;

import javax.swing.JButton;

/**
 * Class that represents button of a calculator.
 * Every calculator button must have a name and
 * position stored as a string in format "x,y" 
 * where x and y are coordinates in layout grid 
 * @author Josip Trbuscic
 *
 */
public class CalculatorButton extends JButton{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Position in layout grid
	 */
	private String position;
	
	/**
	 * Constructor.
	 * @param name - button name
	 * @param position - button position
	 */
	public CalculatorButton(String name, String position) {
		super(name);
		this.position = position;
	}
	
	/**
	 * Returns position of a button
	 * @return position of a button
	 */
	public String getPosition() {
		return position;
	}
}
