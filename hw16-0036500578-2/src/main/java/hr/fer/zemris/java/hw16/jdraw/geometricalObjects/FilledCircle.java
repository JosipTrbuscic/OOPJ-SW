package hr.fer.zemris.java.hw16.jdraw.geometricalObjects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jdraw.editors.FilledCircleEditor;
import hr.fer.zemris.java.hw16.jdraw.editors.GeometricalObjectEditor;


/**
 * Extension of geometric object which represents a circle object
 * filled with specific color
 * @author Josip Trbuscic
 *
 */
public class FilledCircle extends GeometricalObject {
	
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
	private Color outlineColor;
	
	/**
	 * Fill color
	 */
	private Color fillColor;

	/**
	 * Constructor
	 * @param x - coordinate
	 * @param y - coordinate
	 * @param r - radius
	 * @param outlineColor
	 * @param fillColor
	 */
	public FilledCircle(int x, int y, double r, Color outlineColor, Color fillColor) {
		super();
		this.x = x;
		this.y = y;
		this.r = r;
		this.outlineColor = outlineColor;
		this.fillColor = fillColor;
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
	 * Returns outline color
	 * @return outline color
	 */
	public Color getOutlineColor() {
		return outlineColor;
	}

	/**
	 * Returns fill color 
	 * @return fill color
	 */
	public Color getFillColor() {
		return fillColor;
	}


	/**
	 * Sets new outline color
	 * @param outlineColor
	 */
	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
		notifyListeners();
	}

	/**
	 * Sets new fill color
	 * @param fillColor
	 */
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
		notifyListeners();
	}
	
	/**
	 * Notifies listeners about property change
	 */
	private void notifyListeners() {
		listeners.forEach(l->l.geometricalObjectChanged(this));
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Filled circle (");
		sb.append(x).append(",").append(y).append("), ").append((int) r).append(", #")
				.append(Integer.toHexString(fillColor.getRGB()).substring(2).toUpperCase());
		return sb.toString();
	}
	
	@Override
	public String getJvdString() {
		StringBuilder sb = new StringBuilder("FCIRCLE ");
		sb.append(x).append(" ").append(y).append(" ").append((int)r)
		.append(" ").append(outlineColor.getRed()).append(" ")
		.append(outlineColor.getGreen()).append(" ").append(outlineColor.getBlue())
		.append(" ").append(fillColor.getRed()).append(" ")
		.append(fillColor.getGreen()).append(" ").append(fillColor.getBlue());
		
		return sb.toString();
	}

}
