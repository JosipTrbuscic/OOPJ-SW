package hr.fer.zemris.java.gui.layout;

/**
 * This class represents constraint used by {@link CalcLayout} for specifying 
 * where components should be added to the layout.
 * @author Josip Trbuscic
 *
 */
public class RCPosition {
	
	/**
	 * Row index
	 */
	private int row;
	
	/**
	 * Column index
	 */
	private int column;
	
	/**
	 * Constructor
	 * @param row - row index
	 * @param column - column index
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Returns row index
	 * @return row index
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns column index
	 * @return column index
	 */
	public int getColumn() {
		return column;
	}
	
	
}
