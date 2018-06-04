package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/trigonometric")
public class Trigonometric extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a = req.getParameter("a");
		String b = req.getParameter("b");
		
		int aInt = getInteger(a, false);
		int bInt = getInteger(b, true);
		
		if(aInt > bInt) {
			int temp = aInt;
			aInt = bInt;
			bInt=temp;
		}
		
		List<NumberAndTrigValues> list = new LinkedList<>();
		for(int i = aInt; i<=bInt; i++) {
			list.add(new NumberAndTrigValues(i, Math.sin(Math.toRadians(i)), Math.cos(Math.toRadians(i))));
		}
		
		req.setAttribute("values", list );
		req.getRequestDispatcher("WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}
	
	public static class NumberAndTrigValues{
		private int number;
		private double sin;
		private double cos;
		
		public NumberAndTrigValues(int number, double sin, double cos) {
			this.number = number;
			this.sin = sin;
			this.cos = cos;
		}
		
		public int getNumber() {
			return number;
		}
		
		public double getSin() {
			return sin;
		}
		
		public double getCos() {
			return cos;
		}
	}

	private Integer getInteger(String a, boolean isB) {
		if(a != null) {
			try {
				return Integer.valueOf(a);
			} catch(NumberFormatException ignore) {
			}
		}
		return isB ?Integer.valueOf(360) : Integer.valueOf(0);
	}
}
