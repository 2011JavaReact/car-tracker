package com.revature.carTracker.test;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.Driver;

/**
 * Tests for an occupied DB connection to a specified remote DB.
 * @author Frank Johnson III
 */
public class JDBCTest {

	Connection conn = null;
	String url = System.getenv("DB_URL");
	String username = System.getenv("DB_USERNAME");
	String password = System.getenv("DB_PASSWORD");

	@Before
	public void connectionSetUp() throws Exception {
		DriverManager.registerDriver(new Driver());
		conn = DriverManager.getConnection(url, username, password);
	}

	@After
	public void closeConnection() throws Exception {
		conn.close();
	}

	@Test
	public void testConnection() {
		assertNotNull(conn);
	}
}
