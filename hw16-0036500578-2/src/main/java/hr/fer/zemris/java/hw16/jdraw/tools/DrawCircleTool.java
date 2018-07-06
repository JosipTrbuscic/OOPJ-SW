package hr.fer.zemris.java.hw16.jdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jdraw.colors.IColorProvider;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.Circle;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jdraw.models.DrawingModel;

/**
 * Tool/State implementation responsible for tracking 
 * currently drawn circles
 * @author Josip Trbuscic
 *
 */
public class DrawCircleTool implements Tool{
	private int x;
	private int y;
	private double r;
	private boolean paint = true;
	
	private JDrawingCanvas canvas;
	private IColorProvider colorProvider;
	private DrawingModel model;
	
	/**
	 * Constructor
	 * @param colorProvider
	 * @param model
	 * @param canvas
	 */
	public DrawCircleTool(IColorProvider colorProvider, DrawingModel model,  JDrawingCanvas canvas) {
		this.colorProvider = colorProvider;
		this.model = model;
		this.canvas = canvas;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		paint = true;
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		r=Math.sqrt((x-e.getX())*(x-e.getX())+(y-e.getY())*(y-e.getY()));
		if(r == 0) return;
		Circle c = new Circle(x, y, r, colorProvider.getCurrentColor());
		c.addGeometricalObjectListener((GeometricalObjectListener)model);
		model.add(c);
		paint = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		r=Math.sqrt((x-e.getX())*(x-e.getX())+(y-e.getY())*(y-e.getY()));
		canvas.repaint();
	}

	@Override
	public void paint(Graphics2D g2d) {
		if(!paint) return;
		g2d.setColor(colorProvider.getCurrentColor());
		g2d.drawOval(x-(int)r, y - (int)r,2 * (int) Math.round(r),2 * (int) Math.round(r));
		
	}
}
