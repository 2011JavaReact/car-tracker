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
		try (Connection sqlConn = JDBC.getConnection()){
			ArrayList<Customer> customers = new ArrayList<>();
			Statement stmt = sqlConn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlQuery);
			logger.info("Connection made to external database " + sqlConn);
			while (rs.next()) {
				int id = rs.getInt(1);
				int carId = rs.getInt(2);
				String name = rs.getString(3); 
				Customer customer = new Customer(id, carId, name);
				customers.add(customer);
				sqlConn.close();
			}
			return customers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Returns all rows with the matching name.
	 * @param name
	 * @return ArrayList<Customer>, null
	 */
	public ArrayList<Customer> getCustomerByName(String name) {
		String sqlQuery = "SELECT * "
						+ "FROM Customers "
						+ "WHERE name = ? ";
		try (Connection sqlConn = JDBC.getConnection()){
			ArrayList<Customer> customers = new ArrayList<>();
			PreparedStatement pstmt = sqlConn.prepareStatement(sqlQuery);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				int carId = rs.getInt(2);
				String customerName = rs.getString(3);
				Customer customer = new Customer(carId, carId, customerName);
				customers.add(customer);
				sqlConn.close();
			} 
			return customers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Returns all rows with the matching ID.
	 * @param id
	 * @return ArrayList<Customer>, null
	 */
	public ArrayList<Customer> getCustomerById(int id) {
		String sqlQuery = "SELECT * "
					+ "FROM Customers "
					+ "WHERE customer_id = ?";
		try (Connection sqlConn = JDBC.getConnection()){
			ArrayList<Customer> customers = new ArrayList<>();
			PreparedStatement stmt = sqlConn.prepareStatement(sqlQuery);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int customerId = rs.getInt(1);
				int carId = rs.getInt(2);
				String name = rs.getString(3);
				Customer customer = new Customer(customerId, carId, name);
				customers.add(customer);
				sqlConn.close();
			} 
			return customers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Adds a row to the Customers table.
	 * @param carId, name
	 * @return Customer, null 
	 */
	public Customer addCustomer(int carId, String name) {
		String sqlQuery = "INSERT INTO Customers (car_id, name) "
						+ "VALUES (?, ?)";
		try (Connection sqlConn = JDBC.getConnection()) {
			sqlConn.setAutoCommit(false);
			PreparedStatement pstmt = sqlConn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, carId);
			pstmt.setString(2, name);
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows != 1) {
				throw new SQLException("Could not insert new customer. No rows affected.");
			}
			int autoId = 0;
			ResultSet genKeys = pstmt.getGeneratedKeys();
			if (genKeys.next()) {
				autoId = genKeys.getInt(1);
			} else {
				throw new SQLException("Could not insert new customer. ID not generated.");
			}
			sqlConn.commit();
			sqlConn.close();
			return new Customer(autoId, carId, name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteCustomer(int id) {
		String sqlQuery = "DELETE FROM Customers "
						+ "WHERE customer_id = ? ";
		try (Connection sqlConn = JDBC.getConnection()) {
			sqlConn.setAutoCommit(false);
			PreparedStatement pstmt = sqlConn.prepareStatement(sqlQuery);
			pstmt.setInt(1, id);
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows != 1) {
				throw new SQLException("Could not remove customer. No rows affected.");
			} else {
				System.out.println("Row at ID#: " + id + " has been deleted.");
			}
			sqlConn.commit();
			sqlConn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
