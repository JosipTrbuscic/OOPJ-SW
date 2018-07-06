package hr.fer.zemris.java.hw16.jdraw.geometricalObjects;

import java.awt.Graphics2D;

/**
 * Implementation of geometric object visitor which paints all visited 
 * geometric objects
 * @author Josip Trbuscic
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor{
	/**
	 * Graphics
	 */
	private Graphics2D g2d;
	
	/**
	 * Constructor
	 * @param g2d - graphics used to paint components
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;
	}
	
	@Override
	public void visit(Line line) {
		g2d.setColor(line.getColor());
		g2d.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
	}

	@Override
	public void visit(Circle circle) {
		g2d.setColor(circle.getColor());
		int r = (int) Math.round(circle.getR());
		g2d.drawOval(circle.getX()-r, circle.getY()-r, 2*r, 2*r);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		int r = (int) Math.round(filledCircle.getR());
		g2d.setColor(filledCircle.getFillColor());
		g2d.fillOval(filledCircle.getX()-r, filledCircle.getY()-r, 2*r, 2*r);
		
		g2d.setColor(filledCircle.getOutlineColor());
		g2d.drawOval(filledCircle.getX()-r, filledCircle.getY()-r, 2*r, 2*r);
	}

}
