package hr.fer.zemris.java.gui.charts;

/**
 * Class containing two integer values that 
 * represent coordinates in a coordinate system 
 * @author Josip Trbuscic
 *
 */
public class XYValue implements Comparable<XYValue>{
	/**
	 * X coordinate
	 */
	private int x;
	
	/**
	 * Y coordinate
	 */
	private int y;
	
	/**
	 * Constructor
	 * @param x - x value
	 * @param y - y value
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns x value
	 * @return x value
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns y value
	 * @return y value
	 */
	public int getY() {
		return y;
	}

	/**
	 * Compares this object with the specified object for order.
	 *  Returns a negative integer, zero, or a positive integer
	 *  as this object is less than, equal to, or greater than the specified object. 
	 */
	@Override
	public int compareTo(XYValue other) {
		return Integer.compare(x, other.getX());
	}
}
