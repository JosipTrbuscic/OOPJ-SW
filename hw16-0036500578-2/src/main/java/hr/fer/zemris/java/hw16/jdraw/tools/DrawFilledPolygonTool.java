package hr.fer.zemris.java.hw16.jdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jdraw.colors.IColorProvider;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.FilledPolygon;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.Vector3;
import hr.fer.zemris.java.hw16.jdraw.models.DrawingModel;

public class DrawFilledPolygonTool implements Tool{
	private int x;
	private int y;
	private List<Vector3> vectors = new ArrayList<>();
	
	private IColorProvider bgColorProvider;
	private IColorProvider fgColorProvider;
	private DrawingModel model;
	private JDrawingCanvas canvas;
	
	/**
	 * Constructor
	 * @param colorProvider
	 * @param model
	 * @param canvas
	 */
	public DrawFilledPolygonTool(IColorProvider fgColorProvider, IColorProvider bgColorProvider, DrawingModel model, JDrawingCanvas canvas) {
		this.model = model;
		this.canvas = canvas;
		this.bgColorProvider = bgColorProvider;
		this.fgColorProvider = fgColorProvider;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Vector3 current = new Vector3(e.getX(), e.getY(), 0);
		if(e.getButton() ==  MouseEvent.BUTTON1) {
			if(vectors.isEmpty()) {
				vectors.add(current);
				return;
			}
			if(vectors.size()<3 && isClose(current, vectors.get(vectors.size()-1))) {
				return;
			}
			if(vectors.size() <3 && !isClose(current, vectors.get(vectors.size()-1))) {
				vectors.add(current);
				return;
			}
			
			if(vectors.size() == 3 && !isClose(current, vectors.get(vectors.size()-1))) {
				if(!isConvex(current)) {
					JOptionPane.showMessageDialog(canvas, "Not convex");
					return;
				}else {
					vectors.add(current);
					return;
				}
				
			}
			if(vectors.size() == 3 && isClose(current, vectors.get(vectors.size()-1))) {
				FilledPolygon polygon  = new FilledPolygon(new ArrayList<>(vectors), fgColorProvider.getCurrentColor(), bgColorProvider.getCurrentColor());
				model.add(polygon);
				polygon.addGeometricalObjectListener((GeometricalObjectListener) model);
				vectors.clear();
				return;
			}
			
			if(vectors.size() >= 4 && isClose(current, vectors.get(vectors.size()-1))) {
					FilledPolygon polygon  = new FilledPolygon(new ArrayList<>(vectors), fgColorProvider.getCurrentColor(), bgColorProvider.getCurrentColor());
					model.add(polygon);
					polygon.addGeometricalObjectListener((GeometricalObjectListener) model);
					vectors.clear();
					return;
			} else {
				if(isConvex(current)) {
					vectors.add(current);
					return;
				}
				JOptionPane.showMessageDialog(canvas, "Not convex");
			}
			
		}else {
			vectors.clear();
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		canvas.repaint();
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(fgColorProvider.getCurrentColor());
		for(int i = 0; i<vectors.size(); i++) {
			if(i == vectors.size()-1) {
				int x2 =(int) vectors.get(i).getX();
				int y2 = (int) vectors.get(i).getY();
				g2d.drawLine(x, y, x2, y2);
				x2 =(int) vectors.get(0).getX();
				y2 = (int) vectors.get(0).getY();
				g2d.drawLine(x, y, x2, y2);

				return;
			}
			int x1 = (int)vectors.get(i).getX();
			int y1 = (int)vectors.get(i).getY();
			int x2 = (int)vectors.get(i+1).getX();
			int y2 = (int)vectors.get(i+1).getY();

			g2d.drawLine(x1, y1, x2, y2);
		}
		
	}
	
	private boolean isClose(Vector3 current, Vector3 last) {
		double distance = Math.sqrt((current.getX()-last.getX())*(current.getX()-last.getX())
				+(current.getY()-last.getY())*(current.getY()-last.getY()));
		
		return distance <= 3;
	}
	
	private boolean isConvex(Vector3 current) {
		List<VectorPair> pairs = new ArrayList<>();
		for(int i = 0; i<vectors.size(); i++) {
			if(i == vectors.size()-1) {
				pairs.add(new VectorPair(current.sub(vectors.get(i)), vectors.get(0).sub(vectors.get(i))));
			}else if (i == vectors.size()-2){
				pairs.add(new VectorPair(vectors.get(i+1).sub(vectors.get(i)), current.sub(vectors.get(i))));
			}else {
				pairs.add(new VectorPair(vectors.get(i+1).sub(vectors.get(i)), vectors.get(i+2).sub(vectors.get(i))));
			}
		}
		boolean convex = true;
		boolean first = true;
		double firstZ = pairs.get(0).getProduct().getZ();
		for(VectorPair pair : pairs) {
			if(first) {
				first = false;
				continue;
			}
			if(pair.getProduct().getZ()*firstZ < 0) {
				convex = false;
				return convex;
			}
		}
		return convex;
	}
	
	public static class VectorPair{
		Vector3 first;
		Vector3 second;
		
		public VectorPair(Vector3 first, Vector3 second) {
			super();
			this.first = first;
			this.second = second;
		}
		
		public Vector3 getProduct() {
			return first.cross(second);
		}
		
	}

}
