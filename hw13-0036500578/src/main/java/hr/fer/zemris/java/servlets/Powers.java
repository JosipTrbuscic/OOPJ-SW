package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author josip
 *
 */
@WebServlet("/powers")
public class Powers extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a = req.getParameter("a");
		String b = req.getParameter("b");
		String n = req.getParameter("n");
		
		if(a == null || b == null || n == null) {
			req.setAttribute("msg", "Must specify a, b and n parameters");
			req.getRequestDispatcher("/WEB-INF/pages/powerError.jsp").forward(req, resp);
		}
		
		int aInt = 0;
		int bInt = 0;
		int nInt = 0;
		try {
			aInt = Integer.parseInt(a);
			bInt = Integer.parseInt(b);
			nInt = Integer.parseInt(n);
		}catch(NumberFormatException e) {
			req.setAttribute("msg", "Invalid parameters value");
			req.getRequestDispatcher("/WEB-INF/pages/powerError.jsp").forward(req, resp);
		}
		
		if(aInt < -100 || aInt > 100 ||
			bInt < -100 || bInt > 100 ||
			nInt <1 || nInt > 5) {
			req.setAttribute("msg", "Parameter(s) out of range");
			req.getRequestDispatcher("/WEB-INF/pages/powerError.jsp").forward(req, resp);
		}
		
		if(aInt > bInt) {
			int temp = aInt;
			aInt = bInt;
			bInt = temp;
		}
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		
		for(int i = 1; i<= nInt; i++) {
			HSSFSheet sheet = hwb.createSheet(String.valueOf(i));
			HSSFRow rowhead=   sheet.createRow((short)0);
			
			rowhead.createCell((short) 0).setCellValue("Number");
			rowhead.createCell((short) 1).setCellValue("Power");
			
			for(int j = aInt; j<=bInt; j++) {
				HSSFRow row=   sheet.createRow((short)j-aInt+1);
				row.createCell((short) 0).setCellValue(j);
				row.createCell((short) 1).setCellValue(Math.pow(j, i));
			}
		}
		hwb.close();
		hwb.write(resp.getOutputStream());
	}
}
