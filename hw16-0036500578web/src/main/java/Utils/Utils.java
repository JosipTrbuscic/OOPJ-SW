package Utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Action;

import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.Circle;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.FilledCircle;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.FilledPolygon;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.GeometricalObject;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.Line;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.Vector3;

public class Utils {
	/**
	 * Parses lines of .jvd file and creates new geometric
	 * object from each parsed line
	 * @param lines - lines of .jvd file
	 * @return list of created objects
	 */
	public static List<GeometricalObject> parseLines(List<String> lines) {
		List<GeometricalObject> objects = new LinkedList<>();
		
		for(String line : lines) {
			try {
				String[] parts = line.split("\\s+");
				String name = parts[0];
				switch(name) {
				case"LINE":
					objects.add(new Line(Integer.parseInt(parts[1]),
							Integer.parseInt(parts[2]),
							Integer.parseInt(parts[3]),
							Integer.parseInt(parts[4]),
							new Color(Integer.parseInt(parts[5]),
									Integer.parseInt(parts[6]), 
									Integer.parseInt(parts[7])
									)
							));
					break;
				case"CIRCLE":
					objects.add(new Circle(Integer.parseInt(parts[1]),
							Integer.parseInt(parts[2]),
							Integer.parseInt(parts[3]),
							new Color(Integer.parseInt(parts[4]),
									Integer.parseInt(parts[5]), 
									Integer.parseInt(parts[6])
									)
							));
					break;
				case"FCIRCLE":
					objects.add(new FilledCircle(Integer.parseInt(parts[1]),
							Integer.parseInt(parts[2]),
							Integer.parseInt(parts[3]),
							new Color(Integer.parseInt(parts[4]),
									Integer.parseInt(parts[5]), 
									Integer.parseInt(parts[6])
									),
							new Color(Integer.parseInt(parts[7]),
									Integer.parseInt(parts[8]), 
									Integer.parseInt(parts[9])
									)
							));
					break;
				case"FPOLY":
					List<Vector3> points = new ArrayList<>();
					for(int i = 2; i < parts.length-7;i+=2) {
						points.add(new Vector3(Double.parseDouble(parts[i]),
								Double.parseDouble(parts[i+1]),
								0));
					}
					int length = parts.length;
					objects.add(new FilledPolygon(points,
							new Color(Integer.parseInt(parts[length-6]),
									Integer.parseInt(parts[length-5]), 
									Integer.parseInt(parts[length-4])
									),
							new Color(Integer.parseInt(parts[length-3]),
									Integer.parseInt(parts[length-2]), 
									Integer.parseInt(parts[length-1])
									)
							));
					break;
				}
			}catch(Exception ignore) {
			}
		}
		return objects;
	}
}
