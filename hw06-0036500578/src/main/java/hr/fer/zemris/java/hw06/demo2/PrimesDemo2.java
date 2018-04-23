package hr.fer.zemris.java.hw06.demo2;

/**
 * Demonstration program which test basic 
 * functionality
 * @author Josip Trbuscic
 *
 */
public class PrimesDemo2 {
	
	/**
	 * Method where program starts
	 * @param args -command line arguments. Not used here
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		for(Integer prime : primesCollection) {
		  for(Integer prime2 : primesCollection) {
		    System.out.println("Got prime pair: "+prime+", "+prime2);
		  }
		}
	}
}
