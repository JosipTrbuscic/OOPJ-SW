package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.forms.BlogEntryForm;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet used to handle requests about specific users and
 * request to create new blog entry 
 * @author Josip Trbuscic
 *
 */
@WebServlet(urlPatterns = "/servleti/author/*")
public class AuthorServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] parts = req.getPathInfo().substring(1).split("/");
		
		if(parts.length > 2) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND,"Servlet not found");
			return;
		}
		
		String nick = parts[0];
		boolean isOwner = false;
		
		BlogUser requestedUser = DAOProvider.getDAO().getUserFromNick(nick);
		if(requestedUser == null ) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Nonexisting author requested");
			return;
		}
		
		BlogUser logedInUser = (BlogUser) req.getSession().getAttribute("login");
		if(logedInUser != null && logedInUser.getNick().equals(requestedUser.getNick())) {
			isOwner = true;
		}
		
		req.setAttribute("isOwner", isOwner);
		req.setAttribute("nick", nick);
		if(parts.length == 2) {
			
			if(parts[1].equals("new")) {
				if(!isOwner) {
					resp.sendError(HttpServletResponse.SC_FORBIDDEN,"Login required");
					return;
				}
				req.getRequestDispatcher("/WEB-INF/pages/newEntry.jsp").forward(req, resp);
				return;
			}else if(parts[1].startsWith("edit")) {
				if(!isOwner) {
					resp.sendError(HttpServletResponse.SC_FORBIDDEN,"Login required");
					return;
				}
				
				String eidString = req.getParameter("eid");
				long eid = 0;
				try {
					eid = Long.parseLong(eidString);
				}catch(NumberFormatException ex) {
					resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Must provide integer id");
				}
				
				BlogEntry requestedEntry =  DAOProvider.getDAO().getBlogEntry(eid);
				if(!requestedEntry.getCreator().getNick().equals(logedInUser.getNick())) {
					resp.sendError(HttpServletResponse.SC_FORBIDDEN,"Login required");
					return;
				}
				
				req.setAttribute("entry", DAOProvider.getDAO().getBlogEntry(eid));
				req.getRequestDispatcher("/WEB-INF/pages/editEntry.jsp").forward(req, resp);
				return;
				
			}else {
				long id = 0;
				try {
					id = Long.parseLong(parts[1]);
					
				}catch(NumberFormatException e) {
					resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Must provide integer entry ID");
				}
				BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
				if(entry == null) {
					resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"No entry with provided entry ID");
				}
				
				req.setAttribute("entry", entry);
				req.setAttribute("comments", entry.getComments());
				req.getRequestDispatcher("/WEB-INF/pages/showEntry.jsp").forward(req, resp);
				return;
			}
		}
		List<BlogEntry> entries = DAOProvider.getDAO().getUserBlogs(requestedUser);
		req.setAttribute("entries", entries);
		req.getRequestDispatcher("/WEB-INF/pages/author.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = req.getParameter("nick");
		BlogEntryForm entryForm = new BlogEntryForm();
		entryForm.fillFromHttpRequest(req);
		entryForm.validate();
		
		if(DAOProvider.getDAO().getUserFromNick(nick) == null) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid nick");
		}
		
		if(entryForm.hasErrors()) {
			req.setAttribute("form", entryForm);
			req.setAttribute("nick", nick);
			req.getRequestDispatcher("/WEB-INF/pages/newEntry.jsp").forward(req, resp);
			return;
		}
		
		BlogUser creator = DAOProvider.getDAO().getUserFromNick(nick);
		BlogEntry entry = new BlogEntry();
		
		entry.setCreator(creator);
		entry.setCreatedAt(new Date());
		entry.setLastModifiedAt(new Date());
		entry.setTitle(entryForm.getTitle());
		entry.setText(entryForm.getText());
		DAOProvider.getDAO().addBlogEntry(entry);

		resp.sendRedirect("/blog/servleti/author/"+nick+"/new");;
	}
}
