package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;


/**
 * Implementation of a servlet which processes HTTP  GET request 
 * and generates report in a form of image of piechart and writes it
 * in outputstream of a response
 * @author Josip Trbuscic
 *
 */
@WebServlet("/reportImage")
public class ReportImage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");
		PieDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset, "");
        OutputStream os = resp.getOutputStream();
        ImageIO.write(chart.createBufferedImage(480, 270), "png", os);
        os.close();
	}
	
	/**
	 * Creates dataset
	 * @return dataset
	 */
	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Linux", 10);
		result.setValue("Mac", 10);
		result.setValue("Windows", 10);
		return result;

	}

	/**
	 * Creates a chart
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title, // chart title
				dataset,
				true,
				true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;

	}
}
