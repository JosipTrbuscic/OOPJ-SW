package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.LoginForm;

/**
 * Servlet used to fetch all data needed to display home page and
 * handle logins
 * @author josip
 *
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet{
	
	private static final long serialVersionUID = 8067748696702751032L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BlogUser> users = DAOProvider.getDAO().getAllUsers();
		req.setAttribute("users", users);
		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LoginForm lForm = new LoginForm();
		lForm.fillFromHttpRequest(req);
		lForm.validate();
		
		req.setAttribute("form", lForm);
		if(!lForm.hasErrors()) {
			req.getSession().setAttribute("login", lForm.getBlogUser());
			resp.sendRedirect("main");
			return;
		}
		List<BlogUser> users = DAOProvider.getDAO().getAllUsers();
		req.setAttribute("users", users);
		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}
}
