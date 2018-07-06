package hr.fer.zemris.java.hw16.jdraw.geometricalObjects;

/**
 * Interface which defines methods which are implemented 
 * by classes who wish to perform action on specific 
 * geometric object 
 * @author Josip Trbuscic
 *
 */
public interface GeometricalObjectVisitor {
	
	/**
	 * Action performed on specified line
	 * @param line - visited line
	 */
	public abstract void visit(Line line);
	  
	  /**
	   * Action performed on specified circle
	   * @param circle - visited circle
	   */
	public abstract void visit(Circle circle);
	
	/**
	 * Action performed on specified filled circle
	 * @param filledCircle - visited filled circle
	 */
	public abstract void visit(FilledCircle filledCircle);
}
