package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

public class BarChartComponent extends JComponent {
	private static final Color RECT = new Color(16021320);
	private static final Color BACK_GRID = new Color(15654075);
	private static final int LEFT_INDENT1 = 10;
	private static final int LEFT_INDENT2 = 10;
	private static final int BOTTOM_INDENT1 = 10;
	private static final int BOTTOM_INDENT2 = 10;
	private static final int ARROW_LENGHT = 10;
	private BarChart barChart;
	
	
	public BarChartComponent(BarChart barChart) {
		this.barChart = barChart;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Insets insets = getInsets();
		FontMetrics fm = g.getFontMetrics();
		
		int width = getWidth();
		int height = getHeight();

		int maxY = largestYValueStringWidth(barChart.getList(), fm);
		
		int yAxisIndent = LEFT_INDENT1 + LEFT_INDENT2 + maxY + insets.left + fm.getHeight();
		int xAxisIndent = BOTTOM_INDENT1 + BOTTOM_INDENT2 + fm.getHeight() + fm.getHeight()+insets.bottom;
		drawGrid(g, insets, width, height, xAxisIndent, yAxisIndent, fm);
		g.setColor(Color.BLACK);
		drawAxesNames(g, insets, width, height, fm, yAxisIndent, xAxisIndent);

		
		
	}
	
	private void drawGrid(Graphics g, Insets insets, int width, int height, int xAxisIndent, int yAxisIndent, FontMetrics fm) {
		int barChartStep = barChart.getStep();
		int barChartYMin = barChart.getyMin();
		int top = insets.top + ARROW_LENGHT;
		int right = width - (insets.right + ARROW_LENGHT);
		int rows = (barChart.getyMax()-barChartYMin)/barChartStep;
		int columns = barChart.getList().size();
		
		int xStep = (height - xAxisIndent - top)/rows;
		int yStep = (right - yAxisIndent)/columns;
		
		List<XYValue> list = barChart.getList();
		Font numberFont = new Font(fm.getFont().getName(), Font.BOLD, fm.getFont().getSize());
		//horizontal
		for(int i = 0; i < rows+1; ++i) {
			
			if(i == 0) {
				int[] xValues = {right+1,right+ARROW_LENGHT+1,right+1};
				int[] yValues = {height-xAxisIndent-i*xStep-4,height-xAxisIndent-i*xStep,height-xAxisIndent-i*xStep+4};
				g.setColor(Color.GRAY);
				g.drawLine(yAxisIndent, height-xAxisIndent-i*xStep, right, height-xAxisIndent-i*xStep);
				g.fillPolygon(xValues, yValues, 3);
			} else {
				g.setColor(BACK_GRID);
				g.drawLine(yAxisIndent, height-xAxisIndent-i*xStep, right, height-xAxisIndent-i*xStep);
			}
			
			g.setColor(Color.GRAY);
			g.drawLine(yAxisIndent, height-xAxisIndent-i*xStep, yAxisIndent-4, height-xAxisIndent-i*xStep);

			
			String value = String.valueOf(barChartYMin + barChartStep*i);
			g.setColor(Color.BLACK);
			g.setFont(numberFont);
			g.drawString(value, xAxisIndent-BOTTOM_INDENT2-fm.stringWidth(value),height-xAxisIndent-i*xStep+fm.getAscent()/2-2);
			g.setFont(fm.getFont());
		}
		
		//vertical
		for(int i = 0; i < columns+1; ++i) {
			
			if(i == 0) {
				int[] xValues = {yAxisIndent + i *yStep + 4,yAxisIndent + i *yStep,yAxisIndent + i *yStep - 4};
				int[] yValues = {top,top-ARROW_LENGHT,top};
				g.setColor(Color.GRAY);
				g.drawLine(yAxisIndent + i *yStep, height-xAxisIndent , yAxisIndent + i *yStep, top);
				g.fillPolygon(xValues, yValues, 3);
			} else {
				g.setColor(BACK_GRID);
				g.drawLine(yAxisIndent + i *yStep, height-xAxisIndent -1, yAxisIndent + i *yStep, top);
			}
			
			g.setColor(Color.GRAY);
			g.drawLine(yAxisIndent + i *yStep, height-xAxisIndent -1, yAxisIndent + i *yStep, height-xAxisIndent +5);
			if(i != columns) {
				String value = String.valueOf(list.get(i).getX());
				g.setColor(Color.BLACK);
				g.setFont(numberFont);
				g.drawString(value, yAxisIndent + i *yStep + yStep/2 - fm.stringWidth(value)/2, height-xAxisIndent+BOTTOM_INDENT2+fm.getAscent());
				g.setFont(fm.getFont());

				g.setColor(RECT);
				g.fillRect(yAxisIndent + i *yStep+1, height-xAxisIndent - list.get(i).getY()*xStep/barChartStep -2 , yStep, (list.get(i).getY()*xStep/barChartStep)+2);
				
				g.setColor(Color.WHITE);
				g.drawLine(yAxisIndent + (i+1) *yStep,height-xAxisIndent -3, yAxisIndent + (i+1) * yStep, height-xAxisIndent - list.get(i).getY()*xStep/barChartStep);
			}

		}
		
	}
	
	private void numerateAxes(Graphics g, Insets insets, int width, int height, int maxY) {
		FontMetrics fm = g.getFontMetrics();
		Graphics2D g2d = (Graphics2D) g;
		List<XYValue> list = barChart.getList();
		
		
		for(int i = 0; i < list.size(); i++) {
			g2d.drawString(String.valueOf(list.get(i).getX()),
					width/2-insets.right - insets.left - fm.stringWidth(barChart.getxTitle())/2,
					height - (insets.bottom+fm.getHeight()));
		}
	}
	
	private void drawAxesNames(Graphics g, Insets insets, int width, int height, FontMetrics fm, int yAxisIndent, int xAxisIndent) {
		Graphics2D g2d = (Graphics2D) g;
		int graphWidth = width - yAxisIndent;
		int graphHeight = height - xAxisIndent;
		
		g2d.drawString(barChart.getxTitle(),
				yAxisIndent + graphWidth/2-insets.right - insets.left - fm.stringWidth(barChart.getxTitle())/2,
				height - (insets.bottom+5));
		
		AffineTransform at = AffineTransform.getQuadrantRotateInstance(3);
		g2d.setTransform(at);
		g2d.drawString(barChart.getyTitle(),
				-(graphHeight/2 + insets.top + insets.bottom + fm.stringWidth(barChart.getyTitle())/2),
				insets.left+fm.getHeight());
		
	}
	
	private int largestYValueStringWidth(List<XYValue> list, FontMetrics fm) {
		int maxWidth = 0;
		for(XYValue value : list) {
			int width = fm.stringWidth(String.valueOf(value.getY()));
			if(width > maxWidth){
				maxWidth = width;
			}
		}
		return maxWidth;
	}
	
}
