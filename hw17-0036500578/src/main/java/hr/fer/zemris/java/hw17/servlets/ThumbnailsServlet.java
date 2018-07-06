package hr.fer.zemris.java.hw17.servlets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which writes thumbnail of specified picture to the output stream.
 * If specified thumbnail was request for the first time it will be saved 
 * to the folder so it can be reused. Folder containing thumbnails will also 
 * be created if it doesn't already exist.
 * @author Josip Trbuscic
 *
 */
@WebServlet("/servlets/thumbnail")
public class ThumbnailsServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Path to folder containing pictures
	 */
	private static final String PICTURES_PATH = "/WEB-INF/slike/";
	
	/**
	 * Path to folder containing thumbnails
	 */
	private static final String THUMBNAILS_PATH = "/WEB-INF/thumbnails/";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");
		String name = req.getParameter("name");
		Path folderPath = Paths.get(req.getServletContext().getRealPath(THUMBNAILS_PATH));
		
		if(!folderPath.toFile().exists()) {
			folderPath.toFile().mkdir();
		}
		File thumbnailFile = Paths.get(req.getServletContext().getRealPath(THUMBNAILS_PATH+name)).toFile();
		File pictureFile = Paths.get(req.getServletContext().getRealPath(PICTURES_PATH+name)).toFile();

		if(thumbnailFile.exists()) {
			ImageIO.write(ImageIO.read(thumbnailFile), "png", resp.getOutputStream());
		}else {
			BufferedImage img = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
			img.createGraphics().drawImage(ImageIO.read(pictureFile).getScaledInstance(150, 150, BufferedImage.SCALE_SMOOTH),0,0,null);
			ImageIO.write(img, "png", thumbnailFile);
			
			ImageIO.write(img, "png", resp.getOutputStream());
		}
	}
}
