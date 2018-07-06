package hr.fer.zemris.java.hw16.jdraw.colors;

import java.awt.Color;

/**
 * Interface representing color provider.  
 * @author Josip Trbuscic
 *
 */
public interface IColorProvider {
	
	/**
	 * Returns currently selected color
	 * @return currently selected color
	 */
	public Color getCurrentColor();
	
	/**
	 * Registers new listener
	 * @param l - listener to register
	 */
	public void addColorChangeListener(ColorChangeListener l);
	
	/**
	 * Unregisters specified listener from this provider
	 * @param l - listener to unregister
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}
