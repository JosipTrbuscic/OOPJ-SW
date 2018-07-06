package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.forms.RegisterForm;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet used to register a user
 * @author Josip Trbuscic
 *
 */
@WebServlet("/servleti/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		RegisterForm regForm = new RegisterForm();
		regForm.fillFromHttpRequest(req);
		regForm.validate();
		
		if(!regForm.hasErrors()) {
			BlogUser user = new BlogUser();
			regForm.populateUser(user);
			DAOProvider.getDAO().registerBlogUser(user);
		}
		
		req.setAttribute("form", regForm);
		req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
	}
}
