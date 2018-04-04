package hr.fer.zemris.math;

/**
 * Representation of two dimensional vector with x and y components. Class
 * offers operations to translate, rotate and scale vectors.
 * 
 * @author Josip Trbuscic
 *
 */
public class Vector2D {
	
	/**
	 * Threshold used for comparing vectors
	 */
	public static final double DELTA = 1E-6;
	
	/**
	 * Abscissa coordinate
	 */
	private double x;
	
	/**
	 * Ordinate coordinate
	 */
	private double y;

	/**
	 * Constructs new {@code Vector2D} out of given coordinates
	 * @param x coordinate
	 * @param y coordinate
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @return x coordinate
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * @return y coordinate
	 */
	public double getY() {
		return y;
	}

	/**
	 * Translates point represented by x and y coordinate of this vector
	 * in a direction specified by given {@code Vector2D} 
	 * @param offset - distance and direction of translation
	 * @throws NullPointerException if given argument is null
	 */
	public void translate(Vector2D offset) {
		if (offset == null)
			throw new NullPointerException("Offset cannot be null");

		x += offset.x;
		y += offset.y;
	}


	/**
	 * Creates new {@code Vector2D} as a copy of a current one and
	 * translates it direction specified by given {@code Vector2D}.
	 * Original vector remains unchanged.
	 * @param offset - distance and direction of translation
	 * @return new translated vector
	 * @throws NullPointerException if given argument is null
	 */
	public Vector2D translated(Vector2D offset) {
		Vector2D translated = copy();
		translated.translate(offset);

		return translated;
	}

	/**
	 * Rotates the vector around the origin of a coordinate system 
	 * by given angle. Angle must be given as a value in degrees. 
	 * @param angle which specifies rotation
	 */
	public void rotate(double angle) {
		double radians = angle * Math.PI / 180;

		double rotatedX = x * Math.cos(radians) - y * Math.sin(radians);
		double rotatedY = x * Math.sin(radians) + y * Math.cos(radians);

		x = rotatedX;
		y = rotatedY;
	}

	/**
	 * Returns new instance of {@code Vector2D} rotated
	 * by given angle. Angle must be given as a value in degrees.
	 * Original vector remains unchanged.
	 * @param angle which specifies rotation
	 * @return new rotated vector
	 */
	public Vector2D rotated(double angle) {
		Vector2D rotated = copy();
		rotated.rotate(angle);

		return rotated;
	}

	/**
	 * Scales vector with given value
	 * @param scale - value to scale with 
	 */
	public void scale(double scale) {
		x *= scale;
		y *= scale;
	}

	/**
	 * Returns new instance of {@code Vector2D} scaled
	 * with given value. Original vector remains unchanged.
	 * @param scale - value to scale with
	 * @return new scaled vector
	 */
	public Vector2D scaled(double scale) {
		Vector2D scaled = copy();
		scaled.scale(scale);

		return scaled;

	}
	
	/**
	 *Returns new instance of {@code Vector2D} with same 
	 *parameters as current one 
	 * @return copy of a current vector
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}

	/**
	 * Returns angle which this vector encloses with 
	 * positive part of x axis
	 * @return angle with positive part of x axis
	 */
	public double getAngle() {
		return Math.atan2(y, x);
	}

	/**
	 * Returns {@code String} representation of vector
	 * @return {@code String} representation of a vector
	 */
	@Override
	public String toString() {
		if (y < 0) {
			return String.format("%.2fi - %.2fj", x, -y);
		} else {
			return String.format("%.2fi + %.2fj", x, y);
		}
	}

	/**
	 * Compares {@code Vector2D} with given object
	 * @return {@code true} if objects are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Vector2D))
			return false;

		Vector2D v = (Vector2D) o;

		if (Math.abs(v.x - this.x) > DELTA)
			return false;
		if (Math.abs(v.y - this.y) > DELTA)
			return false;

		return true;
	}
}
