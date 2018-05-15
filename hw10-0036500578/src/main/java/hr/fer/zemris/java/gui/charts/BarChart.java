package hr.fer.zemris.java.gui.charts;

import java.util.List;

public class BarChart {
	private List<XYValue> list;
	private String xTitle;
	private String yTitle;
	private int yMin;
	private int yMax;
	private int step;
	
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

	public List<XYValue> getList() {
		return list;
	}

	public String getxTitle() {
		return xTitle;
	}

	public String getyTitle() {
		return yTitle;
	}

	public int getyMin() {
		return yMin;
	}

	public int getyMax() {
		return yMax;
	}

	public int getStep() {
		return step;
	}
	
	
}
