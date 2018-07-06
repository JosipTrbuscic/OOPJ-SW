package hr.fer.zemris.java.hw17.servlets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which writes image with specified name to the output stream
 * @author Josip Trbuscic
 *
 */
@WebServlet("/servlets/fullPicture")
public class FullPictureServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Path to the folder containing pictures
	 */
	private static final String PICTURES_PATH = "WEB-INF/slike/";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");
		String name = req.getParameter("name");
		File pictureFile = Paths.get(req.getServletContext().getRealPath(PICTURES_PATH+name)).toFile();
		
		ImageIO.write(ImageIO.read(pictureFile), "png", resp.getOutputStream());
	}
}
