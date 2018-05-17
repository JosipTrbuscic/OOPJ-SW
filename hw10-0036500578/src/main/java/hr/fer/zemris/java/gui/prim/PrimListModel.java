package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * List model containing prime numbers. Prime numbers 
 * are calculated only when they are requested.
 * @author Josip Trbuscic
 *
 */
public class PrimListModel implements ListModel<Integer>{
	
	/**
	 * List of prime numbers
	 */
	private List<Integer> elements = new ArrayList<>();
	
	/**
	 * List of listeners
	 */
	private List<ListDataListener> listeners = new ArrayList<>();
	
	/**
	 * Constructor
	 */
	public PrimListModel() {
		elements.add(1);
	}
	
	/**
	 * Adds given listener to the list.
	 * If list already contains given listener,
	 * listener will not be added.
	 * @param listener - listener to add
	 */
	@Override
	public void addListDataListener(ListDataListener listener) {
		if(!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	/**
	 * Returns element of the list at specified index.
	 * @param index - element index
	 * @throws IndexOutOfBoundsException if index is out of range
	 */
	@Override
	public Integer getElementAt(int index) {
		return elements.get(index);
	}

	/**
	 * Returns number of elements in the list
	 * @return number of elements in the list
	 */
	@Override
	public int getSize() {
		return elements.size();
	}

	/**
	 * Removes specified listener from the list
	 * @param listener - listener to be removed
	 */
	@Override
	public void removeListDataListener(ListDataListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * Appends next prime number to the list
	 */
	public void next() {
		if(elements.size() == 1) {
			elements.add(2);
			notifyListeners();
			return;
		}
		
		int next = elements.get(elements.size()-1) + 1;
		
		while(true){
			boolean isPrime = true;
			
			for(Integer i : elements) {
				if(i == 1) continue;
				if(next%i == 0) {
					isPrime = false;
					break;
				}
			}
			
			if(isPrime) {
				elements.add(next);
				
				notifyListeners();
				return;
			}
			
			next++;
		}
	}
	
	/**
	 * Notifies every listener that contents of prime number list 
	 * has changed
	 */
	private void notifyListeners() {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, elements.size()-1, elements.size()-1);
		for(ListDataListener l : listeners) {
			l.intervalAdded(event);
		}
	}

}
