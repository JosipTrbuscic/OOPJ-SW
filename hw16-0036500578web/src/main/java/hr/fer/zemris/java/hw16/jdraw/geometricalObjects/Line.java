package hr.fer.zemris.java.hw16.jdraw.geometricalObjects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jdraw.editors.LineEditor;

/**
 * Extension of geometric object which represents a line object
 * @author Josip Trbuscic
 *
 */
public class Line extends GeometricalObject{
	
	/**
	 * Starting point coordinates
	 */
	private int x1;
	private int y1;
	
	/**
	 * Ending point coordinates
	 */
	private int x2;
	private int y2;
	
	/**
	 * Line color
	 */
	private Color color;
	
	/**
	 * Constructor
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param color
	 */
	public Line(int x1, int y1, int x2, int y2, Color color) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = color;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}
	
	/**
	 * Returns starting x
	 * @return starting x
	 */
	public int getX1() {
		return x1;
	}
	
	/**
	 * Sets starting x
	 * @param x1
	 */
	public void setX1(int x1) {
		this.x1 = x1;
		notifyListeners();
	}
	
	/**
	 * Returns starting y
	 * @return starting y
	 */
	public int getY1() {
		return y1;
	}
	
	/**
	 * Sets starting y
	 * @param y1
	 */
	public void setY1(int y1) {
		this.y1 = y1;
		notifyListeners();
	}
	
	/**
	 * Returns ending x
	 * @return ending x
	 */
	public int getX2() {
		return x2;
	}
	
	/**
	 * Sets ending x
	 * @param x2
	 */
	public void setX2(int x2) {
		this.x2 = x2;
		notifyListeners();
	}
	
	/**
	 * Returns ending y
	 * @return ending y
	 */
	public int getY2() {
		return y2;
	}
	
	/**
	 * Sets ending y
	 * @param y2
	 */
	public void setY2(int y2) {
		this.y2 = y2;
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
	 * Sets color
	 * @param color
 	 */
	public void setColor(Color color) {
		this.color = color;
		notifyListeners();
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Line (");
		sb.append(x1).append(",").append(y1).append(")-(").append(x2).append(",").append(y2).append(")");
		return sb.toString();
	}
	
	@Override
	public String getJvdString() {
		StringBuilder sb = new StringBuilder("LINE ");
		sb.append(x1).append(" ").append(y1).append(" ")
		.append(x2).append(" ").append(y2).append(" ")
		.append(color.getRed()).append(" ")
		.append(color.getGreen()).append(" ").append(color.getBlue());
		return sb.toString();
	}
	
	/**
	 * Notifies listeners about property change
	 */
	private void notifyListeners() {
		listeners.forEach(l->l.geometricalObjectChanged(this));
	}

}
