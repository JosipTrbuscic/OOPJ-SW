package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.math.Vector2D;

public class DrawCommand implements Command{
	private double step;
	
	public DrawCommand(double step) {
		this.step = step;
	}

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
