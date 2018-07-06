package hr.fer.zemris.java.hw16.jdraw;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jdraw.models.DrawingModel;
import hr.fer.zemris.java.hw16.jdraw.models.DrawingModelListener;

/**
 * JComponent implementation which serves as canvas for 
 * geometric objects which will be drawn
 * @author Josip Trbuscic
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener{
	
	/**
	 * Drawing model which contains 
	 */
	private DrawingModel model;
	
	/**
	 * Constructor
	 * @param model - drawing model
	 */
	public JDrawingCanvas(DrawingModel model) {
		this.model = model;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		GeometricalObjectPainter painter = new GeometricalObjectPainter((Graphics2D) g);
		
		for(int i=model.getSize()-1; i>=0; i--) {
			model.getObject(i).accept(painter);
		}
		JVDraw.getCurrentState().paint((Graphics2D) g);
	}
	
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
		
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

}
