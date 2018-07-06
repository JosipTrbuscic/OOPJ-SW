package hr.fer.zemris.java.hw16.jdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jdraw.colors.IColorProvider;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.FilledCircle;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jdraw.models.DrawingModel;

/**
 * Tool/State implementation responsible for tracking 
 * currently drawn filled circles
 * @author Josip Trbuscic
 *
 */
public class DrawFilledCircleTool implements Tool{
	private int x;
	private int y;
	private double r;
	private boolean paint = true;
	
	private JDrawingCanvas canvas;
	private IColorProvider bgColorProvider;
	private IColorProvider fgColorProvider;
	private DrawingModel model;
	
	/**
	 * Constructor
	 * @param colorProvider
	 * @param model
	 * @param canvas
	 */
	public DrawFilledCircleTool(IColorProvider fgColorProvider, IColorProvider bgColorProvider, DrawingModel model,  JDrawingCanvas canvas) {
		this.bgColorProvider = bgColorProvider;
		this.fgColorProvider = fgColorProvider;
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
		FilledCircle fc = new FilledCircle(x, y, r, fgColorProvider.getCurrentColor(), bgColorProvider.getCurrentColor());
		fc.addGeometricalObjectListener((GeometricalObjectListener) model);
		model.add(fc);
		paint = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		r=Math.sqrt((x-e.getX())*(x-e.getX())+(y-e.getY())*(y-e.getY()));
		canvas.repaint();
	}

	@Override
	public void paint(Graphics2D g2d) {
		if(!paint) return;
		g2d.setColor(bgColorProvider.getCurrentColor());
		g2d.fillOval(x-(int)r, y - (int)r,2 * (int) Math.round(r),2 * (int) Math.round(r));
		g2d.setColor(fgColorProvider.getCurrentColor());
		g2d.drawOval(x-(int)r, y - (int)r,2 * (int) Math.round(r),2 * (int) Math.round(r));
	}

}
