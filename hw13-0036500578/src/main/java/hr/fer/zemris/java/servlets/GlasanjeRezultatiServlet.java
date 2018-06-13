package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.servlets.util.Band;
import hr.fer.zemris.java.servlets.util.Util;

/**
 * Implementation of a servlet which processes HTTP  GET requests,
 * reads the list of bands and voting results and maps the number
 * of votes to the band. Additionally servlet will generate list 
 * of bands with highest number votes
 * @author Josip Trbuscic
 *
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		
		Util.createResultsFile(fileName);
		List<Band> bands = Util.getBands(req);
		Util.mapVotesToBands(Util.getResults(req), bands);
		
		req.setAttribute("bands", bands);
		req.setAttribute("best", getBest(bands));
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
	
	/**
	 * Returns the list of bands with the highest number of votes 
	 * @param bands - list of bands
	 * @return list of bands with the highest number of votes 
	 */
	private List<Band> getBest(List<Band> bands) {
		Iterator<Band> it = bands.iterator();
		int max = 0;
		while(it.hasNext()){
			Band b = it.next();
			max = b.getVotes() > max ? b.getVotes() : max;
		}
		int fmax = max;
		List<Band> best = new ArrayList<>();
		bands.forEach(b->{
			if(b.getVotes() >= fmax) {
				best.add(b);
			}
		});
		return best;
	}
}
