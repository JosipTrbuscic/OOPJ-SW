package hr.fer.zemris.java.hw16.jdraw.editors;

import javax.swing.JPanel;

/**
 * Abstract class which defines methods implemented by classes 
 * that are able to update info about specific geometric object
 * @author Josip Trbuscic
 *
 */
public abstract class GeometricalObjectEditor extends JPanel{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Verifies users input for object parameters
	 */
	public abstract void checkEditing();
	
	/**
	 * Sets new object parameters
	 */
	public abstract void acceptEditing();
}
