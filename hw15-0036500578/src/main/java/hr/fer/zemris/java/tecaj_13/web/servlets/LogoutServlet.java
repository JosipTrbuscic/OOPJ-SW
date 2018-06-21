package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to logout user who was logged in current session
 * @author Josip Trbuscic
 *
 */
@WebServlet("/servleti/logout")
public class LogoutServlet extends HttpServlet{
	private static final long serialVersionUID = -1770316178727453773L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().invalidate();
		req.getRequestDispatcher("/servleti/main").forward(req, resp);
	}
}
