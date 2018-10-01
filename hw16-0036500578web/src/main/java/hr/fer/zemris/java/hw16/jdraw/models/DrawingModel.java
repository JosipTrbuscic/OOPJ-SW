package hr.fer.zemris.java.hw16.jdraw.models;

import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.GeometricalObject;

/**
 * Interface representing model responsible 
 * for tracking all painted objects and 
 * notifying listeners
 * @author Josip Trbuscic
 *
 */
public interface DrawingModel {
	
	/**
	 * Returns number of geometric objects	
	 * @return number of geometric objects	
	 */
	public int getSize();
	
	/**
	 * Returns object at specified index
	 * @param index - index of object
	 * @return object at specified index
	 */
	public  GeometricalObject getObject(int index);
	
	/**
	 * Adds specified geometric object to this model
	 * @param object - object to add
	 */
	public void add(GeometricalObject object);
	
	/**
	 * Registers model listener to this model
	 * @param l - listener to register
	 */
	public void addDrawingModelListener(DrawingModelListener l);
	
	/**
	 * Unregisters model listener from this model
	 * @param l - listener to unregister
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
	
	/**
	 * Removes specified geometric object from this model
	 * @param object - object to remove
	 */
	public void remove(GeometricalObject object);
	
	/**
	 * Moves specified object for specified number of places in 
	 * the collection
	 * @param object - object to move
	 * @param offset - number of places to move for
	 */
	void changeOrder(GeometricalObject object, int offset);
}
