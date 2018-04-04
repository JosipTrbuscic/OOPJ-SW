package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.math.Vector2D;

/**
 * Implementation of {@code Command} interface used for 
 * drawing a line using a instance of {@code Painter}
 * 
 * @author Josip Trbuscic
 *
 */
public class DrawCommand implements Command{
	
	/**
	 *length of a line 
	 */
	private double step;
	
	/**
	 * Constructs new {@code DrawCommand} with given length
	 * @param step
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	/**
	 * Draws a line from current turtle position in a current direction 
	 * @param ctx - {@code Context} containing turtle states
	 * @param painter which paints the picture 
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		double length = ctx.getCurrentState().getUnitLength()*step;
		double angle = ctx.getCurrentState().getDirection().getAngle();
		
		Color color = ctx.getCurrentState().getColor();
		
		Vector2D  currentPos = ctx.getCurrentState().getPosition().copy();
		Vector2D translator = new Vector2D(length*Math.cos(angle), length*Math.sin(angle));
		
		ctx.getCurrentState().setPosition(
				ctx.getCurrentState().getPosition().translated(translator));
		
		painter.drawLine(
						currentPos.getX(),
						currentPos.getY(),
						ctx.getCurrentState().getPosition().getX(),
						ctx.getCurrentState().getPosition().getY(),
						color,
						(float)0.5);
		
	}

}
