package hr.fer.zemris.java.hw16.jdraw.editors;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jdraw.colors.JColorArea;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.Line;

/**
 * Geometric object editor implementation which is used to 
 * update information about Line object
 * @author Josip Trbuscic
 *
 */
public class LineEditor extends GeometricalObjectEditor{
	private static final long serialVersionUID = 1L;
	
	/**
	 * New coordinates input fields
	 */
	private JTextField x1Field;
	private JTextField y1Field;
	private JTextField x2Field;
	private JTextField y2Field;
	
	/**
	 * Line color input field
	 */
	private JColorArea color;
	
	/**
	 * Line to update
	 */
	private Line line;
	
	/**
	 * New coordinates
	 */
	private int newX1;
	private int newY1;
	private int newX2;
	private int newY2;
	
	/**
	 * Constructor
	 * @param line - line to update
	 */
	public LineEditor(Line line) {
		this.line = line;
		x1Field = new JTextField(String.valueOf(line.getX1()));
		y1Field = new JTextField(String.valueOf(line.getY1()));
		x2Field = new JTextField(String.valueOf(line.getX2()));
		y2Field = new JTextField(String.valueOf(line.getY2()));
		color = new JColorArea(line.getColor());
		
		setLayout(new GridLayout(3, 3));
		add(new JLabel("Start"));
		add(x1Field);
		add(y1Field);
		add(new JLabel("End: "));
		add(x2Field);
		add(y2Field);
		add(new JLabel("Line color:"));
		add(color);
		add(new JLabel());
	}
	
	@Override
	public void checkEditing() {
		newX1 = Integer.parseInt(x1Field.getText());
		newY1 = Integer.parseInt(y1Field.getText());
		newX2 = Integer.parseInt(x2Field.getText());
		newY2 = Integer.parseInt(y2Field.getText());
	}

	@Override
	public void acceptEditing() {
		line.setX1(newX1);
		line.setX2(newX2);
		line.setY1(newY1);
		line.setY2(newY2);
		line.setColor(color.getCurrentColor());
	}

}
