package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
/**
 * Implementation of {@code Command} interface used for
 * changing direction of turtle
 * @author Josip Trbuscic
 *
 */
public class RotateCommand implements Command {
	/**
	 * angle of rotation
	 */
	private double angle;
	
	/**
	 * Constructs new {@code RotateCommand} with given angle
	 * @param angle
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}
	
	/**
	 * Changes the current direction of turtle by
	 * rotating its vector 
	 * @param ctx - {@code Context} containing turtle states
	 * @param painter which paints the picture 
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().getDirection().rotate(angle);
	}

}
