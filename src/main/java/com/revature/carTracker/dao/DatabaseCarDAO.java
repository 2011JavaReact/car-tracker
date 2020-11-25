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
	 * @return ArrayList<Car>
	 */
	public ArrayList<Car> getAllCars() {
		String sqlQuery = "SELECT * " 
						+ "FROM Inventory";
		
		ArrayList<Car> cars = new ArrayList<>();
		
		try (Connection sqlConn = JDBC.getConnection()){
			Statement stmt = sqlConn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlQuery);
			
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String price = rs.getString(3);
				
				Car car = new Car(id, name, price);
				
				cars.add(car);
				logger.debug("Connection made to external database.");
				sqlConn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cars;
	}
	
	/**
	 * Adds a row to the Inventory table.
	 * @param carInfo
	 * @return Car Object
	 */
//	public Car addCar(String name, String price) {
//		try (Connection sqlConn = JDBC.getConnection()) {
//			sqlConn.setAutoCommit(false);
//			
//			String sqlQuery = "INSERT INTO Inventory"
//						+ "(name, price) "
//						+ "VALUES "
//						+ "?, ?";
//		
//			PreparedStatement pstmt = sqlConn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
//			
//			pstmt.setString(1, name);
//			pstmt.setString(2, price);
//			
//			if (pstmt.executeUpdate() != 1) {
//				throw new SQLException("Could not insert new car. No rows affected.");
//			}
//			
//			int autoId = 0;
//			ResultSet genKeys = pstmt.getGeneratedKeys();
//			if (genKeys.next()) {
//				autoId = genKeys.getInt(1);
//			} else {
//				throw new SQLException("Could not insert new car. ID not generated.");
//			}
//			
//			sqlConn.commit();
//			
//			return new Car(autoId, name, price);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return null;
//		
//	}
//}
	
	/**
	 * Returns all rows in a DB that match the entered
	 * name constraint.
	 * @param name
	 * @return ArrayList<Car>, NULL
	 */
//	public Car getCarByName(String name) {
//		
//		
//		try (Connection sqlConn = JDBC.getConnection()){
//			String sqlQuery = "SELECT * "
//							+ "FROM Inventory i "
//							+ "WHERE i.name = ? ";
//			
//			PreparedStatement pstmt = sqlConn.prepareStatement(sqlQuery);
//			pstmt.setString(1, name);
//			ResultSet rs = pstmt.executeQuery();
//			
//			if (rs.next()) {
//				int id = rs.getInt(1);
//				String carName = rs.getString(2);
//				
//				return new Car(id, carName);
//			} else {
//				return null;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//			
//		return null;
//	}
//}

	
	/**
	 * Returns all rowsin a DB that match the entered
	 * id constraint.
	 * @param id
	 * @return ArrayList<Car>, NULL
	 */
//	public ArrayList<Car> getCarById(int id) {
//		String sqlQuery = "SELECT * "
//					+ "FROM Inventory "
//					+ "WHERE car_id = ? "
//					+ "LIMIT 1";
//		
//		try (Connection sqlConn = JDBC.getConnection()){
//	
//			ArrayList<Car> cars = new ArrayList<>();
//			
//			PreparedStatement pstmt = sqlConn.prepareStatement(sqlQuery);
//			pstmt.setInt(1, id);
//			ResultSet rs = pstmt.executeQuery();
//			
//			while (rs.next()) {
//				String name = rs.getString(2);
//				int price = rs.getInt(3);
//				
//				Car car = new Car(rs.getInt(1), name, price);
//				
//				cars.add(car);
//				
//				return cars;
//			} 
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//			
//		return null;
//	}
	
//	public ArrayList<Car> getAllTables() {
//		String sqlQuery = "SELECT *" 
//						+ "FROM Inventory i"
//						+ "INNER JOIN Customers c"
//						+ "ON c.car_id = i.car_id"
//						+ "ORDER BY i.car_id";
//		
//		ArrayList<Car> cars = new ArrayList<>();
//		
//		try (Connection sqlConn = JDBC.getConnection()){
//			Statement stmt = sqlConn.createStatement();
//			ResultSet rs = stmt.executeQuery(sqlQuery);
//			
//			while (rs.next()) {
//				int id = rs.getInt(1);
//				String name = rs.getString(2);
//				int price = rs.getInt(3);
//			
//				Car car = new Car(id, name, price);
//				
//				cars.add(car);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return cars;
//	}
}
