package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static java.lang.Math.abs;

public class RandomFontColor extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Random rand = new Random();
		
		req.setAttribute("fontColor", new Color(abs(rand.nextInt())%256, abs(rand.nextInt())%256, abs(rand.nextInt())%256) );
		req.getRequestDispatcher("/WEB-INF/stories/funny.jsp").forward(req, resp);
	}
	
	public static class Color{
		private int red;
		private int green;
		private int blue;

		public Color(int red, int green, int blue) {
			this.red = red;
			this.green = green; 
			this.blue = blue;
		}
		
		public int getRed() {
			return red;
		}

		public int getGreen() {
			return green;
		}

		public int getBlue() {
			return blue;
		}

	}
}
