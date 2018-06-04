package hr.fer.zemris.java.gui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.round;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Layout Manager used by simple calculators. Layout is designed as a grid 
 * with 5 rows and 7 columns where positions (1,2-5) are reserved for "display"
 * placed at position (1,1) and cannot be used by any other component.
 * Gap between components can be specified in constructor as positive integer which 
 * will be treated as number of pixels.
 * @author Josip Trbuscic
 *
 */
public class CalcLayout implements LayoutManager2 {

	/**
	 * Default gap between rows and columns in pixels
	 */
	private static final int DEFAULT_GAP = 0;

	/**
	 * Max row index
	 */
	private static final int MAX_ROWS = 5;
	
	/**
	 * Max column index
	 */
	private static final int MAX_COLS  = 7;

	/**
	 * 2D array indicating if cell is occupied by another component
	 */
	private boolean[][] occupied = new boolean[5][7];

	/**
	 * 2D array of components
	 */
	private Component[][] components = new Component[5][7];

	/**
	 * Minimum size of container managed by this layout manager
	 */
	private int minWidth = 0;
	private int minHeight = 0;
	

	/**
	 * Maximum size of container managed by this layout manager
	 */
	private int maxWidth = Integer.MAX_VALUE;
	private int maxHeight = Integer.MAX_VALUE;
	
	/**
	 * Preferred size of container managed by this layout manager
	 */
	private int preferredWidth = 0;
	private int preferredHeight = 0;
	
	/**
	 * Component gap in pixels
	 */
	private int gap;

	/**
	 * Constructs new {@code CalcLayout} with preferred gap between rows and columns
	 * in pixels
	 * 
	 * @param gap
	 *            - preferred gap between rows and columns in pixels
	 */
	public CalcLayout(int gap) {
		if (gap < 0)
			throw new CalcLayoutException("Preferred row and column gap cannot be negative");

		this.gap = gap;

		for (int i = 1; i < 5; ++i) {
			occupied[0][i] = true;
		}

	}

	/**
	 * Constructs new {@code CalcLayout} with default gap between rows and columns
	 * in pixels
	 */
	public CalcLayout() {
		this(DEFAULT_GAP);
	}

	
	/**
	 * Lays out the specified container.
	 * @param parent - container
	 */
	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int maxWidth = parent.getWidth() - (insets.left + insets.right);
		int maxHeight = parent.getHeight() - (insets.top + insets.bottom);
		int left = insets.left;
		int top = insets.top;

		double compHeight = (maxHeight - (MAX_ROWS - 1) * gap) / (double)MAX_ROWS;
		double compWidth = (maxWidth - (MAX_COLS - 1) * gap) / (double)MAX_COLS;

		for (int i = 0; i < MAX_ROWS; i++) {
			for (int j = 0; j < MAX_COLS; j++) {
				if (components[i][j] == null)
					continue;
				Component c = components[i][j];

				if (i == 0 && j == 0) {
					c.setBounds(left, top, (int)round(compWidth * 5 + gap * 4), (int)round(compHeight));
					continue;
				}
				c.setBounds( (int)round(left + j * (compWidth + gap)),
						(int)round(top + i * (compHeight + gap)),
						 (int)round(compWidth),  (int)round(compHeight));
			}
		}
	}

	/**
	 * Calculates the minimum size dimensions for the specified container.
	 * @param param - container 
	 * @return minimum dimensions required by specified container
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		setSizes(parent);
		parent.setMinimumSize(new Dimension(minWidth, minHeight));
		
		return addInsets(parent.getInsets(), minHeight, minWidth);
	}
	
	/**
	 * 
	 */
	@Override
	public Dimension maximumLayoutSize(Container parent) {
		setSizes(parent);
		parent.setMaximumSize(new Dimension(maxWidth, maxHeight));
		
		return addInsets(parent.getInsets(), maxHeight, maxWidth);
	}

	/**
	 * Calculates the preferred size dimensions for the specified container.
	 * @param param - container 
	 * @return preferred dimensions required by specified container
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		setSizes(parent);
		parent.setPreferredSize(new Dimension(preferredWidth, preferredHeight));
		
		return addInsets(parent.getInsets(), preferredHeight, preferredWidth);
	}

	/**
	 * Removes specified component from this layout manager.
	 * @param comp - component to be removed
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
		for (int i = 0; i < MAX_ROWS; i++) {
			for (int j = 0; j < MAX_COLS; j++) {
				if (components[i][j] == comp) {
					components[i][j] = null;
					occupied[i][j] = false;
				}
			}
		}

	}

	/**
	 * Adds specified component with given constraints to this
	 * layout manager
	 * @param comp - component to be added
	 * @param constraint - position constraint
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraint) {
		RCPosition rcconstraint;
		if (constraint instanceof RCPosition) {
			rcconstraint = (RCPosition) constraint;
		} else if (constraint instanceof String) {
			rcconstraint = parseConstraint((String) constraint);
		} else {
			throw new CalcLayoutException("Invalid type of constraint object");
		}

		if (!isValidConstraint(rcconstraint.getRow(), rcconstraint.getColumn()))
			throw new CalcLayoutException("Invalid constraint");

		components[rcconstraint.getRow() - 1][rcconstraint.getColumn() - 1] = comp;
		occupied[rcconstraint.getRow() - 1][rcconstraint.getColumn() - 1] = true;
	}

	/**
	 * Returns the alignment along the x axis.
	 * @param parent - container
	 */
	@Override
	public float getLayoutAlignmentX(Container parent) {
		return 0.5f;
	}

	/**
	 * Returns the alignment along the y axis.
	 * @param parent - container
	 */
	@Override
	public float getLayoutAlignmentY(Container parent) {
		return 0.5f;
	}

	/**
	 * Invalidates the layout, indicating that if the
	 * layout manager has cached information it should be discarded.
	 */
	@Override
	public void invalidateLayout(Container parent) {
	}
	
	/**
	 * Adds the component to the layout, associating
	 *  it with the string specified by name.
	 *  @param name - component name
	 *  @param comp - component to be added
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {}

	/**
	 * Returns {@code Dimension} representing size of the container 
	 * after subtracting its insets.
	 * @param insets - container insets
	 * @param height - container height
	 * @param width - container width
	 * @return size of the container after subtracting its insets
	 */
	private Dimension addInsets(Insets insets, int height, int width) {
		return new Dimension(width - insets.left - insets.right,
							height-insets.bottom -insets.top);
	}
	
	/**
	 * Sets preferred, minimum and maximum size of container. Size are calculated 
	 * by getting information from every component. If components do not specify 
	 * sizes zero will be set.
	 * @param parent
	 */
	private void setSizes(Container parent) {
		for (int i = 0; i < MAX_ROWS; i++) {
			for (int j = 0; j < MAX_COLS; j++) {
				if (!occupied[i][j] || isDisplayComponent(i, j)) continue;
				Component c = components[i][j];

				if (c.isVisible()) {
					Dimension dimPref = c.getPreferredSize();
					Dimension dimMin = c.getMinimumSize();
					Dimension dimMax = c.getMaximumSize();

					if (dimPref != null) {
						preferredHeight = max(preferredHeight, dimPref.height);
						if (i == 0 && j == 0) {
							preferredWidth = (dimPref.width - 4 * gap) / 5;
						} else {
							preferredWidth = max(preferredWidth, dimPref.width);
						}
					}
					
					if (dimMin != null) {
						minHeight = max(minHeight, dimMin.height);
						if (i == 0 && j == 0) {
							minWidth = max(minWidth,(dimMin.width - 4 * gap) / 5);
						} else {
							minWidth = max(minWidth, dimMin.width);
						}
					}
					
					if (dimMax != null) {
						maxHeight = min(maxHeight, dimMax.height);
						if (i == 0 && j == 0) {
							maxWidth = min(maxWidth,(dimMax.width - 4 * gap) / 5);
						} else {
							maxWidth = min(maxWidth, dimMax.width);
						}
					}
				}
			}
		}
		
		preferredHeight = preferredHeight * MAX_ROWS + (MAX_ROWS - 1) * gap;
		preferredWidth = preferredWidth * MAX_COLS + (MAX_COLS - 1) * gap;
		
		minHeight = minHeight * MAX_ROWS + (MAX_ROWS - 1) * gap;
		minWidth = minWidth * MAX_COLS + (MAX_COLS - 1) * gap;
		
		maxHeight = maxHeight * MAX_ROWS + (MAX_ROWS - 1) * gap;
		maxWidth = maxWidth * MAX_COLS + (MAX_COLS - 1) * gap;
	}

	/**
	 * Checks if given row and column indexes are valid. Constraint is 
	 * valid if cell is not occupied by another component or is not 
	 * one of the cells reserved for the "display" component
	 * @param row - row index
	 * @param column - column index
	 * @return true if indexes are valid, false otherwise
	 */
	private boolean isValidConstraint(int row, int column) {
		if (row < 1 || row > 5)
			return false;
		if (column < 1 || column > 7)
			return false;

		return !occupied[row - 1][column - 1];
	}

	/**
	 * Checks if given row and column indexes represent 
	 * one of the cells reserved for the "display" component
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean isDisplayComponent(int row, int column) {
		if (row != 0)
			return false;
		if (column == 0 || column == 5 || column == 6)
			return false;
		return true;
	}

	/**
	 * Returns a new {@code RCPosition} initialized to the value represented by
	 * the specified string.
	 * @param constraint - string to parse
	 * @return {@code RCPosition} represented by the argument
	 * @throws IllegalArgumentException if given string does not represent valid {@code RCPosition}
	 */
	private static RCPosition parseConstraint(String constraint) {
		Pattern pattern = Pattern.compile("^\\s*(\\d+)\\s*,\\s*(\\d+)\\s*$");
		Matcher matcher = pattern.matcher(constraint);

		if (matcher.find()) {
			return new RCPosition(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
		} else {
			throw new IllegalArgumentException("Invalid constraint");
		}
	}

}
