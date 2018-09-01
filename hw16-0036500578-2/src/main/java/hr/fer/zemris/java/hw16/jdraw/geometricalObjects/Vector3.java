package hr.fer.zemris.java.hw16.jdraw.geometricalObjects;

import static java.lang.Math.sqrt;
import static java.lang.Math.abs;

/**
 * This class is representation of three dimensional vector. Class offers methods for unary operations like
 * calculating magnitude and normalizing vector and binary operations like addition, subtraction and 
 * dot and cross product Instances of this class are immutable.
 * 
 * @author Josip Trbuscic
 */
public class Vector3 {
	/**
	 * Comparison threshold
	 */
	private static final double THRESHOLD = 1E-6;
	/**
	 * x-component
	 */
	private double x;
	
	/**
	 * y-component
	 */
	private double y;
	
	/**
	 * z-component
	 */
	private double z;
	
	/**
	 * Constructs new vector from given components
	 * @param x - x component
	 * @param y - y component
	 * @param z - z component
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Returns magnitude of this vector
	 * @return magnitude of this vector
	 */
	public double norm() {
		return sqrt(x*x+y*y+z*z);
	}
	
	/**
	 * Returns new unit vector which has
	 * same direction as this one
	 * @return new normal vector
	 */
	public Vector3 normalized() {
		double norm = norm();
		
		if(Double.compare(norm, 0) == 0) {
			return new Vector3(0,0,0);
		}
		
		return new Vector3(x/norm, y/norm, z/norm);
	}
	
	/**
	 * Adds this vector to the given one 
	 * and returns the result as new vector
	 * @param other - other vector
	 * @return new vector which is result of
	 * 			addition operation
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(
				x + other.x,
				y + other.y,
				z + other.z);
	}
	 
	/**
	 * Subtracts given vector from this one 
	 * and returns the result as new vector
	 * @param other - other vector
	 * @return new vector which is result of
	 * 			subtraction operation
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(
				x - other.x,
				y - other.y,
				z - other.z);
	}
	
	/**
	 * Returns dot product of this 
	 * and given vector
	 * @param other - other vector
	 * @return dot product of vectors
	 */
	public double dot(Vector3 other) {
		return x * other.x +
				y * other.y +
				z * other.z;
	}
	
	/**
	 * Returns cross product of this and given 
	 * vector as new vector
	 * @param other - other vector
	 * @return cross product of two vectors
	 * 
	 */
	public Vector3 cross(Vector3 other) {
		 double newX = y * other.z - z * other.y;
		 double newY = z * other.x - x * other.z;
		 double newZ = x * other.y - y * other.x;
		 
		 return new Vector3(newX, newY, newZ);
	}
	
	/**
	 * Scales this vector with given factor
	 * and returns it as new vector
	 * @param factor - scaling factor
	 * @return new scaled vector
	 */
	public Vector3 scale(double factor) {
		return new Vector3(
				x * factor,
				y * factor,
				z * factor);
	}
	
	/**
	 * Returns cosine of angle between this
	 * and given vector. If any of the vectors 
	 * is null vector 0 will be returned
	 * @param other - other vector
	 * @return cosine of angle between this
	 * 			and given vector
	 */
	public double cosAngle(Vector3 other) {
		Vector3 nullVector = new Vector3(0, 0, 0);
		
		if(this.equals(nullVector) || other.equals(nullVector)) {
			return 0;
		}
		
		double dot = dot(other);
		return dot / (norm() * other.norm());
	}

	/**
	 * Returns x component of this vector
	 * @return x component of this vector
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns y component of this vector
	 * @return y component of this vector
	 */
	public double getY() {
		return y;
	}

	/**
	 * Returns z component of this vector
	 * @return z component of this vector
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * Returns array containing components
	 * of this vector
	 * @return array containing components
	 * 			of this vector
	 */
	public double[] toArray() {
		return new double[] {x, y, z};
	}
	
	/**
	 * Returns string which represents this vector
	 * @return string which represents this vector
	 */
	public String toString() {
		return String.format("(%.6f, %.6f, %.6f)", x,y,z);
	}

	/**
	 * Indicates whether some other object is equal to this one
	 * @param obj - other object
	 * @return {@code true} if other object is equal to this one,
	 * 			false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector3 other = (Vector3) obj;
		if (abs(x-other.x) > THRESHOLD)
			return false;
		if (abs(y-other.y) > THRESHOLD)
			return false;
		if (abs(z-other.z) > THRESHOLD)
			return false;
		return true;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	
}
