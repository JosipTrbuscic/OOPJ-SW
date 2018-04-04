package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.lang.reflect.Array;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

public class LSystemBuilderImpl implements LSystemBuilder {
	private Dictionary commands;
	private Dictionary productions;

	private double unitLength = 0.1;
	private double unitLengthScaler = 1;
	private Vector2D origin = new Vector2D(0, 0);
	private double angle = 0;
	private String axiom = "";

	public LSystemBuilderImpl() {
		commands = new Dictionary();
		productions = new Dictionary();
	}

	@Override
	public LSystem build() {
		class MyLSystem implements LSystem {
			public MyLSystem() {
			}

			@Override
			public void draw(int level, Painter painter) {
				if (level < 0)
					throw new IllegalArgumentException("Level cannot be negative");
				if(painter == null) throw new NullPointerException("Painter cannot be null");
				
				Context ctx = new Context();
				Vector2D unitVector = new Vector2D(Math.cos(angle/180*Math.PI), Math.sin(angle/180*Math.PI));
				TurtleState initialState = new TurtleState(origin,
															unitVector,
															Color.BLACK,
															unitLength * Math.pow(unitLengthScaler, level));
				
				ctx.pushState(initialState);
				
				String product = generate(level);
				char[] instructions = product.toCharArray();
				for (char instruction : instructions) {
					Command com = (Command) commands.get(String.valueOf(instruction));
					
					if(com != null) {
						com.execute(ctx, painter);
					}
				}

			}

			@Override
			public String generate(int level) {
				if (level == 0) return axiom;
				String product = axiom;
			
				for (int i = level; i > 0; i--) {
					StringBuilder result =new StringBuilder();
					char[] poljeZnakova=product.toCharArray();
					
					for(char znak:poljeZnakova) {
						String akcija = (String) productions.get(String.valueOf(znak));
						
						if(akcija != null) {
							result.append(akcija);
						}else {
							result.append(String.valueOf(znak));
						}
					}
					product = result.toString();
				}
				
				return product;
			}

		}
		return new MyLSystem();
	}

	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		for (String s : arg0) {
			if (s.isEmpty())
				continue;

			String[] parts = s.split("\\s+");

			if (parts[0].equals("origin")) {
				setOrigin(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));

			} else if (parts[0].equals("angle")) {
				setAngle(Double.parseDouble(parts[1]));

			} else if (parts[0].equals("unitLength")) {
				setUnitLength(Double.parseDouble(parts[1]));

			} else if (parts[0].equals("unitLengthDegreeScaler")) {
				setUnitLengthDegreeScaler(Double.parseDouble(parts[1]) / Double.parseDouble(parts[3]));

			} else if (parts[0].equals("command")) {
				registerCommand(parts[1].charAt(0), join(parts, 2, parts.length, true));

			} else if (parts[0].equals("axiom")) {
				setAxiom(parts[1]);

			} else if (parts[0].equals("production")) {
				registerProduction(parts[1].charAt(0), parts[2]);

			} else {
				throw new IllegalArgumentException(parts[0] + " is invalid keyword.");
			}

		}
		return this;
	}

	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		commands.put(String.valueOf(symbol), parseAction(action));
		return this;
	}

	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		productions.put(String.valueOf(symbol), production);

		return this;
	}

	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		if ((arg0 < 0 || arg0 > 1) || (arg1 < 0 || arg1 > 1))
			throw new IllegalArgumentException("Invalid origin coordinate");

		origin = new Vector2D(arg0, arg1);
		return this;
	}

	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		if (unitLength <= 0 || unitLength > 1)
			throw new IllegalArgumentException("Unit length out of range");

		this.unitLength = unitLength;
		return this;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {

		this.unitLengthScaler = arg0;
		return this;
	}

	private Command parseAction(String action) {
		String[] parts = action.split("\\s+");

		if (parts[0].equals("draw")) {
			return new DrawCommand(Double.parseDouble(parts[1]));

		} else if (parts[0].equals("rotate")) {
			return new RotateCommand(Double.parseDouble(parts[1]));

		} else if (parts[0].equals("skip")) {
			return new SkipCommand(Double.parseDouble(parts[1]));

		} else if (parts[0].equals("scale")) {
			return new ScaleCommand(Double.parseDouble(parts[1]) / Double.parseDouble(parts[3]));

		} else if (parts[0].equals("push")) {
			return new PushCommand();

		} else if (parts[0].equals("pop")) {
			return new PopCommand();

		} else if (parts[0].equals("color")) {
			return new ColorCommand(new Color(
											Integer.valueOf(parts[1].substring(0, 2), 16),
											Integer.valueOf(parts[1].substring(2, 4), 16),
											Integer.valueOf(parts[1].substring(4, 6), 16)));

		} else {
			throw new IllegalArgumentException("Can't parse given command. " + action);
		}

	}

	private String join(String[] parts, int start, int end, boolean spaces) {
		String result = "";

		for (int i = start; i < end; ++i) {
			if(spaces) {
				result += " " + parts[i];
			} else {
				result +=parts[i];
			}
		}

		return result.trim();
	}

}
