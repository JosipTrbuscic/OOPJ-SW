package servlets;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.GeometricalObject;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jdraw.geometricalObjects.GeometricalObjectPainter;

@WebServlet("/crtaj")
public class CrtajServlet extends HttpServlet{
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String content = req.getParameter("jvd");
		
		String[] lines = content.split("\\n");
		List<GeometricalObject> objects =  Utils.Utils.parseLines(Arrays.asList(lines));
		
		GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
		
		for(int i=objects.size()-1; i>=0; i--) {
			objects.get(i).accept(bbcalc);
		}
		Rectangle box = bbcalc.getBoundingBox();
		
		BufferedImage image = new BufferedImage(
			    box.width, box.height, BufferedImage.TYPE_3BYTE_BGR
		);
		
		Graphics2D g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, box.width, box.height);
		g.translate(-box.x, -box.y);
		
		GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
		
		for(int i=objects.size()-1; i>=0; i--) {
			objects.get(i).accept(painter);
		}
		g.dispose();
		OutputStream os = resp.getOutputStream();
        ImageIO.write(image, "png", os);
        os.close();
	}
}
