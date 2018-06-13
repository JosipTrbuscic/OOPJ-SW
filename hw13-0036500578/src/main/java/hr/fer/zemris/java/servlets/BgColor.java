package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Implementation of a servlet which processes HTTP  GET request and sets 
 * the background to one of specified colors. Available colors are 
 * CYAN(#00FFFF), RED(#FF0000), GREEN(#00FF00) and WHITE(#FFFFFF). 
 * If specified code does not match one of the available colors 
 * servlet will default to white color.  
 * @author Josip Trbuscic
 *
 */
@WebServlet("/setcolor")
public class BgColor extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	/**
	 * White color hex code
	 */
	private static final String WHITE = "FFFFFF";
	
	/**
	 * Cyan color hex code
	 */
	private static final String CYAN = "00FFFF";
	
	/**
	 * Red color hex code
	 */
	private static final String RED = "FF0000";
	
	/**
	 * Green color hex code
	 */
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
