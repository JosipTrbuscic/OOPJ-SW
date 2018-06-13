package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.PollOption;

/**
 * Implementation of a servlet which processes HTTP  GET requests,
 * loads list of poll options with specified poll id and additionally
 * generates list of options with highest number votes
 * @author Josip Trbuscic
 *
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idString = req.getParameter("pollID");
		int pollID = 0;
		try {
			pollID = Integer.parseInt(idString);
		}catch(NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid poll ID");
		}
		List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(pollID);
		
		if(pollOptions.isEmpty()) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Poll with specified id doesn't exist");
		}
		Collections.sort(pollOptions, PollOption.OPTIONS_COMPARATOR);
		List<PollOption> best = getBest(pollOptions);
		
		req.setAttribute("pollID", pollID);
		req.setAttribute("pollOptions", pollOptions);
		req.setAttribute("best", best);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
	//TODO ispravi javadoc
	/**
	 * Returns the list of bands with the highest number of votes 
	 * @param bands - list of bands
	 * @return list of bands with the highest number of votes 
	 */
	private List<PollOption> getBest(List<PollOption> pollOptions) {
		int max = pollOptions.get(0).getVotesCount();
		List<PollOption> best = new ArrayList<>(); 
		for(PollOption option : pollOptions) {
			if(option.getVotesCount()>=max) {
				best.add(option);
			}
		}
		return best;
	}
}
