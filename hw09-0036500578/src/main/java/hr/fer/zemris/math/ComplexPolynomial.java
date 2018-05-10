package hr.fer.zemris.math;

/**
 * Class representing complex polynomial. Instances of this class are 
 * created by specifying arbitrary number of complex factors where each factor 
 * is a polynomial coefficient.
 * @author Josip Trbuscic
 *
 */
public class ComplexPolynomial {
	
	/**
	 * Coefficients
	 */
	private Complex[] factors;

	/**
	 * Constructor
	 * @param factors - polynomial factors
	 * @throws NullPointerException if given factors are null
	 * @throws IllegalArgumentException
	 */
	public ComplexPolynomial(Complex... factors) {
		if (factors == null)
			throw new NullPointerException("factors cannot be null");
		if (factors.length == 0)
			throw new IllegalArgumentException("Must specify at least one factor");

		this.factors = factors;
	}

	/**
	 * Returns degree of polynomial
	 * @return degree of polynomial
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	/**
	 * Multiplies this polynomial with given one and returns 
	 * the result as new polynomial
	 * @param p - other polynomial
	 * @return result of polynomial multiplication 
	 * @throws NullPointerException if given polynomial is null
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		if (p == null)
			throw new NullPointerException("Other complex polynomial cannot be null");

		Complex[] resultFactors = new Complex[order() + p.order()+1];
		for (int i = 0; i < resultFactors.length; i++) {
			resultFactors[i] = Complex.ZERO;
		}
		for (int i = 0; i < factors.length; i++) {
			for (int j = 0; j < p.factors.length; j++) {
				resultFactors[i + j] = resultFactors[i + j].add(factors[i].multiply(p.factors[j]));
			}
		}

		return new ComplexPolynomial(resultFactors);
	}

	/**
	 * Takes the derivative of this polynomial and returns it as new one 
	 * @return derivative of this polynomial
	 */
	public ComplexPolynomial derive() {
		Complex[] derivedFactors = new Complex[factors.length - 1];
		
		for (int i = 0; i < factors.length - 1; i++) {
			derivedFactors[i] = new Complex(factors[i].getRe() * (factors.length - i -1), factors[i].getIm() * (factors.length - i -1));
		}

		return new ComplexPolynomial(derivedFactors);
	}

	/**
	 * Returns value of this polynomial at given point
	 * @param z - point for which value of polynomial will be evaluated
	 * @return value of this polynomial at given point
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		
		for(int i = 0; i < factors.length; i++) {
			result = result.add(factors[i].multiply(z.power(factors.length - i - 1)));
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("f(z) = ");
		
		for(int i = 0; i < factors.length-1; i++) {
			Complex current = factors[i];
			if(i == factors.length-2) {
				sb.append("("+current+")z + ");
			} else {
				sb.append("("+current+")z^"+(factors.length-i-1)+" + ");
			}
		}
		
		sb.append("("+factors[factors.length-1]+")");
		
		return sb.toString();
	}
}
