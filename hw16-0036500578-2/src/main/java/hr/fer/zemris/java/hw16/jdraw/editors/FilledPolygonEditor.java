package hr.fer.zemris.java.hw16.jdraw.editors;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jdraw.colors.JColorArea;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.FilledCircle;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.FilledPolygon;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.Vector3;
import hr.fer.zemris.java.hw16.jdraw.tools.DrawFilledPolygonTool.VectorPair;

public class FilledPolygonEditor extends GeometricalObjectEditor{
	/**
	 * Center x coordinate input field
	 */
	private JTextField[] xField;
	
	/**
	 * Center y coordinate input field
	 */
	private JTextField[] yField;
	
	/**
	 * circle to update
	 */
	private FilledPolygon poly;
	List<Vector3> vectors;
	List<Vector3> newVectors = new ArrayList<>();
	/**
	 * Constructor
	 * @param fCircle - circle to update
	 */
	public FilledPolygonEditor(FilledPolygon poly) {
		this.poly = poly;
		vectors = poly.getPoints();
		xField = new JTextField[vectors.size()];
		yField = new JTextField[vectors.size()];
		for(int i = 0; i< vectors.size(); i++) {
			xField[i] = new JTextField(String.valueOf((int)vectors.get(i).getX()));
			yField[i] = new JTextField(String.valueOf((int)vectors.get(i).getY()));
		}
		
		setLayout(new GridLayout(vectors.size(), 2));
		
		for(int i = 0; i<vectors.size();i++) {
			add(xField[i]);
			add(yField[i]);
		}
	}
	
	@Override
	public void checkEditing() {
		for(int i = 0; i<vectors.size();i++) {
			int newX = Integer.parseInt(xField[i].getText());
			int newY = Integer.parseInt(yField[i].getText());
			newVectors.add(new Vector3(newX, newY, 0));
		}
		
		if(!isConvex()) {
			throw new IllegalArgumentException();
		}
		
	}

	@Override
	public void acceptEditing() {
		for(int i = 0; i<vectors.size();i++) {
			vectors.get(i).setX(newVectors.get(i).getX());
			vectors.get(i).setY(newVectors.get(i).getY());
		}
	}
	
	private boolean isConvex() {
		List<VectorPair> pairs = new ArrayList<>();
		for(int i = 0; i<vectors.size(); i++) {
			if(i == vectors.size()-1) {
				pairs.add(new VectorPair( vectors.get(0).sub(vectors.get(i)), vectors.get(1).sub(vectors.get(i))));
			}else if (i == vectors.size()-2){
				pairs.add(new VectorPair(vectors.get(i+1).sub(vectors.get(i)),  vectors.get(0).sub(vectors.get(i))));
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
}
