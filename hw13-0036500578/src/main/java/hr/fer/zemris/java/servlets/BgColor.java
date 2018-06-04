package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/setcolor")
public class BgColor extends HttpServlet{
	private static final String WHITE = "FFFFFF";
	private static final String CYAN = "00FFFF";
	private static final String RED = "FF0000";
	private static final String GREEN = "00FF00";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String colorHex = req.getParameter("pickedBgCol");

		colorHex = colorHex == null ? "FFFFFF" : colorHex;
		
		switch (colorHex) {
		case WHITE:
			req.getSession().setAttribute("pickedBgCol", WHITE);
			break;	
		case CYAN:
			req.getSession().setAttribute("pickedBgCol", CYAN);
			break;
		case GREEN:
			req.getSession().setAttribute("pickedBgCol", GREEN);
			break;
		case RED:
			req.getSession().setAttribute("pickedBgCol", RED);
			break;
		default:
			req.getSession().setAttribute("pickedBgCol", WHITE);
		}
		
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}
	
}
