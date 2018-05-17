package hr.fer.zemris.java.gui.charts;


import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Program used to display given data as a bar chart. 
 * Data must be provided in a file in specific format.
 * Program takes one user argument which represents path
 * to the file.
 * @author Josip Trbuscic
 *
 */
public class BarChartsDemo extends JFrame{
	/**
	 * Universal ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Bar chart
	 */
	private BarChart barChart;
	
	/**
	 * Str+ring representation of path
	 */
	private String path;
	
	/**
	 * Constructor
	 * @param barChart - bar chart
	 * @param path - path to file
	 */
	public BarChartsDemo(BarChart barChart, String path) {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("BarChartsDemo");
		setLocation(50, 50);
		setSize(600, 630);
		this.barChart = barChart;
		this.path = path;
		initGUI();
	}
	
	/**
	 * GUI initialization method
	 */
	private void initGUI(){
		List<XYValue> list = new ArrayList<>();
		list.add(new XYValue(1, 8));
		list.add(new XYValue(2, 20));
		list.add(new XYValue(3, 22));
		list.add(new XYValue(4, 10));
		list.add(new XYValue(5, 4));
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		JLabel pathLabel = new JLabel(path, JLabel.CENTER);
		cp.add(pathLabel, BorderLayout.PAGE_START);
		cp.add(new BarChartComponent(barChart), BorderLayout.CENTER);
		
	}
	
	/**
	 * Starting method
	 * @param args - path to file 
	 */
	public static void main(String[] args){
		if(args.length != 1) {
			System.out.println("Program accepts exactly one argument.\nTerminating...");
			System.exit(0);
		}
		
		Path path = extractPath(args[0]);
		if(path == null) {
			System.out.println("Invalid file path format.\nTerminating");
			System.exit(0);
		}
		
		File file = path.toFile();
		if(!file.exists() || file.isDirectory()) {
			System.out.println("Given file doesnt represent a file.\nTerminating");
			System.exit(0);
		}
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath(), StandardOpenOption.READ)));
		} catch (IOException ignorable) {}
		
		String xTitle = null;
		String yTitle = null;
		String[] values =null;
		String yMin = null;
		String yMax = null;
		String step = null;
		try {
			xTitle = br.readLine();
			yTitle = br.readLine();
			values = br.readLine().split("\\s+");
			yMin = br.readLine();
			yMax = br.readLine();
			step = br.readLine();
		}catch (IOException e){
			System.out.println("Invalid file format");
			System.exit(0);
		}
		
		List<XYValue> valueList = new ArrayList<>();
		
		for(String value : values) {
			try {
				String[] parts = value.split(",");
				valueList.add(new XYValue(Integer.parseInt(parts[0]),
											Integer.parseInt(parts[1])));
			} catch(NumberFormatException e) {
				System.out.println("Invalid values format");
				System.exit(0);
			}
		}
		Collections.sort(valueList);
		
		BarChart barChart = new BarChart(valueList, xTitle, yTitle,
				Integer.parseInt(yMin),
				Integer.parseInt(yMax),
				Integer.parseInt(step));
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				BarChartsDemo barChartDemo;
				try {
					barChartDemo = new BarChartsDemo(barChart, file.getCanonicalPath());
					barChartDemo.setVisible(true);
				} catch (IOException ignorable) {}

			}
		});
	}
	
	/**
	 * Method used to extract file path from given string.
	 * Returns null if string is not representation of valid path.
	 * @param string - string representation of a path 
	 * @return Returns path if one could be extracted, null otherwise.
	 */
	private static Path extractPath(String string) {
		Pattern p = Pattern.compile("^\\s*((\"(.+)\")|([\\S]+))\\s*$");
		Matcher m = p.matcher(string);

		if (!m.find()) {
			return null;
		}

		if (m.group(3) != null) {
			return Paths.get(m.group(3).replace("\\\"", "\"").replace("\\\\", "\\"));
		}
		
		return Paths.get(m.group(4));
	}
}
