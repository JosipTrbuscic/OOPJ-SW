package hr.fer.zemris.java.hw06.demo2;

/**
 * Demonstration program which test basic 
 * functionality
 * @author Josip Trbuscic
 *
 */
public class PrimesDemo1 {
	
	/**
	 * Method where program starts
	 * @param args -command line arguments. Not used here
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
		for(Integer prime : primesCollection) {
		    System.out.println("Got prime: "+prime);
		}
	}
}
