package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

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

/**
 * Implementation of {@code LSystemBuilder} interface which builds the L system
 * from given configuration. L system is built by generating a string of commands
 * which are executed one by one. Each command keyword is mapped with its value in 
 * instance of {@code Dictionary}. configurations can be parsed from file or given
 * through offered methods
 * 
 * @author Josip Trbuscic
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	/**
	 * collection for storing keyword-command pairs
	 */
	private Dictionary commands = new Dictionary();;
	
	/**
	 * collection for storing keyword-production pairs
	 */
	private Dictionary productions = new Dictionary();;
	
	/**
	 * length of unit initially set to 0.1
	 */
	private double unitLength = 0.1;
	
	/**
	 * scaling factor for unit length initially set to 1
	 */
	private double unitLengthScaler = 1;
	
	/**
	 * origin point of L system initially set at(0,0)
	 */
	private Vector2D origin = new Vector2D(0, 0);

	/**
	 * direction of turtle initially set to 0
	 */
	private double angle = 0;
	
	/**
	 * L system axiom initially set to empty string
	 */
	private String axiom = "";

	/**
	 * This method will build new L system by creating 
	 * instance of it's inner class 
	 * @returns new L system
	 */
	@Override
	public LSystem build() {
		
		/**
		 * This class defines method for generating string of 
		 * commands for given level which will be executed 
		 * and method for drawing L system by executing every
		 * command from generated string
		 */
		class MyLSystem implements LSystem {

			/**
			 * Creates new {@code Context} where new {@code TurtleState}
			 * will be added and draws new L system by executing every 
			 * command from string generated for given level
			 * @param level - depth of system
			 * @param  painter which paints the picture 
			 */
			@Override
			public void draw(int level, Painter painter) {
				if (level < 0)
					throw new IllegalArgumentException("Level cannot be negative");

				if (painter == null)
					throw new NullPointerException("Painter cannot be null");
				// new context, unit vector and initial state
				Context ctx = new Context();
				Vector2D unitVector = new Vector2D(Math.cos(angle / 180 * Math.PI), Math.sin(angle / 180 * Math.PI));
				TurtleState initialState = new TurtleState(origin, unitVector, Color.BLACK,
						unitLength * Math.pow(unitLengthScaler, level));

				ctx.pushState(initialState);
				// generating string for given level and executing commands
				String product = generate(level);
				char[] instructions = product.toCharArray();
				for (char instruction : instructions) {
					Command com = (Command) commands.get(String.valueOf(instruction));

					if (com != null) {
						com.execute(ctx, painter);
					}
				}

			}
			
			/**
			 * Generates string of commands for given level by
			 * applying production rules
			 * @param level - depth of L system
			 * @returns string of commands
			 */
			@Override
			public String generate(int level) {
				if (level == 0) return axiom;
				String product = axiom;

				for (int i = level; i > 0; i--) {
					StringBuilder productOnCurrentLevel = new StringBuilder();
					char[] productArray = product.toCharArray();

					for (char znak : productArray) {
						String production = (String) productions.get(String.valueOf(znak));

						if (production != null) {
							productOnCurrentLevel.append(production);
						} else {
							productOnCurrentLevel.append(String.valueOf(znak));
						}
					}

					product = productOnCurrentLevel.toString();
				}

				return product;
			}

		}

		return new MyLSystem();
	}

	/**
	 * Parses given strings and configures 
	 * L system from given configuration
	 * @param stringParts lines of text
	 * @returns L system configuration 
	 * @throws IllegalArgumentException if configuration is not valid
	 */
	@Override
	public LSystemBuilder configureFromText(String[] stringParts) {
		for (String s : stringParts) {
			if (s.isEmpty()) continue;

			String[] parts = s.split("\\s+");
			try {
				switch (parts[0]) {

				case "origin":
					if (parts.length != 3)
						throw new IllegalArgumentException("Invalid origin arguments");

					setOrigin(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
					break;

				case "angle":
					if (parts.length != 2)
						throw new IllegalArgumentException("Invalid angle arguments");

					setAngle(Double.parseDouble(parts[1]));
					break;

				case "unitLength":
					if (parts.length != 2)
						throw new IllegalArgumentException("Invalid unit length arguments");

					setUnitLength(Double.parseDouble(parts[1]));
					break;
				case "unitLengthDegreeScaler":
					if (parts.length < 2)
						throw new IllegalArgumentException("Invalid unit length degree scaler arguments");

					Double value = parseUnitLengthDegreeScalerValue(join(parts, 1, parts.length));

					setUnitLengthDegreeScaler(value);
					break;

				case "command":
					if (parts.length < 3)
						throw new IllegalArgumentException("Invalid command arguments");

					if (parts[1].length() > 1)
						throw new IllegalArgumentException("'" + parts[1] + "'" + " is invalid command keyword");

					registerCommand(parts[1].charAt(0), join(parts, 2, parts.length));
					break;

				case "axiom":
					if (parts.length != 2)
						throw new IllegalArgumentException("Invalid axiom arguments");

					setAxiom(parts[1]);
					break;

				case "production":
					if (parts.length != 3)
						throw new IllegalArgumentException("Invalid production arguments");

					if (parts[1].length() > 1)
						throw new IllegalArgumentException("'" + parts[1] + "'" + " is invalid production keyword");

					registerProduction(parts[1].charAt(0), parts[2]);
					break;

				default:
					throw new IllegalArgumentException(parts[0] + " is invalid keyword.");
				}
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			}
		}

		return this;
	}

	/**
	 * Parses the string and adds new keyword-command to 
	 * collection
	 * @param symbol - command key
	 * @param action - command to parse
	 * @throws IllegalArgumentException if command cannot be parsed
	 * @return this L system
	 */
	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		try {
			commands.put(String.valueOf(symbol), parseAction(action.trim()));
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}

		return this;
	}

	/**
	 * Parses the string and adds new keyword-production to 
	 * collection
	 * @param symbol - production key
	 * @param action - production to parse
	 * @throws IllegalArgumentException if command cannot be parsed
	 * @return this L system
	 */
	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		productions.put(String.valueOf(symbol), production);

		return this;
	}

	/**
	 * Sets angle of initial state of turtle.
	 * Angel is given in degrees
	 * @param angle
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	/**
	 * Sets axiom to given value
	 * @param axiom
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	/**
	 * Sets new origin for L system
	 * both coordinates must be in range (0,1)
	 * @param x coordinate
	 * @param y coordinate
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		if ((x < 0 || x > 1) || (y < 0 || y > 1))
			throw new IllegalArgumentException("Invalid origin coordinate");

		origin = new Vector2D(x, y);
		return this;
	}

	/**
	 * Sets unit length to a given value
	 * @param unit length
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		if (unitLength <= 0 || unitLength > 1)
			throw new IllegalArgumentException("Unit length out of range");

		this.unitLength = unitLength;
		return this;
	}

	/**
	 * sets new unit length scaler
	 * @param scaler
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double scaler) {

		this.unitLengthScaler = scaler;
		return this;
	}

	/**
	 * Parses given string as {@code Command}
	 * @param action - string to be parsed
	 * @return new command
	 * @throws IllegalArgumentException if action cannot be parsed
	 */
	private Command parseAction(String action) {
		String[] parts = action.split("\\s+");

		switch (parts[0]) {
		case "draw":
			if (parts.length != 2)
				throw new IllegalArgumentException("Invalid draw action arguments");

			return new DrawCommand(Double.parseDouble(parts[1]));

		case "rotate":
			if (parts.length != 2)
				throw new IllegalArgumentException("Invalid rotate action arguments");

			return new RotateCommand(Double.parseDouble(parts[1]));

		case "skip":
			if (parts.length != 2)
				throw new IllegalArgumentException("Invalid skip action arguments");

			return new SkipCommand(Double.parseDouble(parts[1]));

		case "scale":
			if (parts.length < 3)
				throw new IllegalArgumentException("Invalid Unit length degree arguments");

			Double value = parseUnitLengthDegreeScalerValue(join(parts, 1, parts.length));

			return new ScaleCommand(value);

		case "push":
			return new PushCommand();

		case "pop":
			return new PopCommand();

		case "color":
			if (parts.length != 2)
				throw new IllegalArgumentException("Invalid color arguments");

			return new ColorCommand(new Color(
					Integer.valueOf(parts[1].substring(0, 2), 16),
					Integer.valueOf(parts[1].substring(2, 4), 16),
					Integer.valueOf(parts[1].substring(4, 6), 16)));

		default:
			throw new IllegalArgumentException("Can't parse given command. " + action);
		}

	}

	/**
	 * Parses unit length degree scaler expression
	 * @param value - string to be parsed
	 * @return {@code Double} value of the scaler
	 * @throws IllegalArgumentException if string cannot be parsed
	 */
	private Double parseUnitLengthDegreeScalerValue(String value) {
		if (value.contains("/")) {
			String[] parts = value.split("/");
			if (parts.length != 2)
				throw new IllegalArgumentException("'" + value + "'" + " is invalid un it degree scaler ");

			return Double.parseDouble(parts[0].trim()) / Double.parseDouble(parts[1].trim());
		} else {
			return Double.parseDouble(value);
		}

	}

	/**
	 * Joins parts of string array
	 * from start to end-1 indexes
	 * @param parts - array of strings
	 * @param start - starting index
	 * @param end - end index
	 * @return
	 */
	private String join(String[] parts, int start, int end) {
		String result = "";

		for (int i = start; i < end; ++i) {
			result += " " + parts[i];
		}

		return result.trim();
	}

}
