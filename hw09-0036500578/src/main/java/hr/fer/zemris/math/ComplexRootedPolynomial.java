package hr.fer.zemris.math;

import static java.lang.Math.abs;

/**
 * This class represents complex polynomial which is 
 * constructed from given roots 
 * @author Josip Trbuscic
 *
 */
public class ComplexRootedPolynomial {

	/**
	 * Roots of polynomial
	 */
	private Complex[] roots;

	/**
	 * Constructor 
	 * @param roots - roots of polynomial
	 * @throws NullPointerException if given roots are null
	 * @throws IllegalArgumentException
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		if (roots == null)
			throw new NullPointerException("Roots cannot be null");
		if(roots.length == 0)
			throw new IllegalArgumentException("Must specify at least one root");

		this.roots = roots;
	}

	/**
	 * Returns value of this polynomial at given point
	 * @param z - point for which value of polynomial will be evaluated
	 * @return value of this polynomial at given point
	 */
	public Complex apply(Complex z) {
		if (z == null)
			throw new NullPointerException("Complex number cannot be null");

		Complex result = Complex.ONE;

		for (int i = 0; i < roots.length; i++) {
			result = result.multiply(z.sub(roots[i]));
		}

		return result;
	}

	/**
	 * Calculates coefficients of complex polynomial and 
	 * converts this representation to ComplexPolynomial type
	 * @return new ComplexPolynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial polynomial = new ComplexPolynomial(Complex.ONE, roots[0].negate());

		for(int i = 1; i < roots.length; i++) {
			polynomial = polynomial.multiply(new ComplexPolynomial(Complex.ONE, roots[i].negate()));
		}
		
		return polynomial;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("f(z) = ");

		for (int i = 0; i < roots.length; ++i) {
			Complex current = roots[i];
			if (current.equals(Complex.ZERO)) {
				sb.append("z");
			}

			if (current.getRe() > 0) {
				sb.append("(z - " + current + ")");
			} else if (current.getRe() < 0) {
				sb.append("(z + " + current.multiply(Complex.ONE_NEG) + ")");
			} else {
				if (current.getIm() < 0) {
					sb.append("(z " + current + ")");
				} else {
					sb.append("(z + " + current + ")");
				}
			}
		}

		return sb.toString();
	}

	/**
	 *  Finds index of closest root for given complex number z that is within
	 *  threshold, if there is no such root, returns -1 
	 * @param z - complex number
	 * @param threshold - distance threshold
	 * @return index of closest root
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		if(z == null) throw new NullPointerException("Complex number must not be null");
		
		int index = 0;
		double minDistance = z.sub(roots[0]).module();
		
		for(int i = 1; i < roots.length; i++) {
			double currentDistance = z.sub(roots[i]).module();
			
			if(currentDistance < minDistance) {
				minDistance = currentDistance;
				index = i;
			}
		}
		
		return minDistance > threshold ? -1 : index;
	}

}
