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
import static java.lang.Math.abs;
import static java.lang.Math.round;

/**
 * Graphical component used to visualize data as a bar chart
 * @author Josip Trbuscic
 *
 */
public class BarChartComponent extends JComponent {
	
	private static final long serialVersionUID = 1L;
	/**
	 * Rectangle color
	 */
	private static final Color RECT = new Color(16021320);
	
	/**
	 * Background grid color
	 */
	private static final Color BACK_GRID = new Color(15654075);
	
	/**
	 * Indentation of left side
	 */
	private static final int LEFT_INDENT = 10;
	
	/**
	 * Indentation of bottom side
	 */
	private static final int BOTTOM_INDENT = 10;
	
	/**
	 * Indentation of right side
	 */
	private static final int RIGHT_INDENT = 10;
	
	/**
	 * Indentation of top side
	 */
	private static final int TOP_INDENT = 10;
	
	/**
	 * Axis arrow length
	 */
	private static final int ARROW_LENGHT = 10;

	/**
	 * Data to graph
	 */
	private BarChart barChart;

	/**
	 * Constructor
	 * @param barChart - data to graphs
	 */
	public BarChartComponent(BarChart barChart) {
		this.barChart = barChart;
	}

	/**
	 * Draws bar chart
	 */
	@Override
	public void paintComponent(Graphics g) {
		Insets insets = getInsets();
		FontMetrics fm = g.getFontMetrics();

		int width = getWidth();
		int height = getHeight();

		int maxY = largestYValueStringWidth(barChart.getList(), fm);

		int yAxisIndent = 2*LEFT_INDENT + maxY + insets.left + fm.getHeight();
		int xAxisIndent = 2 * BOTTOM_INDENT+ fm.getHeight() + fm.getHeight() + insets.bottom;
		drawGrid(g, insets, width, height, xAxisIndent, yAxisIndent, fm);
		drawAxesNames(g, insets, width, height, fm, yAxisIndent, xAxisIndent);

	}

	/**
	 * Draws grid and rectangles
	 * @param g - graphics
	 * @param insets - insets
	 * @param width - component width
	 * @param height - component height
	 * @param xAxisIndent - distance from bottom of the component to x axis
	 * @param yAxisIndent - distance from left side of the component to y axis
	 * @param fm - font metrics
	 */
	private void drawGrid(Graphics g, Insets insets, int width, int height, int xAxisIndent, int yAxisIndent,
			FontMetrics fm) {
		int barChartStep = barChart.getStep();
		int barChartYMin = barChart.getyMin();
		int top = insets.top + ARROW_LENGHT + TOP_INDENT;
		int right = width - (insets.right + ARROW_LENGHT+RIGHT_INDENT);
		double rows = (barChart.getyMax() - barChartYMin) /(double) barChartStep;
		int columns = barChart.getList().size();

		double xStep = (height - xAxisIndent - top) / (double) rows;
		double yStep = (right - yAxisIndent) / (double)columns;

		List<XYValue> list = barChart.getList();
		Font numberFont = new Font(fm.getFont().getName(), Font.BOLD, fm.getFont().getSize());
		// horizontal
		for (int i = 0; i < rows + 1; ++i) {
			if (i == 0) {
				drawHorizontalArrow(g, right+RIGHT_INDENT/2,height - xAxisIndent);
				drawGridLine(g, yAxisIndent, height - xAxisIndent, right+RIGHT_INDENT/2,height - xAxisIndent, Color.GRAY, false);
			} else {
				drawGridLine(g, yAxisIndent,
						(int)round(height - xAxisIndent - i * xStep),
						right+RIGHT_INDENT/2,
						(int)round( height - xAxisIndent - i * xStep),
						BACK_GRID, false);
			}
			String value = String.valueOf(barChartYMin + barChartStep * i);
			g.setColor(Color.BLACK);
			g.setFont(numberFont);
			g.drawString(value, xAxisIndent - RIGHT_INDENT- fm.stringWidth(value),
					(int)round(height - xAxisIndent - i * xStep + fm.getAscent() / 2. ));
			g.setFont(fm.getFont());
		}

		// vertical
		for (int i = 0; i < columns + 1; ++i) {
			if (i == 0) {
				drawVerticalArrow(g, yAxisIndent,top-TOP_INDENT/2);
				drawGridLine(g,yAxisIndent,height - xAxisIndent, yAxisIndent,top-TOP_INDENT/2,Color.GRAY, true);
			} else {
				drawGridLine(g, (int)round(yAxisIndent + i * yStep),
						height - xAxisIndent,
						(int)round(yAxisIndent + i * yStep),
						top-TOP_INDENT/2, BACK_GRID, true);
			}
			if (i != columns) {
				String value = String.valueOf(list.get(i).getX());
				g.setColor(Color.BLACK);
				g.setFont(numberFont);
				g.drawString(value,(int)round( yAxisIndent + i * yStep + yStep / 2 - fm.stringWidth(value) / 2.),
						height - xAxisIndent + BOTTOM_INDENT + fm.getAscent());
				g.setFont(fm.getFont());
				
				if (list.get(i).getY() > barChart.getyMin()) {
					g.setColor(RECT);
					g.fillRect((int)round(yAxisIndent + i * yStep+1),
							(int)round(height - xAxisIndent - abs(list.get(i).getY() - barChart.getyMin()) * xStep / barChartStep),
							(int)round(yStep)-1,
							(int)round( (abs(list.get(i).getY() - barChart.getyMin()) * xStep / barChartStep)));
					
					g.setColor(Color.WHITE);
					g.drawLine((int)round(yAxisIndent + (i + 1) * yStep), height - xAxisIndent, (int)round(yAxisIndent + (i + 1) * yStep),
							(int)round(height - xAxisIndent - abs(list.get(i).getY() - barChart.getyMin())  * xStep / barChartStep));
				}

			}
		}

	}

	/**
	 * Draws names of axes
	 * @param g - graphics
	 * @param insets
	 * @param insets - insets
	 * @param width - component width
	 * @param height - component height
	 * @param xAxisIndent - distance from bottom of the component to x axis
	 * @param yAxisIndent - distance from left side of the component to y axis
	 * @param fm - font metrics
	 */
	private void drawAxesNames(Graphics g, Insets insets, int width, int height, FontMetrics fm, int yAxisIndent,
			int xAxisIndent) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		int graphWidth = width - yAxisIndent;
		int graphHeight = height - xAxisIndent;

		g2d.drawString(barChart.getxTitle(),
				yAxisIndent + graphWidth / 2 - insets.right - insets.left - fm.stringWidth(barChart.getxTitle()) / 2,
				height - (insets.bottom + BOTTOM_INDENT));

		AffineTransform at = AffineTransform.getQuadrantRotateInstance(3);
		g2d.setTransform(at);
		g2d.drawString(barChart.getyTitle(),
				-(graphHeight / 2 + insets.top + insets.bottom + fm.stringWidth(barChart.getyTitle()) / 2),
				insets.left + fm.getHeight());

	}

	/**
	 * Determines longest string length out of all
	 * values to be written along y axis.
	 * @param list - list of values
	 * @param fm - font metrics
	 * @return longest string length 
	 */
	private int largestYValueStringWidth(List<XYValue> list, FontMetrics fm) {
		int maxWidth = 0;
		for (XYValue value : list) {
			int width = fm.stringWidth(String.valueOf(value.getY()));
			if (width > maxWidth) {
				maxWidth = width;
			}
		}
		return maxWidth;
	}
	
	/**
	 * Draws one line of the grid in specified color
	 * @param g - graphics
	 * @param x1 - start x coordinate
	 * @param y1 - start y coordinate
	 * @param x2 - end x coordinate
	 * @param y2 - end y coordinate
	 * @param color - color of the line
	 * @param vertical - indicates if line is vertical or horizontal
	 */
	private void drawGridLine(Graphics g, int x1, int y1, int x2, int y2, Color color, boolean vertical) {
		g.setColor(color);
		g.drawLine(x1, y1, x2, y2);
		g.setColor(Color.GRAY);
		
		
		if(vertical) {
			g.drawLine(x1, y1+TOP_INDENT/2,x1,y1);
		} else {
			g.drawLine(x1-RIGHT_INDENT/2, y1,x1,y1);
		}
	}
	
	/**
	 * Draws x axis arrow
	 * @param g - graphics
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	private void drawHorizontalArrow(Graphics g, int x, int y) {
		g.setColor(Color.GRAY);
		int[] xValues = {x, x+ARROW_LENGHT,x};
		int[] yValues = { y-ARROW_LENGHT/2,y,y+ARROW_LENGHT/2 };
		g.fillPolygon(xValues, yValues, 3);
	}
	
	/**
	 * Draws y axis arrow
	 * @param g - graphics
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	private void drawVerticalArrow(Graphics g, int x, int y) {
		g.setColor(Color.GRAY);
		int[] xValues = { x-ARROW_LENGHT/2, x, x+ARROW_LENGHT/2};
		int[] yValues = { y, y-ARROW_LENGHT, y};
		g.fillPolygon(xValues, yValues, 3);
	}
}
