package com.revature.carTracker.servlet;

import java.io.IOException;
import java.util.ArrayList;

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
import com.revature.carTracker.model.Customer;

/**
 * Servlet implementation class CustomerServlet
 */
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ObjectMapper objectMapper = new ObjectMapper();
	private DatabaseCustomerDAO customerDAO = new DatabaseCustomerDAO();
	Logger logger = Logger.getLogger(CustomerServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**Retrieves table from DB.
	 * Can retrieve specific data by
	 * specified criteria.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Set endpoint parameters and 
    	//create HTTP session.
		logger.info("Executed HTTP GET request.");
		String name = request.getParameter("customerName");
		String id = request.getParameter("customerId");
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(401);
			return;
		}
		String currentUsername = (String)session.getAttribute("username");
		String jsonString = objectMapper.writeValueAsString(customerDAO.getAllCustomers());
		
		try { 
			//Return data by ID#.
			if (id != null && (boolean)session.getAttribute("isAdmin")) {
				ArrayList<Customer> c = customerDAO.getCustomerById(Integer.parseInt(id));
				response.setContentType("application/json");
				response.getWriter().append(objectMapper.writeValueAsString(c));
				response.setStatus(200);
				response.getWriter().flush();
				response.getWriter().close();
			}
			//Return data by name.
			if (name != null && (boolean)session.getAttribute("isAdmin")) {
				ArrayList<Customer> c = customerDAO.getCustomerByName(name);
				response.setContentType("application/json");
				response.getWriter().append(objectMapper.writeValueAsString(c));
				response.setStatus(200);
				response.getWriter().flush();
				response.getWriter().close();
			}
			//Retrieve table for ADMIN.
			if ((boolean)session.getAttribute("isAdmin")) {
				response.getWriter().append(jsonString);
				response.setContentType("application/json");
				response.setStatus(200);
				response.getWriter().flush();
				response.getWriter().close();
			} else {
				//Set status for unauthorized user.
				response.setStatus(401);
				response.getWriter().print("You don't have authorization to view this data.");
				logger.info(currentUsername + " failed to view data from Customers table.");
			}
		} catch (IOException e) {
			response.setStatus(400);
			e.printStackTrace();
		} 
	}

	/**Adds a row to the Customers table..
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Set endpoint parameters and 
    	//create HTTP session.
    	logger.info("Executed HTTP POST request.");
		String id = request.getParameter("carId");
		String name = request.getParameter("customerName");
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(401);
			return;
		}
		String currentUsername = (String)session.getAttribute("username");
		String jsonString = objectMapper.writeValueAsString(customerDAO.addCustomer(Integer.parseInt(id), name));
		
		try {
			//Insert new data into DB if user has admin rights.
			if ((boolean)session.getAttribute("isAdmin")) {
				logger.info("Customer added.");
				response.getWriter().append(jsonString);
				response.setContentType("application/json");
				logger.info(currentUsername + " successfully added new data to Customers table.");
				response.setStatus(200);
				response.getWriter().flush();
				response.getWriter().close();
			} else {
				//Set status for unauthorized user.
				response.setStatus(401);
				response.getWriter().print("You don't have authorization to modify data.");
				logger.info(currentUsername + " failed to add data to Customers table.");
			}
		} catch (IOException e) {
			response.setStatus(400);
			e.printStackTrace();
		}
    }
	
	/**Deletes a row from the Customers table.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
    	//Set endpoint parameters and
    	//create HTTP session.
		logger.info("Executed HTTP DELETE request.");
		String id = request.getParameter("customerId");
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(401);
			return;
		}
		String currentUsername = (String)session.getAttribute("username");
		String currentId = (String)session.getAttribute("customerId");
		try {
			//Delete row in DB if user has ADMIN
			//rights and and ID is entered.
			if ((boolean)session.getAttribute("isAdmin")) {
				if (id != null) {
					response.getWriter().append("Row at ID#: " + id + " has been deleted.");
					customerDAO.deleteCustomer(Integer.parseInt(id));
					response.setContentType("application/json");
					logger.info(currentUsername + " successfully deleted a row in Customers table.");
					response.setStatus(200);
					response.getWriter().flush();
					response.getWriter().close();
				}
			} else {
				//Set status for unauthorized user.
				response.setStatus(401);
				response.getWriter().print("You don't have authorization to modify data.");
				logger.info(currentUsername + " failed to delete row from Customers table.");
			}
		} catch (IOException e) {
			response.setStatus(400);
			e.printStackTrace();
		}
    }

}
