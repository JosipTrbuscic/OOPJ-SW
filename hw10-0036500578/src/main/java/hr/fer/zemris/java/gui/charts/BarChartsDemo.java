package hr.fer.zemris.java.gui.charts;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class BarChartsDemo extends JFrame{
	
	public BarChartsDemo() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Prozor1");
		setLocation(50, 50);
		setSize(500, 500);
		initGUI();
	}
	
	private void initGUI(){
		List<XYValue> list = new ArrayList<>();
		list.add(new XYValue(1, 8));
		list.add(new XYValue(2, 20));
		list.add(new XYValue(3, 22));
		list.add(new XYValue(4, 10));
		list.add(new XYValue(5, 4));
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		JLabel pathLabel = new JLabel("Path do datoteke", JLabel.CENTER);
		cp.add(pathLabel, BorderLayout.PAGE_START);
		cp.add(new BarChartComponent(new BarChart(list, "Number of People in the car", "Frequency", 0, 22, 2)), BorderLayout.CENTER);
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				BarChartsDemo barChart= new BarChartsDemo();
				barChart.setVisible(true);
			}
		});
	}
}
