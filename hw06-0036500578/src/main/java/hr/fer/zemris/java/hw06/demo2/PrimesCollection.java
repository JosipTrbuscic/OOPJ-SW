package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;

/**
 * This class represents collection of first n 
 * prime numbers. Prime numbers are calculated 
 * only when they are needed, which means there 
 * is no internal storage for the numbers.
 * @author Josip Trbuscic
 *
 */
public class PrimesCollection implements Iterable<Integer> {
	
	/**
	 * Number of prime numbers 
	 */
	int numberOfPrimes;
	
	/**
	 * Constructs new instance of this class 
	 * with given number of first n prime numbers.
	 * @param numberofPrimes - number of prime numbers
	 * @throws IllegalArgumentException if number of prime numbers
	 * 			is not positive
	 */
	public PrimesCollection(int numberofPrimes) {
		if(numberofPrimes < 1) throw new IllegalArgumentException("Number of requested primes must be positive");
		
		this.numberOfPrimes = numberofPrimes;
	}

	/**
	 * Returns new iterator over this
	 * collection
	 * @return new iterator
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new primeCollectionIterator();
	}
	
	/**
	 * Class which represents iterator over the collection 
	 * of prime numbers. Next prime number will be calculated 
	 * when it is requested by user.
	 *
	 */
	private class primeCollectionIterator implements Iterator<Integer>{
		/**
		 * First prime number
		 */
		private final static int FIRST_PRIME = 2;
		
		/**
		 * Current prime number
		 */
		int current;
		
		/**
		 * number of prime numbers returned
		 */
		int counter;
		
		/**
		 * Constructs new instance of iterator and
		 * sets first prime number as the current one.
		 */
		public primeCollectionIterator() {
			current = FIRST_PRIME;
		}
		
		/**
		 * Returns true if maximum number of prime
		 * numbers is nut exceeded
		 */
		@Override
		public boolean hasNext() {
			return counter < PrimesCollection.this.numberOfPrimes;
		}

		/**
		 * Returns next prime number
		 */
		@Override
		public Integer next() {
			if(counter == 0) {
				counter++;
				return current;
			}
			int next = current+1;
			
			while(true){
				boolean isPrime = true;
				
				for(int j = 2; j <= Math.sqrt(next); j++) {
					if(next%j == 0) {
						isPrime = false;
						break;
					}
				}
				
				if(isPrime) {
					counter++;
					current = next;
					return next;
				}
				
				next++;
			}
		}
		
	}
}
