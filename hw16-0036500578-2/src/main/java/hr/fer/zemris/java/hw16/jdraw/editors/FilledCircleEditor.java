package hr.fer.zemris.java.hw16.jdraw.editors;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jdraw.colors.JColorArea;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.FilledCircle;

/**
 * Geometric object editor implementation which is used to 
 * update information about FilledCircle object
 * @author Josip Trbuscic
 *
 */
public class FilledCircleEditor extends GeometricalObjectEditor{
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
	 * Fill color input filed
	 */
	private JColorArea fillColorField;
	
	/**
	 * Outline color input filed
	 */
	private JColorArea outColorField;
	
	/**
	 * circle to update
	 */
	private FilledCircle fCircle;
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
	 * @param fCircle - circle to update
	 */
	public FilledCircleEditor(FilledCircle fCircle) {
		this.fCircle = fCircle;
		xField = new JTextField(String.valueOf(fCircle.getX()));
		yField = new JTextField(String.valueOf(fCircle.getY()));
		rField = new JTextField(String.valueOf((int)fCircle.getR()));
		outColorField = new JColorArea(fCircle.getOutlineColor());
		fillColorField = new JColorArea(fCircle.getFillColor());
		
		setLayout(new GridLayout(4, 3));
		add(new JLabel("Center"));
		add(xField);
		add(yField);
		add(new JLabel("Radius: "));
		add(rField);
		add(new JLabel());
		add(new JLabel("Outline color:"));
		add(outColorField);
		add(new JLabel());
		add(new JLabel("Fill color:"));
		add(fillColorField);
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
		fCircle.setX(newX);
		fCircle.setY(newY);
		fCircle.setR(newR);
		fCircle.setOutlineColor(outColorField.getCurrentColor());
		fCircle.setFillColor(fillColorField.getCurrentColor());
	}
	
}
