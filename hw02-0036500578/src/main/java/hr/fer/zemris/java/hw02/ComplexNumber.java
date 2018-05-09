package hr.fer.zemris.java.hw02;

import static java.lang.Math.abs;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;
import static java.lang.Math.atan2;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.PI;
/**
 * This class is representation of complex number. Class offers methods for basic arithmetic operations
 * and some more complex such as {@code power} and {@code root}. Instances of this class can be created 
 * from polar coordinates, as ordered pair or parsed from a string.
 * @author Josip Trbuscic
 */
public class ComplexNumber {
	public static double THRESHOLD = 1E-4;
	public static final ComplexNumber ZERO = new ComplexNumber(0,0);
	
	private double real;
	private double imaginary;
	/**
	 * Constructs a complex number from
	 * given real and imaginary part
	 * 
	 * @param real part of complex number
	 * @param imaginary part of complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Returns new complex number constructed
	 * only out of real part
	 * @param real part of complex number
	 * @return new complex number which imaginary part is 0
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	/**
	 * Returns new complex number constructed
	 * only out of imaginary part
	 * @param imaginary part of complex number
	 * @return	new complex number which real part is zero
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	/**
	 * Returns new complex number constructed
	 * with given polar coordinates
	 * @param magnitude - distance from the origin
	 * @param angle with the positive real axis
	 * @return new complex number
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		if(magnitude<0) throw new IllegalArgumentException("Magnitude cannot be negative. You entered "+magnitude);
		
		return new ComplexNumber(magnitude * cos(angle), magnitude * sin(angle));		
	}
	/**
	 * Returns a new Complex Number initialized to the value represented
	 *  by the specified String.
	 * @param s - String to be parsed
	 * @return {@code Complex Number} represented by the string
	 * @throws IllegalArgumentException if string does not represent valid comlex
	 */
	public static ComplexNumber parse(String s) {
		if(s.trim().matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?")) {
				return fromReal(Double.parseDouble(s));
				
		} else if(s.trim().matches("[\\d\\+\\.-]*[i$]")) {
			s=s.replaceAll("\\+", " +");
			s=s.replaceAll("-", " -");
			String[] nums = s.trim().split(" ");

			if(nums.length == 2) {
				double imag = replaceI(nums[1]);
				
				return new ComplexNumber(Double.parseDouble(nums[0]), imag);
			} else if(nums.length == 1) {
				double imag = replaceI(nums[0]);
						
				return fromImaginary(imag);
			}
		}
		
		throw new IllegalArgumentException("Invalid Complex Number");

	}
	
	private static double replaceI(String s) {
		s=s.replace("i", "");
		if(s.isEmpty() || s.equals("+")) return 1;
		if(s.equals("-")) return -1;
		
		return Double.parseDouble(s);
	}
	/**
	 * Adds two complex numbers and returns
	 * sum as new complex number
	 * @param c - second operand of sum
	 * @return 	new complex number that represents the sum
	 * 			of two complex numbers
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(real+c.real, imaginary+c.imaginary);
	}
	/**
	 * Subtracts two complex numbers and returns
	 * difference as new complex number
	 * @param c - Subtrahend
	 * @return 	new complex number that represents the difference
	 * 			of two complex numbers
	 */			
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(real-c.real, imaginary-c.imaginary);
		
	}
	/**
	 * Multiplies two complex numbers and returns
	 * product as new complex number
	 * @param c - second factor
	 * @return 	new complex number that represents the product
	 * 			of two complex numbers
	 */		
	public ComplexNumber mul(ComplexNumber c) {
        return new ComplexNumber(
        	real * c.real - imaginary * c.imaginary,
       		real * c.imaginary + imaginary * c.real);
		
	}
	/**
	 * Divides two complex numbers and returns
	 * quotient as new complex number
	 * @param c - divisor
	 * @return 	new complex number that represents the quotient
	 * 			of two complex numbers
	 * @throws IllegalArgumentException if divisor is 0
	 */		
	public ComplexNumber div(ComplexNumber c) {
		if(c.equals(ZERO)) throw new IllegalArgumentException("Divisor cannot be 0");
		
		ComplexNumber numerator = mul(new ComplexNumber(c.real, -c.imaginary));
		double denominator = pow(c.real,2) + pow(c.imaginary,2); 
		
		return new ComplexNumber(numerator.real/denominator, numerator.imaginary/denominator);
	}
	/**
	 * Raises complex number to the given power.
	 * Exponent cannot be negative
	 * @param n - exponent
	 * @return 	new complex number that represents complex number
	 * 			raised to the given power
	 * @throws IllegalArgumentException if exponent is negative
	 */	
	public ComplexNumber power(int n) {
		if(n<0) throw new IllegalArgumentException("Power cannot be negative. Was "+n);
		
		double magnitude = pow(getMagnitude(),n);
		double angle = n*getAngle();
		
		return fromMagnitudeAndAngle(magnitude, angle);
	}
	
	/**
	 * Finds n roots of complex number. Degree of root 
	 * is given as argument and must be positive
	 * @param n - root degree
	 * @return 	array  that contains n roots of a complex number
	 * @throws IllegalArgumentException if root is not positive.
	 */	
	public ComplexNumber[] root(int n) {
		if(n<=0) throw new IllegalArgumentException("Argument must be positive. Was "+n);
		ComplexNumber[] roots = new ComplexNumber[n];
		double magnitude = pow(getMagnitude(),1./n);

		for(int i = 0; i<n;++i) {
			double angle = (getAngle()+2*PI*i)/n;
			roots[i] = fromMagnitudeAndAngle(magnitude, angle);
		}
		
		return roots;
	}
	/**
	 * Returns String instance that represents complex number.
	 */
	@Override
	public String toString() {
		if (equals(ZERO)) {
			return "0";
		}
		else if(isZero(imaginary)){
			return String.valueOf(real);
				
		} else if(isZero(real)) {
			if(imaginary > 0 && isOne(imaginary)) return "i";
			else if(imaginary < 0 && isOne(imaginary)) return "-i";
			else return String.format("%.2fi", imaginary);
			
		} else {
			if(imaginary < 0) {
				if(isOne(imaginary)) return String.format("%.2f - i", real);
				
				return String.format("%.2f - %.2fi", real,-imaginary);
			} else {
				if(isOne(imaginary)) return String.format("%.2f + i", real);
				
				return String.format("%.2f + %.2fi",real,imaginary);
			}
		}
	}
	/**
	 * 
	 * @return real part of complex number
	 */
	public double getReal() {
		return real;
	}
	/**
	 * 
	 * @return imaginary part of complex number
	 */
	public double getImaginary() {
		return imaginary;
	}
	/**
	 * @return distance of ordered pair from the origin
	 */
	public double getMagnitude() {
		return sqrt(pow(real,2) + pow(imaginary,2));
	}
	/**
	 * Returns angle coordinate from polar form 
	 * of this complex number
	 * @return angle with positive axis
	 */
	public double getAngle() {
		double angle = atan2(getImaginary(),getReal());
		
		if(angle>=0) {
			return angle;
		} else {
			return angle+2*PI;
		}
	}
	/**
	 * Determines if specified argument is in range (-THRESHOLD,THRESHHOLD)
	 * @param n - number to be checked
	 * @return {@code true} if number is in range (-THRESHOLD,THRESHHOLD)
	 */
	private static boolean isZero(double n) {
		if(abs(n) < THRESHOLD) return true;
		return false;
	}
	/**
	 * Determines if specified argument is in range (-THRESHOLD-1,-1+THRESHOLD) or (1-THRESHOLD,1+THRESHOLD)
	 * @param n - number to be checked
	 * @return {@code true} if number is in range (-THRESHOLD-1,-1+THRESHOLD) or (1-THRESHOLD,1+THRESHOLD)
	 */
	private static boolean isOne(double n) {
		if(abs(abs(n)-1) < THRESHOLD) return true;
		return false;
	}
	
	/**
	 * Compares this complex number to the specified object. 
	 * The result is true if and only if the argument is not null
	 * and is a Complex Number object and their real parts and 
	 * imaginary parts are equal
	 * @return true if the given object represents a Complex number equivalent to this, false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		
		if(o == null) return false;
		if(!(o instanceof ComplexNumber)) return false;
		
		ComplexNumber c = (ComplexNumber) o;
		
		if(abs(this.real-c.real)>THRESHOLD) return false;
		if(abs(this.imaginary-c.imaginary)>THRESHOLD) return false;
		
		return true;
	}
	
}
