package hr.fer.zemris.java.custom.collections;

public class Collection {
	
	protected Collection() {
		
	}
	
	public boolean isEmpty() {
		if (this.size() == 0) return true;
		return false;
	}
	
	public int size() {
		return 0;
	}
	
	public void add(Object value) {
		
	}
	
	public boolean contains(Object value) {
		return  false;
	}
	
	public boolean remove(Object value) {
		return false;
	}
	
	public Object[] toArray() {
		throw new UnsupportedOperationException();	
	}

	public void forEach(Processor processor) {
	}
	
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
	
	public void clear() {
		 
	}
}
