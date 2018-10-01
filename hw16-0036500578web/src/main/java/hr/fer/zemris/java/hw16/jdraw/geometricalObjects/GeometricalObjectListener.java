package hr.fer.zemris.java.hw16.jdraw.geometricalObjects;

/**
 * Listener which is notified when property of geometric object is changed
 * @author Josip Trbuscic
 *
 */
public interface GeometricalObjectListener {
	
	/**
	 * Performs action when specified geometric object is changed
	 * @param o - changed object
	 */
	public void geometricalObjectChanged(GeometricalObject o);
}
