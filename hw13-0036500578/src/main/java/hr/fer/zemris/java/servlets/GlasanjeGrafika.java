package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

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

import hr.fer.zemris.java.servlets.util.Band;
import hr.fer.zemris.java.servlets.util.Util;

/**
* Implementation of a servlet which processes HTTP  GET request 
* and generates report in a form of image of piechart and writes it
* in outputstream of a response. Piechart will be generated from the
* voting results which are loaded from the file
* @author Josip Trbuscic
*
*/
@WebServlet("/glasanje-grafika")
public class GlasanjeGrafika extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");
		
		Util.createResultsFile(req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt"));
		List<Band> bands = Util.getBands(req);
		Util.mapVotesToBands(Util.getResults(req), bands);
		
		
		PieDataset dataset = createDataset(bands);
        JFreeChart chart = createChart(dataset, "Voting results");
        OutputStream os = resp.getOutputStream();
        ImageIO.write(chart.createBufferedImage(400, 400), "png", os);
        os.close();
	}

	/**
	 * Creates dataset from given list of bands
	 * @param bands
	 * @return
	 */
	private PieDataset createDataset(List<Band> bands) {
		DefaultPieDataset result = new DefaultPieDataset();
		bands.forEach(b->{
			result.setValue(b.getName(), b.getVotes());
		});
		return result;

	}

	/**
	 * Creates a chart
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title, // chart title
				dataset, // data
				true, // include legend
				true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;

	}
}
