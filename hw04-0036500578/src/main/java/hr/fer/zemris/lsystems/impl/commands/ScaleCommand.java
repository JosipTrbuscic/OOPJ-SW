package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Implementation of {@code Command} interface used for
 * scaling {@code UnitLength} of current {@code TurtleState}
 * 
 * @author Josip Trbuscic
 *
 */
public class ScaleCommand implements Command {
	/**
	 * scaling factor
	 */
	private double factor;
	
	/**
	 * Constructs new {@code ScaleCommand} with given angle
	 * @param factor to multiply with
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}
	
	/**
	 * Changes the current unit length
	 * @param ctx - {@code Context} containing turtle states
	 * @param painter which paints the picture 
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setUnitLength(ctx.getCurrentState().getUnitLength()*factor);
	}
}
