package hr.fer.zemris.java.gui.prim;

import static org.junit.Assert.*;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.junit.Test;

public class PrimListModelTest {

	@Test
	public void modelTest() {
		PrimListModel model = new PrimListModel();
		model.addListDataListener(new ListDataListener() {
			
			@Override
			public void intervalRemoved(ListDataEvent e) {}
			
			@Override
			public void intervalAdded(ListDataEvent e) {
				System.out.println("Added");
			}
			
			@Override
			public void contentsChanged(ListDataEvent e) {}
		});
		
		model.next();
		assertEquals(2, model.getElementAt(model.getSize()-1).intValue());
		model.next();
		assertEquals(3, model.getElementAt(model.getSize()-1).intValue());
		model.next();
		assertEquals(5, model.getElementAt(model.getSize()-1).intValue());
		model.next();
		assertEquals(7, model.getElementAt(model.getSize()-1).intValue());
		model.next();
		assertEquals(11, model.getElementAt(model.getSize()-1).intValue());
		model.next();
		assertEquals(13, model.getElementAt(model.getSize()-1).intValue());
		model.next();
		assertEquals(17, model.getElementAt(model.getSize()-1).intValue());
		model.next();
		assertEquals(19, model.getElementAt(model.getSize()-1).intValue());
		model.next();
		assertEquals(23, model.getElementAt(model.getSize()-1).intValue());
		model.next();
		assertEquals(29, model.getElementAt(model.getSize()-1).intValue());
	}

}
