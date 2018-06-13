package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hr.fer.zemris.java.servlets.util.Band;

/**
 * Implementation of a servlet which processes HTTP  GET requests and
 * reads the list of bands clients can vote for. Each band has unique 
 * ID number, a name and a link to their video. 
 * @author Josip Trbuscic
 *
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int MINIMUM_NUMBER_OF_BANDS = 7;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> bandsString = Files.readAllLines(Paths.get(fileName));
		
		if(bandsString.size() < MINIMUM_NUMBER_OF_BANDS) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not enough specified bands");
		}
		
		List<Band> bands = new ArrayList<>();
		bandsString.forEach(s->{
			String[] parts = s.split("\\t+");
			bands.add(new Band(Integer.parseInt(parts[0]), parts[1], parts[2]));
		});
		
		req.setAttribute("bands", bands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
	

}
