package com.revature.carTracker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.revature.carTracker.model.Car;
import com.revature.carTracker.model.Customer;
import com.revature.carTracker.servlet.CarServlet;
import com.revature.carTracker.util.JDBC;

public class DatabaseCustomerDAO {
	Logger logger = Logger.getLogger(DatabaseCustomerDAO.class);
	
	/**
	 * Retrieves all data from Customers table.
	 * @return ArrayList<Customer>
	 */
	public ArrayList<Customer> getAllCustomers() {
		String sqlQuery = "SELECT * " 
						+ "FROM Customers";
		
		ArrayList<Customer> customers = new ArrayList<>();
		
		try (Connection sqlConn = JDBC.getConnection()){
			Statement stmt = sqlConn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlQuery);
			
			logger.info("Connection made to external database." + " Database: " + sqlConn);
			
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(3); //Skip to column 3 to get names.
				
				Customer customer = new Customer(id, name);
				
				customers.add(customer);
				
				sqlConn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return customers;
	}
}
