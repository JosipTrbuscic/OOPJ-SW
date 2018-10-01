package hr.fer.zemris.java.hw16.jdraw.geometricalObjects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jdraw.editors.CircleEditor;
import hr.fer.zemris.java.hw16.jdraw.editors.GeometricalObjectEditor;

/**
 * Extension of geometric object which represents a circle object
 * @author Josip Trbuscic
 *
 */
public class Circle extends GeometricalObject{
	/**
	 * Center coordinates
	 */
	private int x;
	private int y;
	
	/**
	 * Radius
	 */
	private double r;
	
	/**
	 * Outline color
	 */
	private Color color;
	
	/**
	 * Constructor
	 * @param x - center coordinate
	 * @param y - center coordinate
	 * @param r - radius
	 * @param color - outline color
	 */
	public Circle(int x, int y, double r, Color color) {
		super();
		this.x = x;
		this.y = y;
		this.r = r;
		this.color = color;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}
	
	/**
	 * Returns x coordinate
	 * @return x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets x coordinate
	 * @param x - coordinate
	 */
	public void setX(int x) {
		this.x = x;
		notifyListeners();
	}

	/**
	 * Returns y coordinate
	 * @return y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets y coordinate
	 * @param y - coordinate
	 */
	public void setY(int y) {
		this.y = y;
		notifyListeners();

	}

	/**
	 * Returns radius
	 * @return radius
	 */
	public double getR() {
		return r;

	}

	/**
	 * Sets radius
	 * @param r - new radius
	 */
	public void setR(double r) {
		this.r = r;
		notifyListeners();

	}

	/**
	 * Returns color
	 * @return color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets new color
	 * @param color - new color
	 */
	public void setColor(Color color) {
		this.color = color;
		notifyListeners();
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Circle (");
		sb.append(x).append(",").append(y).append("), ").append((int)r);
		return sb.toString();
	}
	
	/**
	 * Notifies listeners about this objects change
	 */
	private void notifyListeners() {
		listeners.forEach(l->l.geometricalObjectChanged(this));
	}

	@Override
	public String getJvdString() {
		StringBuilder sb = new StringBuilder("CIRCLE ");
		sb.append(x).append(" ").append(y).append(" ").append((int)r)
		.append(" ").append(color.getRed()).append(" ")
		.append(color.getGreen()).append(" ").append(color.getBlue());
		
		return sb.toString();
	}
	
}
