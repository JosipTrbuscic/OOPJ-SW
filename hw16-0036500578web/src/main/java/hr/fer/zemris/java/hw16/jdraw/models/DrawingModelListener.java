package hr.fer.zemris.java.hw16.jdraw.models;

/**
 * Interface representing listener which is notified when 
 * change in drawing model happens
 * @author Josip Trbuscic
 *
 */
public interface DrawingModelListener {
	
	/**
	 * Action performed when objects are added to the list
	 * @param source
	 * @param index0
	 * @param index1
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);
	
	/**
	 * Action performed when objects are removed from the list
	 * @param source
	 * @param index0
	 * @param index1
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);
	
	/**
	 * Action performed when objects from the model were modified
	 * @param source
	 * @param index0
	 * @param index1
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
