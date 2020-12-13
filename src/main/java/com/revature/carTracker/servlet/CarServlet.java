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
import com.revature.carTracker.model.AdminList;
import com.revature.carTracker.model.Car;
import com.revature.carTracker.model.Customer;

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

	/**Retrieves table(s) from DB.
	 * Can retrieve specific data by
	 * specified criteria.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//Set endpoint parameters and 
    	//create HTTP session.
    	logger.info("Executed HTTP GET request.");
		String name = request.getParameter("carName");
		String id = request.getParameter("carId");
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(401);
			return;
		}
		String currentUsername = (String)session.getAttribute("username");
		ArrayList<Car> cars = carDAO.getAllCars();
		ArrayList<Customer> customers = customerDAO.getAllCustomers();
		String jsonString = objectMapper.writeValueAsString(cars);
		String jsonString2 = objectMapper.writeValueAsString(customers);
		AdminList admin = new AdminList(cars, customers);
		String adminList = objectMapper.writeValueAsString(admin);
		try {
			//Return data by ID#.
			if (id != null) {
				ArrayList<Car> c = carDAO.getCarById(Integer.parseInt(id));
				response.setContentType("application/json");
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
				response.getWriter().append(objectMapper.writeValueAsString(c));
				response.setStatus(200);
				response.getWriter().flush();
				response.getWriter().close();
			}
			//Retrieve table for ADMIN.
			if ((boolean)session.getAttribute("isAdmin")) {
				response.getWriter().append(adminList);
//				response.getWriter().append(jsonString);
//				response.getWriter().append(jsonString2);
				response.setContentType("application/json");
				response.setStatus(200);
				response.getWriter().flush();
				response.getWriter().close();
			} else {
				//Retrieve table for STANDARD USER.
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

    /**Adds a row to the Inventory table.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//Set endpoint parameters and
    	//create HTTP session..
    	logger.info("Executed HTTP POST request.");
		String name = request.getParameter("carName");
		String price = request.getParameter("carPrice");
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(401);
			return;
		}
		String currentUsername = (String)session.getAttribute("username");
		String jsonString = objectMapper.writeValueAsString(carDAO.addCar(name, Integer.parseInt(price)));
		try {
			//Insert new data into DB if password is correct.
			if ((boolean)session.getAttribute("isAdmin")) {
				response.getWriter().append("Car added.");
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
		} catch (IOException e) {
			response.setStatus(400);
			e.printStackTrace();
		}
    }
    
    /**Deletes a row from the Inventory table.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
    	//Set endpoint parameters, 
    	//create HTTP session, and set attributes.
    	logger.info("Executed HTTP DELETE request.");
		String id = request.getParameter("carId");
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(401);
			return;
		}
		String currentUsername = (String)session.getAttribute("username");
		try {
			//Delete row in DB if user has ADMIN
			//rights and and ID is entered.
			if ((boolean)session.getAttribute("isAdmin")) {
				if (id != null) {
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
			
		} catch (IOException e) {
			response.setStatus(400);
			e.printStackTrace();
		}
    }
}


