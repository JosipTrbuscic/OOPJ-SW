package hr.fer.zemris.java.hw16.jdraw.colors;

import java.awt.Color;

/**
 * Interface representing listeners which will be notified 
 * when color is changed
 * @author Josip Trbuscic
 *
 */
public interface ColorChangeListener {
	
	/**
	 * Action performed when color is changed
	 * @param source - source of the change
	 * @param oldColor - old color
	 * @param newColor - new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
