package com.revature.carTracker.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.carTracker.dao.DatabaseCarDAO;
import com.revature.carTracker.dao.DatabaseCustomerDAO;
import com.revature.carTracker.model.Car;

/**
 * Servlet implementation class CarServlet
 */
public class CarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ObjectMapper objectMapper = new ObjectMapper();
	private DatabaseCarDAO carDAO = new DatabaseCarDAO();
	private DatabaseCustomerDAO customerDAO=new DatabaseCustomerDAO();
	Logger logger = Logger.getLogger(CarServlet.class);
    
    public CarServlet() {
        super();
    }

	/**Retrieves a database table after user
	 * enters login criteria
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	logger.info("Executed HTTP GET request.");
    	//Set endpoint parameters and 
    	//create HTTP session for login.
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String name = request.getParameter("carName");
		HttpSession session = request.getSession();
		
		try {
			session.setAttribute("username", username);
			session.setAttribute("password", password);
			String currentUser = (String)session.getAttribute("username");
			String currentPassword = (String)session.getAttribute("password");
			
			//Return error page if login is 
			//left empty.
			if (currentUser == null || currentPassword == null) {
				response.setStatus(400);
			} else {
				//Retrieve table and append to JSON format.
				String jsonString = objectMapper.writeValueAsString(carDAO.getAllCars());
				String jsonString2 = objectMapper.writeValueAsString(customerDAO.getAllCustomers());
				if (currentPassword.equals("admin")) {
					logger.info(currentUser + " logged in as: ADMIN. ");
					response.getWriter().append("Welcome " + currentUser);
					response.getWriter().append(jsonString);
					response.getWriter().append(jsonString2);
					response.setContentType("application/json");
					
					//Set successful HTTP status and release memory
					//from response.getWriter().
					response.setStatus(200);
					response.getWriter().flush();
					response.getWriter().close();
				} else {
					logger.info(currentUser + " logged in as: STANDARD USER.");
					response.getWriter().append("Welcome " + currentUser);
					response.getWriter().append(jsonString);
					response.setContentType("application/json");
					
					response.setStatus(200);
					response.getWriter().flush();
					response.getWriter().close();
				}
			}
		} catch (IOException e) {
			response.setStatus(400);
			e.printStackTrace();
		} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
//    	String username = request.getParameter("username");
//		String password = request.getParameter("password");
//		String name = request.getParameter("carName");
//		String price = request.getParameter("carPrice");
//		HttpSession session = request.getSession();
//		
//		try {
//			session.setAttribute("username", username);
//			session.setAttribute("password", password);
//			session.setAttribute("carName", name);
//			session.setAttribute("carPrice", price);
//			String currentUser = (String)session.getAttribute("username");
//			String currentPassword = (String)session.getAttribute("password");
//			String currentName = (String)session.getAttribute("carName");
//			String currentPrice = (String)session.getAttribute("carPrice");
//		
//			if (currentUser == null || currentPassword == null) {
//				response.setStatus(400);
//			} else {
//				String jsonString = objectMapper.writeValueAsString(carDAO.addCar(name, price));
//				response.getWriter().append("Welcome " + currentUser);
//				response.getWriter().append(jsonString);
//				response.setStatus(200);
//				response.getWriter().flush();
//				response.getWriter().close();
//			}
//		} catch (IOException e) {
//			response.setStatus(400);
//			e.printStackTrace();
//		}
    }
}


