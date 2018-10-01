package hr.fer.zemris.java.hw16.jdraw.geometricalObjects;

import java.awt.Graphics2D;
import java.util.List;

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

	@Override
	public void visit(FilledPolygon filledPolygon) {
		List<Vector3> vectors = filledPolygon.getPoints();
		
		g2d.setColor(filledPolygon.getFillColor());
		int[] xpoints = new int[vectors.size()];
		int[] ypoints = new int[vectors.size()];
		
		for(int i = 0; i<vectors.size(); i++) {
			xpoints[i] = (int)vectors.get(i).getX();
			ypoints[i] = (int)vectors.get(i).getY();
		}
		g2d.fillPolygon(xpoints, ypoints, vectors.size());
		
		g2d.setColor(filledPolygon.getOutlineColor());
		for(int i = 0; i<vectors.size(); i++) {
			if(i == vectors.size()-1) {
				int x1 = (int) vectors.get(0).getX();
				int y1 = (int) vectors.get(0).getY();
				int x2 =(int) vectors.get(i).getX();
				int y2 = (int) vectors.get(i).getY();
				g2d.drawLine(x1, y1, x2, y2);

			}else {
				int x1 = (int)vectors.get(i).getX();
				int y1 = (int)vectors.get(i).getY();
				int x2 = (int)vectors.get(i+1).getX();
				int y2 = (int)vectors.get(i+1).getY();

				g2d.drawLine(x1, y1, x2, y2);
			}
			
		}
	}

}
