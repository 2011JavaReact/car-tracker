package com.revature.carTracker.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.revature.carTracker.dao.DatabaseCredentialsDAO;

/**
 * Servlet implementation class UserLoginServlet
 */
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DatabaseCredentialsDAO credentialsDAO = new DatabaseCredentialsDAO();
	Logger logger = Logger.getLogger(UserLoginServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**Ends a user session if they're logged in.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Check for empty session.
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(401);
			return;
		}
		//End user session.
		if (request.getServletPath().equals("/logout")) {
			response.getWriter().append((String)session.getAttribute("username") + " logged out.");
			logger.info((String)session.getAttribute("username") + " logged out.");
			session.invalidate();
		} else {
			response.setStatus(404);
		}
	}

	/**Creates a user session if they aren't
	 * already logged in.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getServletPath().equals("/logout")) {
			response.setStatus(404);
			return;
		}
		//Set request paramters.
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String newPassword = credentialsDAO.getPassword(username);
		//Create new Http session if password is correct.
		if (newPassword.equals(password)) {
			logger.info(username + " logged in.");
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			session.setAttribute("isAdmin", credentialsDAO.getIsAdmin(username));
			String currentUsername = (String)session.getAttribute("username");
			response.getWriter().append(currentUsername + " logged in.");
			response.setStatus(200);
		} else {
			response.setStatus(400);
			response.getWriter().append("Incorrect password or user does not exist.");
		}
	}

}
