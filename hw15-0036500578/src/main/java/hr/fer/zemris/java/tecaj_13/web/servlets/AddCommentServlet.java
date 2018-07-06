package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet used to add comment on a blog entry
 * @author Josip Trbuscic
 *
 */
@WebServlet("/servleti/addComment")
public class AddCommentServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String eidString = req.getParameter("eid");
		String email = req.getParameter("email");
		String text = req.getParameter("text");
		
		email = email == null ? ((BlogUser) req.getSession().getAttribute("login")).getEmail() : email;
		Long eid = (long) 0;
		try {
			eid = Long.parseLong(eidString);
		} catch (NumberFormatException e){
			
		}
		
		BlogComment comment = new BlogComment();
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(eid);
		comment.setBlogEntry(entry);
		comment.setMessage(text);
		comment.setUsersEMail(email);
		comment.setPostedOn(new Date());
		
		DAOProvider.getDAO().addBlogComment(comment);
		resp.sendRedirect("author/"+entry.getCreator().getNick()+"/"+eid);
	}
}
