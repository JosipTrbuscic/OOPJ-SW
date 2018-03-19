package hr.fer.zemris.java.hw02;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;
import static java.lang.Math.atan2;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.PI;


public class ComplexNumber {
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
		String[] parts = s.split("-?\\d+");
		double real = Double.parseDouble(parts[0]);
		double imaginary = Double.parseDouble(parts[1]);
		return new ComplexNumber(real, imaginary);
		
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
		
		return null;
	}
	
	public ComplexNumber power(int n) {
		
		return null;
	}
	
	public ComplexNumber root(int n) {
		
		return null;
	}
	
	public String toString() {
		if (imaginary == 0) return real + "";
        if (real == 0) return imaginary + "i";
        if (imaginary <  0) return real + " - " + (-imaginary) + "i";
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
		return atan2(getImaginary(),getReal());
	}
	
	public static void main(String[] args) {
		String c1 = "2.1+3.1i";
		String c2 = "2.1-3.1i";
		String c3 = "-2.1+3.1i";
		String c4 = "-2.1-3.1i";
		
		System.out.println(ComplexNumber.parse(c1).toString());
		System.out.println(ComplexNumber.parse(c2).toString());
		System.out.println(ComplexNumber.parse(c3).toString());
		System.out.println(ComplexNumber.parse(c4).toString());
	}
	
}
