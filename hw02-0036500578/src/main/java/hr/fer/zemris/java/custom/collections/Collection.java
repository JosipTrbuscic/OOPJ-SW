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
		
		class ProcessorAdd extends Processor{
			private Collection collection;
			
			public ProcessorAdd(Collection collection) {
				this.collection = collection; 
			}
			
			@Override
			public void process(Object value) {
				if (value ==  null) throw new NullPointerException("Predani argument se ne može obraditi");
				collection.add(value);
			}
		}
		
		ProcessorAdd pr =  new ProcessorAdd(this);
		other.forEach(pr);
	}
	
	public void clear() {
		 
	}
}
