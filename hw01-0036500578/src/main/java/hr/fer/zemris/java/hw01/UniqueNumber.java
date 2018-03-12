package hr.fer.zemris.java.hw01;

import java.util.Scanner;

public class UniqueNumber {
	/**
	 * Struktura čvora
	 * @author josip
	 *
	 */
	static class TreeNode {
		TreeNode right;
		TreeNode left;
		int value;
	}
	
	/**
	 * Rekurzivna metoda za dodavanje čvora u stablo
	 * ako se on već ne nalazi u njemu
	 * @param head 
	 * @param value
	 * @return
	 */
	public static TreeNode addNode(TreeNode head, int value) {
		if (head == null) {
			head = new TreeNode();
			head.value = value;
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
	 * Metoda koja provjerava sadrži li binarno stablo navedeni
	 * element u kojem slučaju vraća {@code true}
	 * 
	 * @param head
	 * @param value 
	 * @return
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
	
	public static void lowToHigh(TreeNode head) {
		if(head == null) {
			return;
		}
		lowToHigh(head.left);
		System.out.printf("%d ",head.value);
		lowToHigh(head.right);
	}
	
	public static void highToLow(TreeNode head) {
		if(head == null) {
			return;
		}
		highToLow(head.right);
		System.out.printf("%d ",head.value);
		highToLow(head.left);
	}

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
		lowToHigh(head);
		System.out.printf("\nIspis od najvećeg: ");
		highToLow(head);
	}
}
