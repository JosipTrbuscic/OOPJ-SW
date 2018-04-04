package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

public class TurtleState {
	private Vector2D position;
	private Vector2D direction;
	private Color color;
	private double unitLength;
	
	public TurtleState(Vector2D position, Vector2D direction, Color color, double unitLength) {
		
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.unitLength = unitLength;
	}
	
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setUnitLength(double unitLength) {
		this.unitLength = unitLength;
	}

	public Vector2D getPosition() {
		return position;
	}

	public Vector2D getDirection() {
		return direction;
	}

	public Color getColor() {
		return color;
	}

	public double getUnitLength() {
		return unitLength;
	}

	public TurtleState copy() {
		return new TurtleState( position.copy(),
								direction.copy(), 
								new Color(color.getRGB()),
								unitLength);
	}
	
}