package hr.fer.zemris.java.hw01;

import java.lang.Runtime;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Scanner;
/**
 * Program za računanje opsega i površine
 * pravokutnika. Argumenti se mogu unjeti 
 * kroz komandnu liniju ili ih može unijeti 
 * korisnik.
 * @author Josip Trbuscic
 *
 */
public class Rectangle {

	/**
	 * Metoda koja računa površinu pravokutnika.
	 * @param width širina pravokutnika. Mora biti poitivna
	 * @param height visina pravokutnika. Mora biti pozitivna
	 * @return 	vraća iznos izraza x*y
	 * @throws 	IllegalArgumentException ako je 
	 * 			argument {@code width} ili {@code height}
	 * 			manji ili jednak od nule
	 */
	public static double calculateArea(double width, double height) {
		if(width <= 0 || height <= 0) {
			throw new IllegalArgumentException("Neispravan argument");
		}
		
		return width * height;
	}

	/**
	 * Metoda koja računa opseg pravokutnika. 
	 * @param width	širina pravokutnika
	 * @param height visina stranica pravokutnika
	 * @return	vraća iznos izraza 2*(x+y)
	 * @throws 	IllegalArgumentException ako je 
	 * 			argument {@code width} ili {@code height}
	 * 			manji od nule
	 */
	public static double calculateCircumference(double width, double height) {
		if(width <= 0 || height <= 0) {
			throw new IllegalArgumentException("Neispravan argument");
		}
		
		return 2 * (width + height);
	}
	
	/**
	 * Metoda za formatirani ispis površine i opsega
	 * @param width  širina pravokukutnika
	 * @param height visina pravokutnika
	 * @throws 	IllegalArgumentException ako je 
	 * 			argument {@code width} ili {@code height}
	 * 			manji od nule
	 */
	public static void print(double width, double height) {
		if(width <= 0 || height <= 0) {
			throw new IllegalArgumentException("Neispravan argument");
		}
		System.out.format("Pravokutnik sirine %.2f i visine %.2f ima povrsinu %.2f te opseg %.2f"
				, width, height, calculateArea(width, height), calculateCircumference(width, height));
	}
	
	/**
	 * Metoda za unos stranice pravokutnika i provjeru
	 * unesene vrijednosti. Vrijednost unosi korisnik.
	 * @param sc čitač znakovnog niza sa ulaznog toka
	 * @return	 pročitanu vrijednost
	 */
	public static double scanDimension(Scanner sc, String dimension) {
		String next = null;
		
		while(true) {
			System.out.println(dimension);
			
			next = sc.next();
	
			try {
				Double value = NumberFormat.getInstance().parse(next).doubleValue();
	
				if (value < 0) {
					System.out.println("Unijeli ste negativnu vrijednost");
				} else {
					return value;
				}
	
			} catch (ParseException ex) {
				System.out.println("'" + next + "'" + " se ne može protumačiti kao broj.");
			}
		}

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

				print(width, height);

			} catch (NumberFormatException ex) {
				System.out.println("Argumenti se ne mogu protumačiti kao brojevi.");
			}

		} else if (args.length == 0) {
			Scanner sc = new Scanner(System.in);

			double width = scanDimension(sc, "Unesite širinu >");
			double height = scanDimension(sc, "Unesite visinu >");
			
			print(width, height);
			
			sc.close();
		} else {
			System.out.println("Neispravan broj argumenata.");
			Runtime.getRuntime().exit(0);
		}
		
		

	}

}
