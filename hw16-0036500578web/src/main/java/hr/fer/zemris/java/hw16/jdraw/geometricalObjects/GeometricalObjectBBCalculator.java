package hr.fer.zemris.java.hw16.jdraw.geometricalObjects;

import java.awt.Rectangle;
import java.util.List;

/**
 * Implementation of geometric object visitor which will calculates bounds of
 * bounding rectangle for specific object
 * @author Josip Trbuscic
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor{
	/**
	 * Values used to describe bounding rectangle of all 
	 * visited objects
	 */
	private int top = Integer.MAX_VALUE;
	private int bottom = 0;
	private int left =  Integer.MAX_VALUE;
	private int right = 0;
	
	@Override
	public void visit(Line line) {
		left = line.getX1() < left ? line.getX1() : left;
		left = line.getX2() < left ? line.getX2() : left;
		
		right = line.getX1() > right ? line.getX1() : right;
		right = line.getX2() > right ? line.getX2() : right;
		
		top = line.getY1() < top ? line.getY1() : top;
		top = line.getY2() < top ? line.getY2() : top;

		bottom = line.getY1() > bottom ? line.getY1() : bottom;
		bottom = line.getY2() > bottom ? line.getY2() : bottom;
	}

	@Override
	public void visit(Circle circle) {
		left = (circle.getX()-(int)circle.getR()) < left ? (circle.getX()-(int)circle.getR()) : left;
		right = (circle.getX()+(int)circle.getR()) > right ? (circle.getX()+(int)circle.getR()) : right;
		
		top = (circle.getY()-(int)circle.getR()) < top ? (circle.getY()-(int)circle.getR()) : top;
		bottom = (circle.getY()+(int)circle.getR()) > bottom ? (circle.getY()+(int)circle.getR()) : bottom;
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		left = (filledCircle.getX()-(int)filledCircle.getR()) < left ? (filledCircle.getX()-(int)filledCircle.getR()) : left;
		right = (filledCircle.getX()+(int)filledCircle.getR()) > right ? (filledCircle.getX()+(int)filledCircle.getR()) : right;
		
		top = (filledCircle.getY()-(int)filledCircle.getR()) < top ? (filledCircle.getY()-(int)filledCircle.getR()) : top;
		bottom = (filledCircle.getY()+(int)filledCircle.getR()) > bottom ? (filledCircle.getY()+(int)filledCircle.getR()) : bottom;
	}
	
	/**
	 * Returns bounding rectangle of all visited objects
	 * @return bounding rectangle of all visited objects
	 */
	public Rectangle getBoundingBox() {
		return new Rectangle(left, top, right -left, bottom -top);
	}

	@Override
	public void visit(FilledPolygon filledPolygon) {
		List<Vector3> vectors = filledPolygon.getPoints();
		
		for(Vector3 vector: vectors) {
			left = (int)vector.getX() < left ? (int)vector.getX() : left;
			
			right = (int) vector.getX() > right ?  (int)vector.getX() : right;
			
			top = (int)vector.getY() < top ? (int)vector.getY() : top;

			bottom = (int)vector.getY() > bottom ? (int)vector.getY() : bottom;
		}
		
	}

}
