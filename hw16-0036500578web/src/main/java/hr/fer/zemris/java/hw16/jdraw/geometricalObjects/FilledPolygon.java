package hr.fer.zemris.java.hw16.jdraw.geometricalObjects;

import java.awt.Color;
import java.util.List;

import hr.fer.zemris.java.hw16.jdraw.editors.FilledPolygonEditor;
import hr.fer.zemris.java.hw16.jdraw.editors.GeometricalObjectEditor;

public class FilledPolygon extends GeometricalObject{

	private List<Vector3> points;
	/**
	 * Outline color
	 */
	private Color outlineColor;
	
	/**
	 * Fill color
	 */
	private Color fillColor;
	
	
	public FilledPolygon(List<Vector3> points, Color outlineColor, Color fillColor) {
		super();
		this.points = points;
		this.outlineColor = outlineColor;
		this.fillColor = fillColor;
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledPolygonEditor(this);
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
		
	}

	@Override
	public String getJvdString() {
		StringBuilder sb = new StringBuilder("FPOLY ");
		sb.append(points.size()+" ");
		for(Vector3 vector : points) {
			sb.append((int)vector.getX()+" "+(int)vector.getY()+" ");
		}
		sb.append(outlineColor.getRed()).append(" ")
		.append(outlineColor.getGreen()).append(" ").append(outlineColor.getBlue())
		.append(" ").append(fillColor.getRed()).append(" ")
		.append(fillColor.getGreen()).append(" ").append(fillColor.getBlue());
		return sb.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Filled Polygon (");
		sb.append(points.size()).append(" )").append(", #")
				.append(Integer.toHexString(fillColor.getRGB()).substring(2).toUpperCase());
		return sb.toString();
	}

	public List<Vector3> getPoints() {
		return points;
	}

	public void setPoints(List<Vector3> points) {
		this.points = points;
		notifyListeners();
	}

	public Color getOutlineColor() {
		return outlineColor;
	}

	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
		notifyListeners();
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
		notifyListeners();
	}
	
	private void notifyListeners() {
		listeners.forEach(l->l.geometricalObjectChanged(this));
	}

	

}
