package hr.fer.zemris.java.hw01;

import java.util.Scanner;
/**
 * Program za izgradnju uređenog binarnog stabla u koje 
 * korisnik može unositi cijelobrojne vrijednosti.
 * Osigurava se da će se različite vrijednosti 
 * pojaviti samo jednom.
 * @author Josip Trbuscic
 *
 */
public class UniqueNumbers {
	/**
	 * Struktura čvora binarnog stabla
	 */
	static class TreeNode {
		TreeNode right;
		TreeNode left;
		int value;
	}
	
	/**
	 * Rekurzivna metoda koja će dodati čvor u
	 * uređeno binarno stablo ako se čvor sa
	 * istom vrijednosti već ne nalazi u njemu.
	 * @param head glava binarnog stabla
	 * @param value vrijednost čvora
	 * @return glavu binarnog stabla
	 */
	public static TreeNode addNode(TreeNode head, int value) {
		if (head == null) {
			head = new TreeNode();
			head.value = value;
			System.out.println("Dodano.");
			return head;
		}
		
		if (head.value == value) {
			System.out.println("Broj već postoji. Preskačem.");
			return head;
		}
		
		if (value > head.value) {
			head.right = addNode(head.right, value);
		} else {
			head.left = addNode(head.left, value);
		}

		return head;
	}
	/**
	 * Metoda koja rekurzivnim obilaskom prebrojava članove
	 * binarnog stabla
	 * @param head glava binarnog stabla
	 * @return 	broj čvorova binarnog stabla
	 */
	public static int treeSize(TreeNode head) {
		int size = 0;
		if (head == null) {
			return 0;
		}
		size = treeSize(head.right) + treeSize(head.left);
		return 1 + size;

	}
	/**
	 * Metoda koja provjerava sadrži li binarno stablo čvor
	 * vrjednosti {@code value} u kojem slučaju vraća {@code true}
	 * @param head glava binarnog stabla
	 * @param value vrijednost čvora kojeg tražimo
	 * @return 	{@code true} ako se čvor nalazi u stablu,
	 * 			{@code false} ako se čvor ne  nalazi u stablu
	 */
	public static boolean containsValue(TreeNode head, int value) {
		boolean contains = false;

		if (head == null) {
			return contains;
		}

		if (head.value == value) {
			return true;
		}

		if (value < head.value) {
			contains = containsValue(head.left, value);
		} else {
			contains = containsValue(head.right, value);
		}

		return contains;
	}
	/**
	 * Rekurzivna metoda za ispis uređenog binarnog stabla
	 * rastućim poretkom
	 * @param head glava binarnog stabla
	 */
	public static void printAscending(TreeNode head) {
		if(head == null) {
			return;
		}
		printAscending(head.left);
		System.out.printf("%d ",head.value);
		printAscending(head.right);
	}
	/**
	 * Rekurzivna metoda za ispis uređenog binarnog stabla
	 * padajućim poretkom
	 * @param head glava binarnog stabla
	 */
	public static void printDescending(TreeNode head) {
		if(head == null) {
			return;
		}
		printDescending(head.right);
		System.out.printf("%d ",head.value);
		printDescending(head.left);
	}
	/**
	 * Metoda koja se poziva prilikom pokretanja
	 * programa.
	 * @param args Argumenti iz komandne linije
	 */
	public static void main(String[] args) {
		TreeNode head = null;
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			System.out.println("Unesi broj");
			String next = sc.next();
			
			if(next.equals("kraj")) {
				break;
			}
			
			try {
				int value = Integer.parseInt(next);
				head = addNode(head, value);
			} catch (NumberFormatException ex) {
				System.out.println("'" + next + "'" + " nije cijeli broj.");
			}

		}

		sc.close();
		
		System.out.printf("Ispis od najmanjeg: " );
		printAscending(head);
		System.out.printf("\nIspis od najvećeg: ");
		printDescending(head);
	}
}
