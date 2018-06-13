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

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.PollOption;

/**
 * Implementation of a servlet which processes HTTP  GET request
 * and generates .xls file which contains sheet with titles
 * and number of votes 
 * @author Josip Trbuscic
 *
 */
@WebServlet("/servleti/glasanje-xls")
public class GlasanjeXLS extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
		
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
		
	  	HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet();
		HSSFRow rowhead=   sheet.createRow((short)0);
		
		rowhead.createCell((short) 0).setCellValue("Title");
		rowhead.createCell((short) 1).setCellValue("Number of votes");
		
		for(int i = 0, size = pollOptions.size(); i< size; i++) {
			HSSFRow row = sheet.createRow(i+1);
			row.createCell((short) 0).setCellValue(pollOptions.get(i).getOptionTitle());
			row.createCell((short) 1).setCellValue(pollOptions.get(i).getVotesCount());
		}
		hwb.close();
		hwb.write(resp.getOutputStream());
	}
}
