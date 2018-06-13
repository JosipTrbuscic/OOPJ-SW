package hr.fer.zemris.java.servlets.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

/**
 * Class containing methods used to load files with band 
 * definitions and voting results. It also provides method
 * to map loaded result to the corresponding bands
 * @author Josip Trbuscic
 *
 */
public class Util {
	
	/**
	 * Loads band informations from the file and returns the lines 
	 * of the file as list
	 * @param req - servlet request
	 * @return list of strings which are lines of a file 
	 * @throws IOException if file could not be loaded
	 */
	public static List<Band> getBands(HttpServletRequest req) throws IOException{
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> bandsString = Files.readAllLines(Paths.get(fileName));
		
		List<Band> bands = new ArrayList<>();
		bandsString.forEach(s->{
			String[] parts = s.split("\\t+");
			bands.add(new Band(Integer.parseInt(parts[0]), parts[1], parts[2]));
		});
		
		return bands;
	}
	
	/**
	 * Loads the file containing voting results and returns the map where keys 
	 * are band IDs and their values are number of votes 
	 * @param req - servlet requests
	 * @return map containing voting statistic
	 * @throws IOException
	 */
	public static Map<Integer, Integer> getResults(HttpServletRequest req) throws IOException{
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		List<String> resultsLines = Files.readAllLines(Paths.get(fileName));
		
		Map<Integer,Integer> resultsMap = new TreeMap<>();
		
		resultsLines.forEach(r->{
			if(r.isEmpty()) return;
			String[] parts = r.split("\\t+");
			resultsMap.put(Integer.valueOf(parts[0]), Integer.valueOf(parts[1]));
		});
		return resultsMap;
	}
	
	/**
	 * Sets the number of votes of a band based on the number from the map of the results
	 * @param resultsMap - map containing band ID and number of votes
	 * @param bands - list of defined bands
	 */
	public static void mapVotesToBands(Map<Integer, Integer> resultsMap, List<Band> bands) {
		bands.forEach(b->{
			b.votes = resultsMap.get(b.id) == null ? 0 : resultsMap.get(b.id); 
		});
	}
	
	/**
	 * Creates new file which will contain voting results if 
	 * such file doesn't exist
	 * 
	 * @param path - path to file
	 * @throws IOException 
	 */
	public static void createResultsFile(String path) throws IOException {
		File res = Paths.get(path).toFile();
		
		if(!res.exists()) {
			Files.createFile(res.toPath());
		}
	}
}
