package hr.fer.zemris.java.hw16.jdraw.geometricalObjects;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jdraw.editors.GeometricalObjectEditor;

/**
 * Abstract class representing geometric object 
 * @author Josip Trbuscic
 *
 */
public abstract class GeometricalObject {
	/**
	 * Obejcts listeners
	 */
	protected List<GeometricalObjectListener> listeners;
	
	/**
	 * Creates editor object for this geometric object
	 * @return editor object for this geometric object
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();
	
	/**
	 * Performs action on object visitors visit
	 * @param v
	 */
	public abstract void accept(GeometricalObjectVisitor v);
	
	/**
	 * Returns .jvd string representation of this object
	 * @return .jvd string representation of this object
	 */
	public abstract String getJvdString();
	
	/**
	 * Registers specified listener to this object
	 * @param l - listener to register
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		if(listeners == null) {
			listeners = new ArrayList<>();
		}
		listeners.add(l);
	}
	
	/**
	 * Unregisters specified listener from this object
	 * @param l - listener to unregister
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}
	

}
