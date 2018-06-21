package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogEntryForm;

/**
 * Servlet used to edit title and text of existing blog entry
 * @author Josip Trbuscic
 *
 */
@WebServlet("/servleti/editEntry")
public class EditBlogEntryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlogEntryForm form = new BlogEntryForm();
		form.fillFromHttpRequest(req);
		form.validate();
		
		String eidString = req.getParameter("eid");
		
		long eid = 0;
		try {
			eid = Long.parseLong(eidString);
		}catch(NumberFormatException ex){
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Must provide integer entry ID");
		}
		
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(eid);
		if(entry == null) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"No entry with provided entry ID");
		}
		
		if(form.hasErrors()) {
			req.setAttribute("entry", entry);
			req.setAttribute("form", form);
			req.getRequestDispatcher("/WEB-INF/pages/editEntry.jsp").forward(req, resp);
			return;
		}
		
		
		
		entry.setLastModifiedAt(new Date());
		entry.setTitle(form.getTitle());
		entry.setText(form.getText());
		
		resp.sendRedirect("author/"+entry.getCreator().getNick()+"/"+eid);
	}
}
