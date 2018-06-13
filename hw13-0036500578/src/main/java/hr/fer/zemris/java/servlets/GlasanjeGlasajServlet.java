package hr.fer.zemris.java.servlets;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.servlets.util.Util;

/**
 * Implementation of a servlet which processes HTTP  GET request and 
 * adds one to the number of votes to the band represented by the 
 * specified id. Id must be an integer and must correspond to the 
 * one of the available bands.
 * @author Josip Trbuscic
 *
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idString = req.getParameter("id");
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Util.createResultsFile(fileName);
		
		int id=0;
		try {
			id = Integer.parseInt(idString);
		} catch(NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Band ID must be an integer");
		}
		
		synchronized (this) {
			Map<Integer, Integer> resultsMap = Util.getResults(req);
			resultsMap.put(id, resultsMap.get(id)==null ? 1 : resultsMap.get(id) +1);
			
			FileWriter fw = new FileWriter(fileName);
			
			for(Integer key : resultsMap.keySet()) {
				fw.write(key+"\t"+resultsMap.get(key)+System.lineSeparator());
			}
			fw.close();
		}
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
	
}