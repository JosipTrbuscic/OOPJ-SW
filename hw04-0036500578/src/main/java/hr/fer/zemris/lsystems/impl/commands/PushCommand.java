package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;

import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Implementation of {@code Command} interface used for
 * adding {@code TurtleState} to the top of
 * {@code Context} stack
 * 
 * @author Josip Trbuscic
 *
 */
public class PushCommand implements Command {
	
	/**
	 * Makes the copy of current {@code TurtleState} from top of the stack
	 * and pushes it back on top of the stack. After this operation context
	 * will contain two equal states on the stack.
	 * @param ctx - {@code Context} containing turtle states
	 * @param painter which paints the picture 
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());
		
	}

}
