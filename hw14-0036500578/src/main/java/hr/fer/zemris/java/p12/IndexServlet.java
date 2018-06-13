package hr.fer.zemris.java.p12;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;

@WebServlet("/servleti/index.html")
public class IndexServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Poll> polls = DAOProvider.getDao().getPolls(); 
		resp.setContentType("text/html; charset=utf-8");
		
		resp.getWriter().write("<html>");
		resp.getWriter().write("<head>");
		resp.getWriter().write("<meta charset='utf-8'>");
		resp.getWriter().write("</head>");
		resp.getWriter().write("<body>");
		resp.getWriter().write("<h1>Choose poll</h1>");
		resp.getWriter().write("<ol>");
		polls.forEach(p->{
			try {
				resp.getWriter().write(" <li><a href = \"glasanje?pollID="+p.getId()+"\" >"+p.getTitle()+"</a></li><br>");
			} catch (IOException ignorable) {
			}
		});
		
		resp.getWriter().write("</ol>");
		resp.getWriter().write("</body>");
		resp.getWriter().write("</html>");
	}
}
