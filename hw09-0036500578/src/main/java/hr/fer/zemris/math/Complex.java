package hr.fer.zemris.math;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.abs;

/**
 * This class is a representation of immutable complex number. It provides methods for basic
 * arithmetic operations and some more complex such as {@code power} and {@code root}. Instances 
 * of this class are created by providing real and imaginary part of a complex number.
 * @author Josip Trbuscic
 *
 */
public class Complex {
	/**
	 * Comparison threshold
	 */
	private static final double THRESHOLD = 1E-9;
	
	/**
	 * Complex number whose real and
	 * imaginary part are 0 
	 */
	public static final Complex ZERO = new Complex(0, 0);
	
	/**
	 * Complex number whose real part is 1 and
	 * imaginary part is 0
	 */
	public static final Complex ONE = new Complex(1, 0);
	
	/**
	 * Complex number whose real part is -1 and
	 * imaginary part is 0 
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	
	/**
	 * Complex number whose real part is 0 and
	 * imaginary part is 1
	 */
	public static final Complex IM = new Complex(0, 1);
	
	/**
	 * Complex number whose real part is 0 and
	 * imaginary part is -1
	 */
	public static final Complex IM_NEG = new Complex(0, -1);
	
	/**
	 * Real part of complex number
	 */
	private double re;
	
	/**
	 * imaginary part of complex number
	 */
	private double im;
	
	/**
	 * Constructs new complex number whose real and
	 * imaginary part are 0 
	 */
	public Complex() {
		this(0,0);
	}
	
	/**
	 * Constructs a complex number from
	 * given real and imaginary part
	 * 
	 * @param re - real part of complex number
	 * @param im - imaginary part of complex number
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Returns module of this complex number
	 * @return module of this complex number
	 */
	public double module() {
		return sqrt(re*re+im*im);
	}
	
	/**
	 * Multiplies this complex number with given one 
	 * and returns the product as new complex number
	 * @param c - multiplication factor
	 * @return - result of multiplication as new 
	 * 			complex number
	 */
	public Complex multiply(Complex c) {
		return new Complex(
				re * c.re - im * c.im,
				re * c.im + im * c.re);
	}
	
	/**
	 * Divides this complex number with given one 
	 * and returns the quotient as new complex number.
	 * @param c - divisor
	 * @return quotient as new complex number
	 * @throws IllegalArgumentException if division by zero 
	 * 			is tried
	 */
	public Complex divide(Complex c) {
		if(c.equals(ZERO)) throw new IllegalArgumentException("Divisor cannot be 0");
		
		double denominator = pow(c.module(),2);
		double first = re * c.re + im * c.im;
		double second = im * c.re - re * c.im;
		
		return new Complex(first/denominator, second/denominator);
	}

	/**
	 * Adds this complex number to the given one 
	 * and returns the sum as new vector
	 * @param c - other operand
	 * @return new complex number which is result of
	 * 			addition operation
	 */

	public Complex add(Complex c) {
		return new Complex(re + c.re, im + c.im);
	}
	
	/**
	 * Subtracts given complex number from this one
	 * and returns the difference as new complex number
	 * @param c - subtrahend
	 * @return new complex number which is result of
	 * 			subtraction operation
	 */
	public Complex sub(Complex c) {
		return new Complex(re - c.re, im - c.im);
	}
	
	/**
	 * Returns the additive inverse of this complex number
	 * @return inverse of this complex number
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}
	
	/**
	 * Raises complex number to the given power and 
	 * returns it as new complex number
	 * Exponent cannot be negative
	 * @param n - exponent
	 * @return 	new complex number that represents complex number
	 * 			raised to the given power
	 * @throws IllegalArgumentException if exponent is negative
	 */	
	public Complex power(int n) {
		if(n<0) throw new IllegalArgumentException("Power cannot be negative. Was "+n);
		
		double magnitude = pow(module(),n);
		double angle = n*getAngle();
		
		return new Complex(
				magnitude * cos(angle),
				magnitude * sin(angle));
	}
	
	/**
	 * Finds n roots of complex number. Degree of root 
	 * is given as argument and must be positive
	 * @param n - root degree
	 * @return 	list  that contains n roots of a complex number
	 * @throws IllegalArgumentException if root is not positive.
	 */	
	public List<Complex> roots(int n) {
		if(n<=0) throw new IllegalArgumentException("Argument must be positive. Was "+n);
		List<Complex> roots = new ArrayList<>();
		double magnitude = pow(module(),1./n);

		for(int i = 0; i<n;++i) {
			double angle = (getAngle()+2*PI*i)/n;
			roots.add(new Complex(
						magnitude * cos(angle),
						magnitude * sin(angle))
					);
		}
		
		return roots;
	}
	
	/**
	 * Returns string representation of this 
	 * complex number
	 * @return string that represents this complex number
	 */
	@Override
	public String toString() {
		if (equals(ZERO)) {
			return "0";
		}
		
		if(im == 0){
			return String.valueOf(re);
				
		} else if(re == 0) {
			return String.format("%.2fi", im);
			
		} else {
			if(im < 0) {
				return String.format("%.2f - %.2fi", re,-im);
				
			} else {
				return String.format("%.2f + %.2fi", re, im);
			}
		}
	}
	
	/**
	 * Returns angle coordinate of polar form 
	 * of this complex number
	 * @return angle with positive axis
	 */
	private double getAngle() {
		double angle = atan2(im, re);
		
		if(angle>=0) {
			return angle;
		} else {
			return angle+2*PI;
		}
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
		Complex other = (Complex) obj;
		if (abs(re-other.re) > THRESHOLD)
			return false;
		if (abs(im-other.im) > THRESHOLD)
			return false;
		return true;
	}

	/**
	 * Returns real part of this complex number
	 * @return real part of this complex number
	 */
	public double getRe() {
		return re;
	}

	/**
	 * Returns imaginary part of this complex number
	 * @return imaginary part of this complex number
	 */
	public double getIm() {
		return im;
	}

}
