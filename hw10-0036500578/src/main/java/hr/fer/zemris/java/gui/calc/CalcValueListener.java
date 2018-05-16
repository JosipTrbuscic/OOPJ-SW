package hr.fer.zemris.java.gui.calc;

/**
 * Interface which defines a calculator listener
 * @author Josip Trbuscic
 *
 */
public interface CalcValueListener {
	
	/**
	 * Action performed by a listener when it gets notified
	 * by calculator model
	 * @param model - calculator model
	 */
	void valueChanged(CalcModel model);
}