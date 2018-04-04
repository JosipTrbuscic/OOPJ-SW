package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Implementation of {@code Command} interface used for changing 
 * color of current {@code TurtleState}
 * @author Josip Trbuscic
 *
 */
public class ColorCommand implements Command {

	/**
	 * Color to be set
	 */
	private Color color;

	/**
	 * Constructs new {@code ColorCommand} with given color argument
	 * @param color
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	/**
	 * Sets current {@code TurtleState}s color
	 * @param ctx - {@code Context} containing turtle states
	 * @param  painter which paints the picture 
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}

}
