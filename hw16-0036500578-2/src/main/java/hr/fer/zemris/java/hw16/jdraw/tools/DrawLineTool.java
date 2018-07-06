package hr.fer.zemris.java.hw16.jdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jdraw.colors.IColorProvider;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.Line;
import hr.fer.zemris.java.hw16.jdraw.models.DrawingModel;

/**
 * Tool/State implementation responsible for tracking 
 * currently drawn line
 * @author Josip Trbuscic
 *
 */
public class DrawLineTool implements Tool{
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private boolean paint = true;
	
	private IColorProvider colorProvider;
	private DrawingModel model;
	private JDrawingCanvas canvas;
	
	/**
	 * Constructor
	 * @param colorProvider
	 * @param model
	 * @param canvas
	 */
	public DrawLineTool(IColorProvider colorProvider, DrawingModel model, JDrawingCanvas canvas) {
		this.colorProvider = colorProvider;
		this.model = model;
		this.canvas = canvas;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		paint = true;
		x1 = e.getX();
		y1 = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		x2 = e.getX();
		y2 = e.getY();
		if(x1 == x2 && y1 == y2) return;
		Line l = new Line(x1, y1, x2, y2, colorProvider.getCurrentColor());
		l.addGeometricalObjectListener((GeometricalObjectListener) model);
		model.add(l);
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
		x2 = e.getX();
		y2 = e.getY();
		canvas.repaint();
	}

	@Override
	public void paint(Graphics2D g2d) {
		if(!paint) return;
		g2d.setColor(colorProvider.getCurrentColor());
		g2d.drawLine(x1, y1, x2, y2);
	}
}
