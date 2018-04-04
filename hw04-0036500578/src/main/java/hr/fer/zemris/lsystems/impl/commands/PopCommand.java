package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
/**
 * Implementation of {@code Command} interface used for
 * removing {@code TurtleState} from top of
 * the {@code Context} stack
 * 
 * @author Josip Trbuscic
 *
 */
public class PopCommand implements Command{
	
	/**
	 * Removes the first {@code TurtleState} from top of the stack
	 * in context. 
	 * @param ctx - {@code Context} containing turtle states
	 * @param painter which paints the picture 
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
		
	}
	
	

}
