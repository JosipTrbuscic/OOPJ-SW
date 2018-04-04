package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.math.Vector2D;

public class SkipCommand implements Command{
	private double step;
	
	public SkipCommand(double step) {
		this.step = step;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		double length = ctx.getCurrentState().getUnitLength()*step;
		double angle = ctx.getCurrentState().getDirection().getAngle();
		
		Vector2D translator = new Vector2D(length*Math.cos(angle), length*Math.sin(angle));
		
		ctx.getCurrentState().setPosition(
				ctx.getCurrentState().getPosition().translated(translator));
	
	}

}
