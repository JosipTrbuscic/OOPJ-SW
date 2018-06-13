package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.Poll;
import hr.fer.zemris.java.model.PollOption;

/**
 * Implementation of a servlet which processes HTTP  GET requests and
 * reads the list of options clients can vote for. Poll is specified 
 * with its id.
 * @author Josip Trbuscic
 *
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idString = req.getParameter("pollID");
		
		int pollID = 0;
		try {
			pollID = Integer.parseInt(idString);
		}catch(NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid poll ID");
			return;
		}
		
		Poll poll= DAOProvider.getDao().getPoll(pollID);
		if(poll == null) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Poll with specified id doesn't exist");
		}
		req.setAttribute("poll", poll);
		
		List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(pollID);
		req.setAttribute("pollOptions", pollOptions);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
	

}
