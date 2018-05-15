package hr.fer.zemris.java.gui.layout;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayoutInfo;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class CalcLayout implements LayoutManager2 {

	/**
	 * Default gap between rows and columns in pixels
	 */
	private static final int DEFAULT_GAP = 0;

	private static final Dimension GRID = new Dimension(7, 5);

	private boolean[][] occupied = new boolean[5][7];

	private Component[][] components = new Component[5][7];

	private int minWidth = 0;
	private int minHeight = 0;
	private int maxWidth = 0;
	private int maxHeight = 0;
	private int preferredWidth = 0;
	private int preferredHeight = 0;
	private boolean sizeUnknown = true;

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

	@Override
	public void addLayoutComponent(String name, Component comp) {}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int maxWidth = parent.getWidth() - (insets.left + insets.right);
		int maxHeight = parent.getHeight() - (insets.top + insets.bottom);
		int left = insets.left;
		int top = insets.top;

		if (sizeUnknown) {
			setSizes(parent);
		}

		int compHeight = (maxHeight - (GRID.height - 1) * gap) / GRID.height;
		int compWidth = (maxWidth - (GRID.width - 1) * gap) / GRID.width;

		for (int i = 0; i < GRID.height; i++) {
			for (int j = 0; j < GRID.width; j++) {
				if (components[i][j] == null)
					continue;
				Component c = components[i][j];

				if (i == 0 && j == 0) {
					c.setBounds(left, top, compWidth * 5 + gap * 4, compHeight);
					continue;
				}
				c.setBounds(left + j * (compWidth + gap), top + i * (compHeight + gap), compWidth, compHeight);
			}
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);

		setSizes(parent);

		Insets insets = parent.getInsets();
		dim.width = minWidth + insets.left + insets.right;
		dim.height = minHeight + insets.top + insets.bottom;

		sizeUnknown = false;

		return dim;
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);

		setSizes(parent);

		Insets insets = parent.getInsets();
		dim.width = preferredWidth + insets.left + insets.right;
		dim.height = preferredHeight + insets.top + insets.bottom;

		sizeUnknown = false;

		return dim;
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		for (int i = 0; i < GRID.height; i++) {
			for (int j = 0; j < GRID.width; j++) {
				if (components[i][j] == comp) {
					components[i][j] = null;
				}
			}
		}

	}

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

	@Override
	public float getLayoutAlignmentX(Container parent) {
		return 0.5f;
	}

	@Override
	public float getLayoutAlignmentY(Container parent) {
		return 0.5f;
	}

	@Override
	public void invalidateLayout(Container parent) {
	}

	@Override
	public Dimension maximumLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);

		setSizes(parent);

		Insets insets = parent.getInsets();
		dim.width = maxWidth + insets.left + insets.right;
		dim.height = maxHeight + insets.top + insets.bottom;

		sizeUnknown = false;

		return dim;
	}

	private void setSizes(Container parent) {
		setPreferredSize(parent);
		setMinimumSize(parent);
		setMaximumSize(parent);
	}

	private void setPreferredSize(Container parent) {
		Dimension dim = null;
		int maxPreferredHeight = 0;
		int maxPreferredWidth = 0;

		for (int i = 0; i < GRID.height; i++) {
			for (int j = 0; j < GRID.width; j++) {
				if (!occupied[i][j] || isFirstComponent(i, j))
					continue;
				Component c = components[i][j];

				if (c.isVisible()) {
					dim = c.getPreferredSize();
					if (dim == null)
						continue;

					int height = dim.height;
					int width;

					if (i == 0 && j == 0) {
						width = (dim.width - 4 * gap) / 5;
					} else {
						width = dim.width;
					}

					if (height > maxPreferredHeight) {
						maxPreferredHeight = height;
					}
					if (width > maxPreferredWidth) {
						maxPreferredWidth = width;
					}
				}
			}
		}
		preferredHeight = maxPreferredHeight * GRID.height + (GRID.height - 1) * gap;
		preferredWidth = maxPreferredWidth * GRID.width + (GRID.width - 1) * gap;
	}

	private void setMinimumSize(Container parent) {
		Dimension dim = null;
		int maxMinimumHeight = 0;
		int maxMinimumWidth = 0;

		for (int i = 0; i < GRID.height; i++) {
			for (int j = 0; j < GRID.width; j++) {
				if (!occupied[i][j] || isFirstComponent(i, j))
					continue;
				Component c = components[i][j];

				if (c.isVisible()) {
					dim = c.getMinimumSize();
					if (dim == null)
						continue;

					int height = dim.height;
					int width;

					if (i == 0 && j == 0) {
						width = (dim.width - 4 * gap) / 5;
					} else {
						width = dim.width;
					}

					if (height > maxMinimumHeight) {
						maxMinimumHeight = height;
					}
					if (width > maxMinimumWidth) {
						maxMinimumWidth = width;
					}
				}
			}
		}
		minHeight = maxMinimumHeight * GRID.height + (GRID.height - 1) * gap;
		minWidth = maxMinimumWidth * GRID.width + (GRID.width - 1) * gap;
	}

	private void setMaximumSize(Container parent) {
		Dimension dim = null;
		int minMaximumHeight = 0;
		int minMaximumWidth = 0;

		for (int i = 0; i < GRID.height; i++) {
			for (int j = 0; j < GRID.width; j++) {
				if (!occupied[i][j] || isFirstComponent(i, j))
					continue;
				Component c = components[i][j];

				if (c.isVisible()) {
					dim = c.getMinimumSize();
					if (dim == null)
						continue;

					int height = dim.height;
					int width;

					if (i == 0 && j == 0) {
						width = (dim.width - 4 * gap) / 5;
					} else {
						width = dim.width;
					}

					if (height < minMaximumHeight) {
						minMaximumHeight = height;
					}
					if (width < minMaximumWidth) {
						minMaximumWidth = width;
					}
				}
			}
		}
		minHeight = minMaximumHeight * GRID.height + (GRID.height - 1) * gap;
		minWidth = minMaximumWidth * GRID.width + (GRID.width - 1) * gap;
	}

	private boolean isValidConstraint(int row, int column) {
		if (row < 1 || row > 5)
			return false;
		if (column < 1 || column > 7)
			return false;

		return !occupied[row - 1][column - 1];
	}

	private boolean isFirstComponent(int x, int y) {
		if (x != 0)
			return false;
		if (y == 0 || y == 5 || y == 6)
			return false;
		return true;
	}

	private static RCPosition parseConstraint(String constraint) {
		Pattern pattern = Pattern.compile("^\\s*(\\d+)\\s*,\\s*(\\d+)\\s*$");
		Matcher matcher = pattern.matcher(constraint);

		if (matcher.find()) {
			return new RCPosition(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
		} else {
			throw new IllegalArgumentException("Invalid constraint");
		}
	}

//	static class Prozor4 extends JFrame {
//
//		private static final long serialVersionUID = 1L;
//
//		public Prozor4() {
//			super();
//			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//			setTitle("Prozor1");
//			setLocation(20, 20);
//			setSize(500, 200);
//			initGUI();
//
//		}
//
//		private void initGUI() {
//			Container cp = getContentPane();
//			cp.setLayout(new BorderLayout());
//			JPanel p = new JPanel(new CalcLayout(3));
//
//			p.add(new JButton("x"), "1,1");
//			p.add(new JButton("y"), "2,3");
//			p.add(new JButton("z"), "2,7");
//			p.add(new JButton("w"), "4,2");
//			p.add(new JButton("a"), "4,5");
//			p.add(new JButton("b"), "4,7");
//			cp.add(p, BorderLayout.CENTER);
//
//		}
//
//	}
//
//	public static void main(String[] args) {
//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				Prozor4 prozor = new Prozor4();
//				prozor.setVisible(true);
//			}
//		});
//	}

}
