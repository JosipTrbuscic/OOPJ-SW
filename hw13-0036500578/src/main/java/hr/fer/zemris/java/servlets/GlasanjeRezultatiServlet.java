package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.servlets.GlasanjeServlet.Band;

@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		
		List<GlasanjeServlet.Band> bands = (List<Band>) req.getServletContext().getAttribute("bands");
		List<String> resultsLines = Files.readAllLines(Paths.get(fileName));
		Map<Integer,Integer> resultsMap = new TreeMap<>();
		
		resultsLines.forEach(r->{
			String[] parts = r.split("\\t+");
			resultsMap.put(Integer.valueOf(parts[0]), Integer.valueOf(parts[1]));
		});
		
		bands.forEach(b->{
			b.votes = resultsMap.get(b.id) == null ? 0 : resultsMap.get(b.id); 
		});
		
		req.setAttribute("best", getBest(bands));
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
	
	private List<Band> getBest(List<GlasanjeServlet.Band> bands) {
		Iterator<GlasanjeServlet.Band> it = bands.iterator();
		int max = 0;
		while(it.hasNext()){
			GlasanjeServlet.Band b = it.next();
			max = b.votes > max ? b.votes : max;
		}
		int fmax = max;
		List<Band> best = new ArrayList<>();
		bands.forEach(b->{
			if(b.votes >= fmax) {
				best.add(b);
			}
		});
		return best;
	}
}
