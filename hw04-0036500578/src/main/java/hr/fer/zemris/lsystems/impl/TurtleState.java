package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;
/**
 * Class representing state of turtle which draws L system
 * @author Josip Trbuscic
 *
 */
public class TurtleState {
	/**
	 * Vector of current position
	 */
	private Vector2D position;
	
	/**
	 * Vector of current direction
	 */
	private Vector2D direction;
	
	/**
	 * Current drawing color
	 */
	private Color color;
	
	/**
	 * Length of one unit
	 */
	private double unitLength;
	
	/**
	 * Creates new state from given arguments
	 * @param position 
	 * @param direction
	 * @param color
	 * @param unitLength - length of unit
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double unitLength) {
		if(position == null || direction == null || color == null)
				throw new NullPointerException("Invalid TurtleState arguments");
		
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.unitLength = unitLength;
	}
	
	/**
	 * Sets new current position
	 * @param position
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 * Sets new current direction
	 * @param direction
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * Sets new current color
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Sets new current unit length
	 * @param unitLength
	 */
	public void setUnitLength(double unitLength) {
		this.unitLength = unitLength;
	}

	/**
	 * @return current position
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * @return current direction
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * @return current color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return current unit length
	 */
	public double getUnitLength() {
		return unitLength;
	}

	/**
	 * @return new {@code TurtleState} with same parameters
	 */
	public TurtleState copy() {
		return new TurtleState( position.copy(),
								direction.copy(), 
								new Color(color.getRGB()),
								unitLength);
	}
	
}