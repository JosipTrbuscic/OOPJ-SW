package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Implementation of a servlet which processes HTTP GET request and calculates
 * sin and cos value of every angle(in degrees) defined by integer value in a
 * range whose bounds are defined by arguments specified by client. Valid
 * arguments are any integer values, if lower bound is not specified it will
 * default to 0 while upper bound will default to 360.
 * 
 * @author Josip Trbuscic
 *
 */
@WebServlet("/trigonometric")
public class Trigonometric extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Lower bound default value
	 */
	private static final int DEFAULT_LOWER_BOUND = 0;
	
	/**
	 * Upper bound default value
	 */
	private static final int DEFAULT_UPPER_BOUND = 360;
	
	/**
	 * Max range of values
	 */
	private static final int MAX_DIFF = 720;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String aString = req.getParameter("a");
		String bString = req.getParameter("b");
		Integer a = DEFAULT_LOWER_BOUND;
		Integer b = DEFAULT_UPPER_BOUND;

		try {
			a = Integer.parseInt(aString);
			b = Integer.parseInt(bString);
		} catch (NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid bound value");
			return;
		} catch (NullPointerException ignorable) {}

		
		if (a > b) {
			int temp = a;
			a = b;
			b = temp;
		}

		if (b > a + MAX_DIFF) {
			b = a + MAX_DIFF;
		}

		List<AngleAndTrigValues> list = new ArrayList<>();
		for (int i = a; i <= b; i++) {
			list.add(new AngleAndTrigValues(i, Math.sin(Math.toRadians(i)), Math.cos(Math.toRadians(i))));
		}

		req.setAttribute("values", list);
		req.getRequestDispatcher("WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}

	/**
	 * Simple class containing angle value and its sin and cos value
	 * @author Josip Trbuscic
	 *
	 */
	public static class AngleAndTrigValues {
		private int angle;
		private double sin;
		private double cos;

		/**
		 * constructor
		 * @param angle - angle value
		 * @param sin - sin value
		 * @param cos - cos value
		 */
		public AngleAndTrigValues(int angle, double sin, double cos) {
			this.angle = angle;
			this.sin = sin;
			this.cos = cos;
		}

		/**
		 * Returns angle value
		 * @return angle value
		 */
		public int getAngle() {
			return angle;
		}

		/**
		 * Returns sin value
		 * @return sin value
		 */
		public double getSin() {
			return sin;
		}

		/**
		 * Returns cos value
		 * @return cos value
		 */
		public double getCos() {
			return cos;
		}
	}
}
