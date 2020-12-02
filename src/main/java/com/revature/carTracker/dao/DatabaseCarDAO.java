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
import com.revature.carTracker.servlet.CustomerServlet;
import com.revature.carTracker.util.JDBC;

/**
 * Handles database table info. retrieval and
 * manipulation.
 * @author Frank Johnson III
 *
 */
public class DatabaseCarDAO {
	Logger logger = Logger.getLogger(CustomerServlet.class);
	/**
	 * Retrieves all data from Inventory table.
	 * @return ArrayList<Car>, null
	 */
	public ArrayList<Car> getAllCars() {
		String sqlQuery = "SELECT * " 
						+ "FROM Inventory";
		ArrayList<Car> cars = new ArrayList<>();
		try (Connection sqlConn = JDBC.getConnection()){
			Statement stmt = sqlConn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlQuery);
			logger.info("Connection made to external database " + sqlConn);
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				int price = rs.getInt(3);
				Car car = new Car(id, name, price);
				cars.add(car);
				sqlConn.close();
			}
			return cars;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Adds a row to the Inventory table.
	 * @param name, price
	 * @return Car, null 
	 */
	public Car addCar(String name, int price) {
		String sqlQuery = "INSERT INTO Inventory (name, price) "
						+ "VALUES (?, ?)";
		try (Connection sqlConn = JDBC.getConnection()) {
			sqlConn.setAutoCommit(false);
			PreparedStatement pstmt = sqlConn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, name);
			pstmt.setInt(2, price);
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows != 1) {
				throw new SQLException("Could not insert new car. No rows affected.");
			}
			int autoId = 0;
			ResultSet genKeys = pstmt.getGeneratedKeys();
			if (genKeys.next()) {
				autoId = genKeys.getInt(1);
			} else {
				throw new SQLException("Could not insert new car. ID not generated.");
			}
			sqlConn.commit();
			sqlConn.close();
			return new Car(autoId, name, price);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * Returns all rows with the matching name.
	 * @param name
	 * @return ArrayList<Car>, null
	 */
	public ArrayList<Car> getCarByName(String name) {
		String sqlQuery = "SELECT * "
							+ "FROM Inventory "
							+ "WHERE name = ? ";
		try (Connection sqlConn = JDBC.getConnection()){
			ArrayList<Car> cars = new ArrayList<>();
			PreparedStatement pstmt = sqlConn.prepareStatement(sqlQuery);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int carId = rs.getInt(1);
				String carName = rs.getString(2);
				int price = rs.getInt(3);
				Car car = new Car(carId, carName, price);
				cars.add(car);
				sqlConn.close();
			} 
			return cars;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	
	/**
	 * Returns all rows with the matching ID.
	 * @param id
	 * @return ArrayList<Car>, null
	 */
	public ArrayList<Car> getCarById(int id) {
		String sqlQuery = "SELECT * "
					+ "FROM Inventory "
					+ "WHERE car_id = ?";
		try (Connection sqlConn = JDBC.getConnection()){
			ArrayList<Car> cars = new ArrayList<>();
			PreparedStatement stmt = sqlConn.prepareStatement(sqlQuery);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int carId = rs.getInt(1);
				String name = rs.getString(2);
				int price = rs.getInt(3);
				Car car = new Car(carId, name, price);
				cars.add(car);
				sqlConn.close();
			} 
			return cars;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Deletes a row with the matching ID.
	 * @param id
	 * @return ArrayList<Car>, null
	 */
	public void deleteCar(int id) {
			String sqlQuery = "DELETE FROM Inventory "
							+ "WHERE car_id = ? ";
			try (Connection sqlConn = JDBC.getConnection()) {
				sqlConn.setAutoCommit(false);
				PreparedStatement pstmt = sqlConn.prepareStatement(sqlQuery);
				pstmt.setInt(1, id);
				int affectedRows = pstmt.executeUpdate();
				if (affectedRows != 1) {
					throw new SQLException("Could not remove car. No rows affected.");
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