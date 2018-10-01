package hr.fer.zemris.java.gui.layout;

import static org.junit.Assert.assertEquals;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

public class CalcLayoutTest {

	private JPanel panel;

	@Before
	public void setup() {
		panel = new JPanel(new CalcLayout());
	}

	@Test
	public void testPreferedSize() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10, 30));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(20, 15));
		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();

		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}

	@Test
	public void testPreferedSize2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(108, 15));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(16, 30));
		p.add(l1, new RCPosition(1, 1));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();

		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}
	
	@Test
	public void maxSize() {
		JLabel label1 = new JLabel();
		JLabel label2 = new JLabel();
		JLabel label3 = new JLabel();
		JLabel label4 = new JLabel();

		panel.add(label1,"2,2");
		panel.add(label2,"3,3");
		panel.add(label3,"4,4");
		panel.add(label4,"5,5");

		label1.setMaximumSize(new Dimension(50, 40));
		label2.setMaximumSize(new Dimension(60, 30));
		label3.setMaximumSize(new Dimension(40, 40));
		label4.setMaximumSize(new Dimension(60, 60));
		
		assertEquals(280, panel.getMaximumSize().width);
		assertEquals(150, panel.getMaximumSize().height);
		
	}
	
	@Test
	public void prefSize() {
		JLabel label1 = new JLabel();
		JLabel label2 = new JLabel();
		JLabel label3 = new JLabel();
		JLabel label4 = new JLabel();

		panel.add(label1,"2,2");
		panel.add(label2,"3,3");
		panel.add(label3,"4,4");
		panel.add(label4,"5,5");

		label1.setPreferredSize(new Dimension(50, 20));
		label2.setPreferredSize(new Dimension(60, 30));
		label3.setPreferredSize(new Dimension(40, 40));
		label4.setPreferredSize(new Dimension(60, 60));
		
		assertEquals(420, panel.getPreferredSize().width);
		assertEquals(300, panel.getPreferredSize().height);
		
	}
	
	@Test
	public void minSize() {
		JLabel label1 = new JLabel();
		JLabel label2 = new JLabel();
		JLabel label3 = new JLabel();
		JLabel label4 = new JLabel();

		panel.add(label1,"2,2");
		panel.add(label2,"3,3");
		panel.add(label3,"4,4");
		panel.add(label4,"5,5");

		label1.setMinimumSize(new Dimension(50, 20));
		label2.setMinimumSize(new Dimension(60, 30));
		label3.setMinimumSize(new Dimension(40, 40));
		label4.setMinimumSize(new Dimension(60, 60));
		
		assertEquals(420, panel.getMinimumSize().width);
		assertEquals(300, panel.getMinimumSize().height);
		
	}

	@Test(expected = CalcLayoutException.class)
	public void invalidPosition1() {
		panel.add(new JLabel(), "1,3");
	}

	@Test(expected = CalcLayoutException.class)
	public void invalidPosition3() {
		panel.add(new JLabel(), "10,1");
	}

	@Test(expected = CalcLayoutException.class)
	public void invalidPosition4() {
		panel.add(new JLabel(), "4,8");

	}

	@Test(expected = CalcLayoutException.class)
	public void collisionTest() {
		panel.add(new JLabel(), "4,1");
		panel.add(new JLabel(), "4,1");
	}

	@Test
	public void removeTest() {
		JLabel label = new JLabel();
		panel.add(label, "3,3");
		panel.remove(label);
		panel.add(label, "3,3");
	}

}
