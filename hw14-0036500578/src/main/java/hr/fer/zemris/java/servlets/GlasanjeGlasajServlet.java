package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.dao.DAOException;
import hr.fer.zemris.java.dao.DAOProvider;

/**
 * Implementation of a servlet which processes HTTP  GET request and 
 * adds one to the number of votes to the option represented by the 
 * specified id. Id must be an integer and must correspond to the 
 * one of the available voting options.
 * @author Josip Trbuscic
 *
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String optionIdString = req.getParameter("id");
		
		int optionId=0;
		try {
			optionId = Integer.parseInt(optionIdString);
		} catch(NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID must be an integer");
			return;
		}
		
		int pollID = 0;
		synchronized (this) {
			try {
				DAOProvider.getDao().incrementVoteCount(optionId);
				pollID = DAOProvider.getDao().getPollID(optionId);
			}catch(DAOException ex) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Poll option with given id doesn't exist");
				return;
			}
		}
		resp.sendRedirect("glasanje-rezultati?pollID="+pollID);
	}
	
}