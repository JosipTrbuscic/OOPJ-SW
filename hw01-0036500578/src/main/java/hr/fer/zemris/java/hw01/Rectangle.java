package hr.fer.zemris.java.hw01;

import java.lang.Runtime;
import java.util.Scanner;

public class Rectangle {

	/**
	 * Metoda koja računa površinu pravokutnika
	 * @param x jedna stranica pravokutnika
	 * @param y druga stranica pravokutnika
	 * @return vraća iznos izraza x*y
	 */
	public static double getArea(double x, double y) {
		if(x <= 0 || y <= 0) {
			throw new IllegalArgumentException("Neispravan argument");
		}
		return x * y;
	}

	/**
	 * Metoda koja računa opseg pravokutnika
	 * @param x jedna stranica pravokutnika
	 * @param y druga stranica pravokutnika
	 * @return vraća iznos izraza 2*(x+y)
	 */
	public static double getCircumference(double x, double y) {
		if(x <= 0 || y <= 0) {
			throw new IllegalArgumentException("Neispravan argument");
		}
		return 2 * (x + y);
	}
	
	/**
	 * Metoda za formatirani ispis površine i opsega
	 * @param width širina pravokukutnika
	 * @param height visina pravokutnika
	 * @param area površina pravokutnika
	 * @param circumference	opseg pravokutnika
	 */
	public static void print(double width, double height, double area, double circumference) {
		System.out.format("Pravokutnik sirine %.1f i visine %.1f ima povrsinu %.1f te opseg %.1f", width, height, area,
				circumference);
	}
	
	/**
	 * Metoda za unos stranice pravokutnika i njenu provjeru
	 * @param sc čitač znakovnog niza sa ulaznog toka
	 * @return vraća pročitanu vrijednost ako je ona nenegativan realan broj, 
	 * 			inače vraća -1
	 */
	public static double checkEntry(Scanner sc) {
		String next = null;

		if (sc.hasNext()) {
			next = sc.next();
		}

		try {
			Double value = Double.parseDouble(next);

			if (value < 0) {
				System.out.println("Unijeli ste negativnu vrijednost");
			} else {
				return value;
			}

		} catch (NumberFormatException ex) {
			System.out.println("'" + next + "'" + " se ne može protumačiti kao broj.");
		}
		return -1;

	}
	
	/**
	 * Metoda koja se poziva prilikom pokretanja
	 * programa.
	 * @param args Argumenti iz komandne linije
	 */
	public static void main(String[] args) {
		if (args.length == 2) {
			try {
				double width = Double.parseDouble(args[0]);
				double height = Double.parseDouble(args[1]);

				print(width, height, getArea(width, height), getCircumference(width, height));

			} catch (NumberFormatException ex) {
				System.out.println("Argumenti se ne mogu protumačiti kao brojevi.");
			}

		} else if (args.length == 0) {
			double width = -1;
			double height = -1;
			Scanner sc = new Scanner(System.in);

			while (width < 0) {
				System.out.println("Unesite sirinu >");
				width = checkEntry(sc);
			}
			while (height < 0) {
				System.out.println("Unesite visinu >");
				height = checkEntry(sc);
			}
			sc.close();
			print(width, height, getArea(width, height), getCircumference(width, height));
		} else {
			System.out.println("Neispravan broj argumenata.");
			Runtime.getRuntime().exit(0);
		}

	}

}
