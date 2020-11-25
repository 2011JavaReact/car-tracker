import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.postgresql.Driver;

import com.revature.carTracker.model.Car;
import com.revature.carTracker.servlet.CustomerServlet;
import com.revature.carTracker.util.JDBC;

public class Main {

	public static void main(String[] args) {
		
		/*DB Test */
		
//		try (Connection sqlConn = JDBC.getConnection()){
//			String sqlQuery = "SELECT * " 
//							+ "FROM Inventory";
//			
//			ArrayList<Car> cars = new ArrayList<>();
//			
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
//				
//				System.out.println(cars);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
		/*DB Connection Test*/
		
		String url = System.getenv("DB_URL");
		String username = System.getenv("DB_USERNAME");
		String password = System.getenv("DB_PASSWORD");
		
		Connection connection = null;
		
		try {
			DriverManager.registerDriver(new Driver());
			connection = DriverManager.getConnection(url, username, password);
			System.out.println(connection);
			Logger logger = Logger.getLogger(CustomerServlet.class);
			logger.debug("Connected to remote databse.");
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		

	}

}
