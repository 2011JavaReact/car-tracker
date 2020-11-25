package com.revature.carTracker.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.carTracker.dao.DatabaseCarDAO;
import com.revature.carTracker.dao.DatabaseCustomerDAO;

/**
 * Servlet implementation class CustomerServlet
 */
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ObjectMapper objectMapper = new ObjectMapper();
	private DatabaseCarDAO carDAO = new DatabaseCarDAO();
	Logger logger = Logger.getLogger(CustomerServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("Executed HTTP GET request.");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String name = request.getParameter("customer");
		HttpSession session = request.getSession();
		
		try {
			session.setAttribute("username", username);
			session.setAttribute("password", password);
			String currentUser = (String)session.getAttribute("username");
			String currentPassword = (String)session.getAttribute("password");
		
			if (currentUser == null || currentPassword == null) {
				response.setStatus(400);
			} else {
				String jsonString = objectMapper.writeValueAsString(carDAO.getAllCars());
				logger.info(currentUser + " logged in as: STANDARD USER.");
				response.getWriter().append("Welcome " + currentUser);
				response.getWriter().append(jsonString);
				response.setContentType("application/json");
				
				response.setStatus(200);
				response.getWriter().flush();
				response.getWriter().close();
			}
		} catch (IOException e) {
			response.setStatus(400);
			e.printStackTrace();
		} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
