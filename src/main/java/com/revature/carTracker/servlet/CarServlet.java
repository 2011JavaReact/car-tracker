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

	/**Retrieves table(s) from DB after user
	 * enters login criteria or retrieves
	 * data by specified criteria.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//Set endpoint parameters, 
    	//create HTTP session, and set attributes.
    	logger.info("Executed HTTP GET request.");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String name = request.getParameter("carName");
		String id = request.getParameter("carId");
		HttpSession session = request.getSession();
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		String currentUsername = (String)session.getAttribute("username");
		String currentPassword = (String)session.getAttribute("password");
		String jsonString = objectMapper.writeValueAsString(carDAO.getAllCars());
		String jsonString2 = objectMapper.writeValueAsString(customerDAO.getAllCustomers());

		try {
			//Return error page if credentials are empty.
			if (currentUsername == null || currentPassword == null) {
				response.setStatus(400);
				response.getWriter().print("Please enter a username and password.");
			} 
			//Return data by ID#.
			if (id != null) {
				ArrayList<Car> c = carDAO.getCarById(Integer.parseInt(id));
				response.setContentType("application/json");
				response.getWriter().append("Welcome " + currentUsername + "!");
				response.getWriter().append(objectMapper.writeValueAsString(c));
				//Set successful HTTP status and release resources.
				response.setStatus(200);
				response.getWriter().flush();
				response.getWriter().close();
			}
			//Return data by name.
			if (name != null) {
				ArrayList<Car> c = carDAO.getCarByName(name);
				response.setContentType("application/json");
				response.getWriter().append("Welcome " + currentUsername + "!");
				response.getWriter().append(objectMapper.writeValueAsString(c));
				response.setStatus(200);
				response.getWriter().flush();
				response.getWriter().close();
			}
			//Retrieve table for ADMIN.
			if (currentPassword.equals("admin")) {
				response.getWriter().append("Welcome " + currentUsername + "!");
				response.getWriter().append(jsonString);
				response.getWriter().append(jsonString2);
				response.setContentType("application/json");
				response.setStatus(200);
				response.getWriter().flush();
				response.getWriter().close();
			} else {
				//Retrieve table for STANDARD USER.
				response.getWriter().append("Welcome " + currentUsername + "!");
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

    /**Adds a row to the Inventory table after user
	 * enters login criteria.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//Set endpoint parameters, 
    	//create HTTP session, and set attributes.
    	logger.info("Executed HTTP POST request.");
    	String username = request.getParameter("username");
		String password = request.getParameter("password");
		String name = request.getParameter("carName");
		String price = request.getParameter("carPrice");
		HttpSession session = request.getSession();
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		session.setAttribute("carName", name);
		session.setAttribute("carPrice", price);
		String currentUsername = (String)session.getAttribute("username");
		String currentPassword = (String)session.getAttribute("password");
		String jsonString = objectMapper.writeValueAsString(carDAO.addCar(name, Integer.parseInt(price)));
		
		try {
			//Return error page if login is 
			//left empty.
			if (currentUsername == null || currentPassword == null) {
				response.setStatus(400);
				response.getWriter().print("Please enter a username and password.");
			} else {
				//Insert new data into DB if password is correct.
				if (currentPassword.equals("admin")) {
					logger.info(currentUsername + " logged in as: ADMIN.");
					response.getWriter().append("Welcome " + currentUsername + "!");
					response.getWriter().append(jsonString);
					response.setContentType("application/json");
					logger.info(currentUsername + " successfully added new data to Inventory table.");
					response.setStatus(200);
					response.getWriter().flush();
					response.getWriter().close();
				} else {
					//Set status for unauthorized user.
					response.setStatus(401);
					response.getWriter().print("You don't have authorization to modify data.");
					logger.info(currentUsername + " failed to add data to Inventory table.");
				}
			}
		} catch (IOException e) {
			response.setStatus(400);
			e.printStackTrace();
		}
    }
    
    /**Deletes a row from the Inventory table after user
	 * enters login criteria.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
    	//Set endpoint parameters, 
    	//create HTTP session, and set attributes.
    	logger.info("Executed HTTP DELETE request.");
    	String username = request.getParameter("username");
		String password = request.getParameter("password");
		String id = request.getParameter("carId");
		HttpSession session = request.getSession();
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		session.setAttribute("carId", id);
		String currentUsername = (String)session.getAttribute("username");
		String currentPassword = (String)session.getAttribute("password");
		String currentId = (String)session.getAttribute("carId");
		
		try {
			//Return error page if login is 
			//left empty.
			if (currentUsername == null || currentPassword == null) {
				response.setStatus(400);
				response.getWriter().print("Please enter a username and password.");
			} else {
				//Delete row in DB if user has ADMIN
				//rights and and ID is entered.
				if (currentPassword.equals("admin")) {
					if (id != null) {
						logger.info(currentUsername + " logged in as: ADMIN.");
						response.getWriter().append("Welcome " + currentUsername + "!\n");
						response.getWriter().append("Row at ID#: " + id + " has been deleted.");
						carDAO.deleteCar(Integer.parseInt(id));
						response.setContentType("application/json");
						logger.info(currentUsername + " successfully deleted a row in Inventory table.");
						response.setStatus(200);
						response.getWriter().flush();
						response.getWriter().close();
					}
				} else {
					//Set status for unauthorized user.
					response.setStatus(401);
					response.getWriter().print("You don't have authorization to modify data.");
					logger.info(currentUsername + " failed to delete row from Inventory table.");
				}
			}
		} catch (IOException e) {
			response.setStatus(400);
			e.printStackTrace();
		}
    }
}


