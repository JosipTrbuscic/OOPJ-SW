package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;
/**
 * Functional interface used for modeling commands.
 * @author Josip Trbuscic
 *
 */
public interface Command {
	
	/**
	 * Method that represents execution of command which
	 * implements this interface
	 * @param ctx - context which contains {@code TurtleState}s
	 * @param painter - object used for painting
	 */
	void execute(Context ctx, Painter painter);
}
