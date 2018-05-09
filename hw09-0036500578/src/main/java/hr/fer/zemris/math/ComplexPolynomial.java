package hr.fer.zemris.math;

public class ComplexPolynomial {
	private Complex[] factors;

	public ComplexPolynomial(Complex... factors) {
		if (factors == null)
			throw new NullPointerException("factors cannot be null");
		if (factors.length == 0)
			throw new IllegalArgumentException("Must specify at least one factor");

		this.factors = factors;
	}

	public short order() {
		return (short) (factors.length - 1);
	}

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

	public ComplexPolynomial derive() {
		Complex[] derivedFactors = new Complex[factors.length - 1];
		
		for (int i = 0; i < factors.length - 1; i++) {
			derivedFactors[i] = new Complex(factors[i].getRe() * (factors.length - i -1), factors[i].getIm() * (factors.length - i -1));
		}

		return new ComplexPolynomial(derivedFactors);
	}

	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		
		for(int i = 0; i < factors.length; i++) {
			result = result.add(factors[i].multiply(z.power(factors.length - i - 1)));
		}
		
		return result;
	}
	
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
	
	public static void main(String[] args) {
		Complex c1 = new Complex(1,0);
		Complex c2 = new Complex(-1,0);
		Complex c3 = new Complex(0,1);
		Complex c4 = new Complex(0,-1);
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(c1,c2,c3,c4);
//		ComplexPolynomial cp = new ComplexPolynomial(c1,c2,c3,c4);
		System.out.println(crp.toComplexPolynom());
		System.out.println(crp.toComplexPolynom().derive());
//		for(Complex c : crp.toComplexPolynom().factors) {
//			System.out.println(c);
//		}

	}
}
