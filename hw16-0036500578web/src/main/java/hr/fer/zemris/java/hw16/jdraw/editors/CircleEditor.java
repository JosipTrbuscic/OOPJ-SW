package hr.fer.zemris.java.hw16.jdraw.editors;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jdraw.colors.JColorArea;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.Circle;

/**
 * Geometric object editor implementation which is used to 
 * update information about Circle object
 * @author Josip Trbuscic
 *
 */
public class CircleEditor extends GeometricalObjectEditor{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Center x coordinate input field
	 */
	private JTextField xField;
	
	/**
	 * Center y coordinate input field
	 */
	private JTextField yField;
	
	/**
	 * Radius input field
	 */
	private JTextField rField;
	
	/**
	 * Color input
	 */
	private JColorArea colorField;
	
	/**
	 * circle to update
	 */
	private Circle circle;
	
	/**
	 * New center x coordinate
	 */
	private int newX;
	
	/**
	 * New center y coordinate
	 */
	private int newY;
	
	/**
	 * New radius
	 */
	private int newR;
	
	/**
	 * Constructor
	 * @param circle - circle to update
	 */
	public CircleEditor(Circle circle) {
		this.circle = circle;
		setLayout(new GridLayout(3, 3));
		xField = new JTextField(String.valueOf(circle.getX()));
		yField = new JTextField(String.valueOf(circle.getY()));
		rField = new JTextField(String.valueOf((int)circle.getR()));
		colorField = new JColorArea(circle.getColor());
		
		add(new JLabel("Center"));
		add(xField);
		add(yField);
		
		add(new JLabel("Radius: "));
		add(rField);
		add(new JLabel());
		add(new JLabel("Outline ccolor:"));
		add(colorField);
		add(new JLabel());
		
	}
	
	@Override
	public void checkEditing() {
		newX = Integer.parseInt(xField.getText());
		newY = Integer.parseInt(yField.getText());
		newR = Integer.parseInt(rField.getText());
		if(newR<=0) throw new IllegalArgumentException("Radius must be positive");
	}

	@Override
	public void acceptEditing() {
		circle.setX(newX);
		circle.setY(newY);
		circle.setR(newR);
		circle.setColor(colorField.getCurrentColor());
	}

}
