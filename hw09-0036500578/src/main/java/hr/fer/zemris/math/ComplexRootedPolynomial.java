package hr.fer.zemris.math;

import static java.lang.Math.abs;

public class ComplexRootedPolynomial {

	private Complex[] roots;

	public ComplexRootedPolynomial(Complex... roots) {
		if (roots == null)
			throw new NullPointerException("Roots cannot be null");
		if(roots.length == 0)
			throw new IllegalArgumentException("Must specify at least one root");

		this.roots = roots;
	}

	public Complex apply(Complex z) {
		if (z == null)
			throw new NullPointerException("Complex number cannot be null");

		Complex result = Complex.ONE;

		for (int i = 0; i < roots.length; i++) {
			result = result.multiply(z.sub(roots[i]));
		}

		return result;
	}

	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial polynomial = new ComplexPolynomial(Complex.ONE, roots[0].negate());

		for(int i = 1; i < roots.length; i++) {
			polynomial = polynomial.multiply(new ComplexPolynomial(Complex.ONE, roots[i].negate()));
		}
		
		return polynomial;
	}

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

	public int indexOfClosestRootFor(Complex z, double threshold) {
		if(z == null) throw new NullPointerException("Complex number must not be null");
		if(threshold <= 0) throw new IllegalArgumentException("Threshold must be positive");
		
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
