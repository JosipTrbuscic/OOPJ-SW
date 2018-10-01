package hr.fer.zemris.java.servlets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/igra/start")
public class GameStartServlet extends HttpServlet{
	public static Random rand = new Random();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String s = req.getParameter("debug");
		int correct = rand.nextInt(99+1);
		
		if(req.getSession().getAttribute("i") == null) {
			req.getSession().setAttribute("i", 1);
			req.getSession().setAttribute("correct",rand.nextInt(99)+1);
		}
		if(s != null && s.equals("true")) {
			req.setAttribute("debug", true);
			
		}
		
		savePicture((Integer)req.getSession().getAttribute("i"), Paths.get(req.getServletContext().getRealPath("WEB-INF/")+"/progress.png"));
		req.getRequestDispatcher("/WEB-INF/pages/game.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int number = -1;
		try {
			number = Integer.parseInt(req.getParameter("number"));

		}catch(NumberFormatException e) {
			
			resp.sendRedirect("start");
			return;
		}
		
		if(number == (Integer)req.getSession().getAttribute("correct")) {
			
		}else {
			int brojpokusaja = (Integer)req.getSession().getAttribute("i");
			if(brojpokusaja == 7) {
				resp.sendRedirect("end");
			}
			savePicture((Integer)req.getSession().getAttribute("i"), Paths.get(req.getServletContext().getRealPath("WEB-INF/")+"/progress.png"));
			req.getSession().setAttribute("i", (Integer)req.getSession().getAttribute("i")+1);
			resp.sendRedirect("start");
		}
		
		
	}
	
	private void savePicture(int i, Path path) {
		double percent = (double)(i-1)/7;
		
		BufferedImage image = new BufferedImage(
			    500, 50, BufferedImage.TYPE_3BYTE_BGR
		);
		
		Graphics2D g = image.createGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 500, 50);
		g.setColor(Color.RED);
		g.fillRect(0, 0, (int)Math.round(percent*500), 50);
		g.setColor(Color.WHITE);
		String.format("%.2f", percent);
		g.drawString(String.format("%.2f", percent*100)+"%", 235, 25);
		g.dispose();
		OutputStream os = null;
		try {
			ImageIO.write(image, "png", path.toFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
