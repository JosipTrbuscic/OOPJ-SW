package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/trigonometric")
public class Trigonometric extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String aString = req.getParameter("a");
		String bString = req.getParameter("b");
		
		int a = isInt(aString) ? Integer.parseInt(aString) : 0;
		int b = isInt(bString) ? Integer.parseInt(bString) : 360;
		
		if(a > b) {
			int temp = a;
			a = b;
			b=temp;
		}
		
		List<NumberAndTrigValues> list = new ArrayList<>();
		for(int i = a; i<=b; i++) {
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

	private boolean isInt(String s) {
		Pattern p = Pattern.compile("^\\d+$");
		Matcher	m = p.matcher(s);
		
		if(m.find()) return true;
		return false;
	}
}
