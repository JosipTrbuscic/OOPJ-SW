package hr.fer.zemris.java.hw02;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.pow;
import static java.lang.Math.atan2;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.PI;


public class ComplexNumber {
	public static double THRESHOLD = 1E-4;
	public static final ComplexNumber ZERO = new ComplexNumber(0,0);
	
	private double real;
	private double imaginary;
	
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	 
	public static ComplexNumber fromImaginray(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		if(angle >= 2*PI) {
			angle -= 2*PI;
		}
		return new ComplexNumber(magnitude * cos(angle), magnitude * sin(angle));		
	}
	
	public static ComplexNumber parse(String s) {
		String numberNoWhiteSpace = s.replaceAll("\\s","");

	    Pattern patternA = Pattern.compile("([-]?\\d+\\.?\\d?)([-|+]+\\d+\\.?\\d?)[i$]+");

	    Pattern patternB = Pattern.compile("[-]?(\\d*\\.?\\d+)$");
	 
	    Pattern patternC = Pattern.compile("[-]?(\\d+\\.?\\d+)*[i$]");
	    
	    Matcher matcherA = patternA.matcher(numberNoWhiteSpace);
	    Matcher matcherB = patternB.matcher(numberNoWhiteSpace);
	    Matcher matcherC = patternC.matcher(numberNoWhiteSpace);

	    if (matcherA.find()) {
	  	return  new ComplexNumber(Double.parseDouble(matcherA.group(1)), 
	  			Double.parseDouble(matcherA.group(2)));
	    } else if (matcherB.find()) {
	    	return  new ComplexNumber(Double.parseDouble(matcherB.group()), 0);
	    } else if (matcherC.find()) {
	    	if(matcherC.group().equals("i")){
	    		return new ComplexNumber(0, 1);
	    	}
	    	
	    	return new ComplexNumber(0, Double.parseDouble(matcherC.group().replaceAll("i", ""))) ;
	    } else {
	    	throw new IllegalArgumentException("Invalid complex number");
	    }
		
	}
	
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(real+c.real, imaginary+c.imaginary);
	}
	
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(real-c.real, imaginary-c.imaginary);
		
	}
	
	public ComplexNumber mul(ComplexNumber c) {
        return new ComplexNumber(
        	real * c.real - imaginary * c.imaginary,
       		real * c.real + imaginary * c.imaginary);
		
	}
	
	public ComplexNumber div(ComplexNumber c) {
		if(c.equals(ZERO)) throw new IllegalArgumentException("Denominator cannot be 0");
		
		ComplexNumber numerator = mul(c.conjugate());
		double denominator = pow(c.real,2) + pow(c.imaginary,2); 
		
		return new ComplexNumber(numerator.real/denominator, numerator.imaginary/denominator);
	}
	
	private ComplexNumber conjugate() {
		return new ComplexNumber(real, -imaginary);
	}
	
	public ComplexNumber power(int n) {
		double magnitude = pow(getMagnitude(),n);
		double angle = n*getAngle();
		return fromMagnitudeAndAngle(magnitude, angle);
	}
	
	public ComplexNumber[] root(int n) {
		ComplexNumber[] roots = new ComplexNumber[n];
		double magnitude = pow(getMagnitude(),1/n);

		for(int i = 0; i<n;++i) {
			double angle = (getAngle()+2*PI*i)/n;
			roots[i] = fromMagnitudeAndAngle(magnitude, angle);
		}
		
		return roots;
	}
	
	public String toString() {
		if (imaginary == 0) return real + "";
        if (real == 0) {
            if(imaginary == 1) return "i";
            if(imaginary == -1) return "-i";
        	return imaginary + "i";
        }
        if (imaginary <  0) return real + " - " + (-imaginary) + "i";
        if(real != 0 && imaginary == 1) return real + "i";
        if(real != 0 && imaginary == -1) return real + "-i";
        
        return real + " + " + imaginary + "i";
	}
	
	public double getReal() {
		return real;
	}
	
	public double getImaginary() {
		return imaginary;
	}
	
	public double getMagnitude() {
		return sqrt(pow(real,2) + pow(imaginary,2));
	}
	
	public double getAngle() {
		double angle = atan2(getImaginary(),getReal());
		if(angle>=0) {
			return angle;
		} else {
			return angle+2*PI;
		}
	}
	
	public static void main(String[] args) {
		String c1 = "+34.51";
		String c2 = "-3.12";
		String c3 = "-2.71i";
		String c4 = "+2.7-3.1i";
		String c5 = "+2.7-3.1i";
		String c6 = "i";
		System.out.println(ComplexNumber.parse(c1).toString());
		System.out.println(ComplexNumber.parse(c2).toString());
		System.out.println(ComplexNumber.parse(c3).toString());
		System.out.println(ComplexNumber.parse(c4).toString());
		System.out.println(ComplexNumber.parse(c5).toString());
		System.out.println(ComplexNumber.parse(c6).toString());
		System.out.println(c4.equals(c5));


	}
	
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
