package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class PrimDemo extends JFrame {
	
	public PrimDemo() {
		setLocation(20, 50);
		setSize(300, 200);
		setTitle("Prim Demo");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		initGUI();
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridLayout(1, 2));
		PrimListModel model = new PrimListModel();
		
		JList<Integer> firstList = new JList<>(model);
		JList<Integer> secondList = new JList<>(model);

		panel.add(new JScrollPane(firstList));
		panel.add(new JScrollPane(secondList));

		JButton next = new JButton("sljedeći");
		next.addActionListener(l->{	
			model.next();
			firstList.ensureIndexIsVisible(model.getSize()-1);
			secondList.ensureIndexIsVisible(model.getSize()-1);
		});
		
		cp.add(next, BorderLayout.PAGE_END);
		cp.add(panel);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				JFrame window = new PrimDemo();
				window.setVisible(true);
				
			}
		});
	}
}
