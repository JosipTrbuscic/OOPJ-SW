package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
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

import hr.fer.zemris.java.servlets.GlasanjeServlet.Band;

@WebServlet("/glasanje-grafika")
public class GlasanjeGrafika extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");
		PieDataset dataset = createDataset((List<Band>) req.getServletContext().getAttribute("bands"));
        JFreeChart chart = createChart(dataset, "");
        OutputStream os = resp.getOutputStream();
        ImageIO.write(chart.createBufferedImage(400, 400), "png", os);
        os.close();
	}
	
	private PieDataset createDataset(List<GlasanjeServlet.Band> bands) {
		DefaultPieDataset result = new DefaultPieDataset();
		double sum = getSum(bands);
		bands.forEach(b->{
			result.setValue(b.name, b.votes/sum);
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
	
	private double getSum(List<GlasanjeServlet.Band> bands) {
		Iterator<GlasanjeServlet.Band> it = bands.iterator();
		double sum = 0;
		while(it.hasNext()) {
			GlasanjeServlet.Band band = it.next();
			sum += band.votes;
		}
		
		return sum;
 	}
}
