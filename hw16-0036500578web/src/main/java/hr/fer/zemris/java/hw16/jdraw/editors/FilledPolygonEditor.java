package hr.fer.zemris.java.hw16.jdraw.editors;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.FilledPolygon;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.GeometricalObject;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.Vector3;

public class FilledPolygonEditor extends GeometricalObjectEditor{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		List<Vector3> obj = Collections.synchronizedList(vectors);
		if(!isConvex()) {
			newVectors.clear();
			throw new IllegalArgumentException("Not Convex");
		}
		
	}

	@Override
	public void acceptEditing() {
		poly.setPoints(newVectors);
	}
	
	private boolean isConvex() {
		System.out.println("Convex check ");
		List<VectorPair> pairs = new ArrayList<>();
		for(int i = 0; i<newVectors.size(); i++) {
			if(i == newVectors.size()-1) {
				pairs.add(new VectorPair( newVectors.get(0).sub(newVectors.get(i)), newVectors.get(1).sub(newVectors.get(i))));
			}else if (i == vectors.size()-2){
				pairs.add(new VectorPair(newVectors.get(i+1).sub(newVectors.get(i)),  newVectors.get(0).sub(newVectors.get(i))));
			}else {
				pairs.add(new VectorPair(newVectors.get(i+1).sub(newVectors.get(i)), newVectors.get(i+2).sub(newVectors.get(i))));
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
