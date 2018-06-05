package hr.fer.zemris.java.servlets;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
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

@WebServlet("glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idString = req.getParameter("id");
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		File res = Paths.get(fileName).toFile();
		
		if(!res.exists()) {
			Files.createFile(res.toPath());
		}
		
		int id=0;
		try {
			id = Integer.parseInt(idString);
		} catch(NumberFormatException e) {
			//TODO add error .jsp
			req.getRequestDispatcher("").forward(req, resp);
		}
		List<String> resultsLines = Files.readAllLines(res.toPath());
		Map<Integer,Integer> resultsMap = new TreeMap<>();
		
		resultsLines.forEach(r->{
			String[] parts = r.split("\\t+");
			resultsMap.put(Integer.valueOf(parts[0]), Integer.valueOf(parts[1]));
		});
		resultsMap.put(id, resultsMap.get(id)==null ? 1 : resultsMap.get(id) +1);
		
		Iterator<Integer> it = resultsMap.keySet().iterator();
		FileWriter fw = new FileWriter(res);
		while (it.hasNext()) {
			try {
				Integer key = it.next();
				fw.write(key+"\t"+resultsMap.get(key));
				if(it.hasNext()) {
					fw.write(System.lineSeparator());
				}
			} catch (IOException ignorable) {
			}
		}
		fw.close();
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}
