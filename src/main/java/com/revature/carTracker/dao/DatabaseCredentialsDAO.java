package com.revature.carTracker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.carTracker.util.JDBC;

public class DatabaseCredentialsDAO {
	
	public String getPassword(String username) {
		String sqlQuery = "SELECT password "
				+ "FROM Credentials "
				+ "WHERE username = ?";
		try (Connection sqlConn = JDBC.getConnection()){
			PreparedStatement stmt = sqlConn.prepareStatement(sqlQuery);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			String name = ""; 
			if (rs.next()) {
				name = rs.getString("password");
				sqlConn.close();
			} 
			return name;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean getIsAdmin(String username) {
		String sqlQuery = "SELECT isAdmin "
				+ "FROM Credentials "
				+ "WHERE username = ?";
		try (Connection sqlConn = JDBC.getConnection()){
			PreparedStatement stmt = sqlConn.prepareStatement(sqlQuery);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			boolean admin = false; 
			if (rs.next()) {
				admin = rs.getBoolean(1);
				sqlConn.close();
			} 
			return admin;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}


