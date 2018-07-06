package hr.fer.zemris.java.hw16.jdraw.models;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.GeometricalObject;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.GeometricalObjectListener;

/**
 * Implementation of {@link DrawingModel} interface
 * @author Josip Trbuscic
 *
 */
public class DrawingModelImpl implements DrawingModel, GeometricalObjectListener{
	
	/**
	 * Stored geometric objects
	 */
	private List<GeometricalObject> objects;
	
	/**
	 * Registered listeners
	 */
	private List<DrawingModelListener> listeners;
		
	/**
	 * Constructor
	 */
	public DrawingModelImpl() {
		objects = new ArrayList<>();
	}
	
	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(0,object);
		notifyListeners();
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		if(listeners == null) {
			listeners = new ArrayList<>();
		}
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);		
	}
	
	private void notifyListeners() {
		listeners.forEach(l->l.objectsAdded(this, objects.size()-1, objects.size()-1));
	}

	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		listeners.forEach(l->l.objectsChanged(this, objects.indexOf(o), objects.indexOf(o)));
		
	}

	@Override
	public void remove(GeometricalObject object) {
		int index =  objects.indexOf(object);
		objects.remove(object);
		listeners.forEach(l->l.objectsRemoved(this, index, index));
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int index = objects.indexOf(object);
		objects.remove(index);
		objects.add(index + offset, object);
		listeners.forEach(l->l.objectsChanged(this, index, index));
	}

}
