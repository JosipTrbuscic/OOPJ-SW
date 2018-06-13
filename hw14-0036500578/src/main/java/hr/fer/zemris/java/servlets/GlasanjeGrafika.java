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

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.PollOption;

/**
* Implementation of a servlet which processes HTTP  GET request 
* and generates report in a form of image of piechart and writes it
* in outputstream of a response. Piechart will be generated from the
* voting results which are loaded using the data access object
* @author Josip Trbuscic
*
*/
@WebServlet("/servleti/glasanje-grafika")
public class GlasanjeGrafika extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");
		
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
		
		PieDataset dataset = createDataset(pollOptions);
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
	private PieDataset createDataset(List<PollOption> bands) {
		DefaultPieDataset result = new DefaultPieDataset();
		bands.forEach(b->{
			result.setValue(b.getOptionTitle(), b.getVotesCount());
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
