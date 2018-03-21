package hr.fer.zemris.java.custom.collections;
/**
 * Collection that is not fully implemented. Should be used as superclass for class 
 * with specific implementation.
 * @author Josip Trbuscic
 *
 */
public class Collection {
	/**
	 * Empty constructor, does nothing.
	 * Do not use.
	 * @deprecated
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
	 * Always returns 0.
	 * Do not use.
	 * @deprecated 
	 */
	public int size() {
		return 0;
	}
	/**
	 * Does nothing.
	 * Do not use.
	 * @deprecated
	 */
	public void add(Object value) {
		
	}
	/**
	 * Always returns false;
	 * Do not use.
	 * @deprecated
	 */
	public boolean contains(Object value) {
		return  false;
	}
	/**
	 * Always returns false;
	 * Do not use.
	 * @deprecated
	 */
	public boolean remove(Object value) {
		return false;
	}
	/**
	 * Always throws exception;
	 * Do not use.
	 * @deprecated
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();	
	}
	/**
	 * Does nothing.
	 * Do not use.
	 * @deprecated
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
	 * Does nothing.
	 * Do not use.
	 * @deprecated
	 */
	public void clear() {
		 
	}
}
