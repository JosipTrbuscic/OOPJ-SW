package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class PrimListModel implements ListModel<Integer>{
	private List<Integer> elements = new ArrayList<>();
	private List<ListDataListener> listeners = new ArrayList<>();
	
	public PrimListModel() {
		elements.add(1);
	}
	@Override
	public void addListDataListener(ListDataListener listener) {
		if(!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public Integer getElementAt(int index) {
		return elements.get(index);
	}

	@Override
	public int getSize() {
		return elements.size();
	}

	@Override
	public void removeListDataListener(ListDataListener listener) {
		listeners.remove(listener);
	}
	
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
	
	private void notifyListeners() {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, elements.size()-1, elements.size()-1);
		for(ListDataListener l : listeners) {
			l.intervalAdded(event);
		}
	}

}
