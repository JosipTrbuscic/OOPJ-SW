package hr.fer.zemris.java.custom.scripting.nodes;
/**
 * Collection that is not fully implemented. Should be used as superclass for class 
 * with specific implementation.
 * @author Josip Trbuscic
 *
 */
public class Collection {
	/**
	 * Constructs new collection. This implementation
	 * does nothing. Class extending {@code Collection} 
	 * should offer specific implementation.
	 */
	protected Collection() {
		
	}
	/**
	 * Returns true if this collection contains no elements.
	 * This implementation returns size() == 0.
	 * @return {@code true} if this collection contains no elements
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	/**
	 * Returns number of elements in this collection.
	 * This implementation always returns 0 and 
	 * should not be used. Class extending {@code Collection} 
	 * should offer specific implementation.
	 */
	public int size() {
		return 0;
	}
	/**
	 * Appends specified element to the end of Collection.
	 * This implementation does nothing. Class extending
	 *  {@code Collection} should offer specific implementation.
	 *  @param value - object to be added to collection
	 */
	public void add(Object value) {
		
	}
	/**
	 * Returns true if collection contains specified element.
	 * This implementation always returns false and should
	 * not be used. Class extending {@code Collection} 
	 * should offer specific implementation. 
	 */
	public boolean contains(Object value) {
		return  false;
	}
	/**
	 * Removes first instance of the specified element from
	 * this collection. Returns true if element is successfully
	 * removed from collection. This implementation always
	 * returns false and should not be used. Class extending
	 *  {@code Collection} should offer specific implementation.
	 *  @param value to be removed
	 *  @return {@code true} if specified element is removed from collection.
	 */
	public boolean remove(Object value) {
		return false;
	}
	/**
	 * Returns new array which contains all elements from collection.
	 * Order of element will not be changed. This implementation 
	 * always throws {@code UnsupportedOperationException} and should
	 * not be used. Class extending {@code Collection} should offer
	 *  specific implementation.
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();	
	}
	/**
	 * Processes every collection element individually
	 * in a way specified by Processor argument.
	 * This implementation does nothing. Class extending
	 *  {@code Collection} should offer specific implementation.
	 */
	public void forEach(Processor processor) {
		
	}
	/**
	 * Adds all of the elements in the specified collection to this collection
	 * @param 	other collection whose elements
	 * 			will be added to this collection
	 */
	public void addAll(Collection other) {
		
		class AddingProcessor extends Processor{
			
			@Override
			public void process(Object value) {
				if (value ==  null) throw new NullPointerException("Null cannot be processed");
				Collection.this.add(value);
			}
		}
		
		AddingProcessor pr =  new AddingProcessor();
		other.forEach(pr);
	}
	/**
	 * Removes all elements from the collection.
	 * This implementation does nothing. Class extending
	 *  {@code Collection} should offer specific implementation.
	 */
	public void clear() {
		 
	}
}
