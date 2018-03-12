package hr.fer.zemris.java.hw01;

import java.util.Scanner;
/**
 * 
 * @author josip
 *
 */
public class Factorial {
	/**
	 * Metoda računa n faktorijela prirodnog broja
	 * @param n argument funkcije n!
	 * @return vrijednost funkcije n!
	 */
	public static long calculateFactorial(int n) {
		if(n<0) {
			throw new IllegalArgumentException("Argument funkcije ne smije biti negativan broj");
		}
	
		long factorial=1;
		
		while(n>=1) {
			factorial *= n;
			n--;
		}
		
		return factorial;
	}

	/**
	 * Metoda koja se poziva prilikom pokretanja
	 * programa.
	 * @param args Argumenti iz komandne linije
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("Unesite broj >");
			String next = sc.next();
			
			if (next.equals("kraj")) {
				break;
			}
			try {
				int number = Integer.parseInt(next);
				
				if(number >=1 && number <=20) {
					long factorial = calculateFactorial(number);
					
					System.out.println(number+"!" +" = " + factorial );
				} else {
					System.out.println("'"+next+"'"+" nije broj u dozvoljenom rasponu.");
				}

			} catch(NumberFormatException ex) {
				System.out.println("'"+next+"'"+" nije cijeli broj.");
			}
			
		}
		
		System.out.println("Doviđenja");
		sc.close();
	}

}