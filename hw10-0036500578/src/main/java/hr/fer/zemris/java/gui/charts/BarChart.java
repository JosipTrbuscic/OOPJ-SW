package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Class that represents bar chart. Values 
 * are provided  in a list by user
 * @author Josip Trbuscic
 *
 */
public class BarChart {
	
	/**
	 * List of values
	 */
	private List<XYValue> list;
	
	/**
	 * Title of x axis
	 */
	private String xTitle;
	
	/**
	 * Title of y value
	 */
	private String yTitle;
	
	/**
	 * Minimum value on y axis
	 */
	private int yMin;
	
	/**
	 * Maximum value on x axis
	 */
	private int yMax;
	
	/**
	 * Y axis step
	 */
	private int step;
	
	/**
	 * Constructor
	 * @param list - list of values
	 * @param xTitle - x axis value
	 * @param yTitle - y axis value
	 * @param yMin - minimum value on y axis
	 * @param yMax - maximum value on x axis
	 * @param step - y axis step
	 */
	public BarChart(List<XYValue> list, String xTitle, String yTitle, int yMin, int yMax, int step) {
		if(yMin >= yMax) throw new IllegalArgumentException("yMin must be smaller than yMax");
		
		while((yMax-yMin)%step != 0) yMax++;
		
		this.list = list;
		this.xTitle = xTitle;
		this.yTitle = yTitle;
		this.yMin = yMin;
		this.yMax = yMax;
		this.step = step;
	}

	/**
	 * Returns list of values
	 * @return list of values
	 */
	public List<XYValue> getList() {
		return list;
	}

	/**
	 * Returns x axis title
	 * @return x axis title
	 */
	public String getxTitle() {
		return xTitle;
	}

	/**
	 * Returns y axis value
	 * @return y axis value
	 */
	public String getyTitle() {
		return yTitle;
	}

	/**
	 * Returns minimum value on y axis
	 * @return minimum value on y axis
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * Returns maximum value on y axis
	 * @return maximum value on y axis
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * Returns y axis step
	 * @return y axis step
	 */
	public int getStep() {
		return step;
	}
	
	
}
