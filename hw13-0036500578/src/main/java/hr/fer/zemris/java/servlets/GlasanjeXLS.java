package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.servlets.util.Band;
import hr.fer.zemris.java.servlets.util.Util;

/**
 * Implementation of a servlet which processes HTTP  GET request
 * and generates .xls file which contains sheet with names of the band
 * and number of votes 
 * @author Josip Trbuscic
 *
 */
@WebServlet("/glasanje-xls")
public class GlasanjeXLS extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
		
		Util.createResultsFile(req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt"));
		List<Band> bands = Util.getBands(req);
		Util.mapVotesToBands(Util.getResults(req), bands);
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("Bands");
		HSSFRow rowhead=   sheet.createRow((short)0);
		
		rowhead.createCell((short) 0).setCellValue("Band");
		rowhead.createCell((short) 1).setCellValue("Numnber of votes");
		
		for(int i = 0, size = bands.size(); i< size; i++) {
			HSSFRow row = sheet.createRow(i+1);
			row.createCell((short) 0).setCellValue(bands.get(i).getName());
			row.createCell((short) 1).setCellValue(bands.get(i).getVotes());
		}
		hwb.close();
		hwb.write(resp.getOutputStream());
	}
}
