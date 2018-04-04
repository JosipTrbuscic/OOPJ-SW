package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.math.Vector2D;

/**
 * Implementation of {@code Command} interface used for 
 * changing current position of turtle
 * 
 * @author Josip Trbuscic
 *
 */
public class SkipCommand implements Command{
	
	/**
	 *length of a line 
	 */
	private double step;
	
	/**
	 * Constructs new {@code SkipCommand} with given length
	 * @param step
	 */
	public SkipCommand(double step) {
		this.step = step;
	}
	
	/**
	 * Changes the current position in {@code TurtleState}
	 * @param ctx - {@code Context} containing turtle states
	 * @param painter which paints the picture 
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		double length = ctx.getCurrentState().getUnitLength()*step;
		double angle = ctx.getCurrentState().getDirection().getAngle();
		
		Vector2D translator = new Vector2D(length*Math.cos(angle), length*Math.sin(angle));
		
		ctx.getCurrentState().setPosition(
				ctx.getCurrentState().getPosition().translated(translator));
	
	}

}
