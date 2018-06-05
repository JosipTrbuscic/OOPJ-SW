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

@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> bandsString = Files.readAllLines(Paths.get(fileName));
		
		List<Band> bands = new ArrayList<>();
		bandsString.forEach(s->{
			String[] parts = s.split("\\t+");
			bands.add(new Band(Integer.parseInt(parts[0]), parts[1], parts[2]));
		});
		
		req.setAttribute("bands", bands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
	
	public static class Band{
		Integer id;
		String name;
		String link;
		Integer votes;
		
		public Band(Integer id, String name, String link) {
			this.id = id;
			this.name = name;
			this.link = link;
		}

		public int getID() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String getLink() {
			return link;
		}
		
		public Integer getVotes() {
			return votes;
		}
		
	}

}
