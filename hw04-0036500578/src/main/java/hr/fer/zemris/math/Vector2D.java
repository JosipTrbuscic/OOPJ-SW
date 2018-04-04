package hr.fer.zemris.math;

import java.util.Vector;

public class Vector2D {
	public static final double DELTA = 1E-6;
	private double x;
	private double y;

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void translate(Vector2D offset) {
		if (offset == null)
			throw new NullPointerException("Offset cannot be null");

		x += offset.x;
		y += offset.y;
	}

	public Vector2D translated(Vector2D offset) {
		Vector2D translated = copy();
		translated.translate(offset);

		return translated;
	}

	public void rotate(double angle) {
		double radians = angle * Math.PI / 180;

		double rotatedX = x * Math.cos(radians) - y * Math.sin(radians);
		double rotatedY = x * Math.sin(radians) + y * Math.cos(radians);
	
		x = rotatedX;
		y = rotatedY;
	}
	
	public Vector2D rotated(double angle) {
		Vector2D rotated = copy();
		rotated.rotate(angle);
		
		return rotated;
	}
	
	public void scale(double scale) {
		x*=scale;
		y*=scale;
	}
	
	public Vector2D scaled(double scale) {
		Vector2D scaled = copy();
		scaled.scale(scale);
		
		return scaled;
		
	}
	
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
	
	public double getAngle() {
		return Math.atan2(y, x);
	}
	
	@Override
	public String toString() {
		if(y<0) {
			return String.format("%.2fi - %.2fj", x,-y);
		} else {
			return String.format("%.2fi + %.2fj", x,y);
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(!(o instanceof Vector2D)) return false;
		
		Vector2D v = (Vector2D) o;
		
		if(Math.abs(v.x-this.x)>DELTA) return false;
		if(Math.abs(v.y-this.y)>DELTA) return false;
		
		return true;
	}
}
