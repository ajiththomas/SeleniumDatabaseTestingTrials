package dbtesting;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DBTesting {

	static Connection conn = null; // connection object
	private static Statement statement; // statement object
	private static ResultSet result; // result set object

	private static String DB_URL="jdbc:mysql://localhost:3310/selenium_test?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false";
	
	private static String DB_USER = "root";
	// oracle DB URL -> jdbc:oracle:thin:@localhost:1521/sid
	private static String DB_Password = "password";
	private static String driver = "com.mysql.jdbc.Driver";
	// oracle driver -> oracle.jdbc.driver.OracleDriver

	@BeforeClass
	public static void beforeClass() {
		// properties for connection to database
		Properties props = new Properties();
		props.setProperty("user", DB_USER);
		props.setProperty("password", DB_Password);

		try {

			Class.forName(driver).newInstance();
			System.out.println("Connecting to database...");

			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_Password);
			System.out.println("Connected to database");
			System.out.println("Creating statement..");
			statement = conn.createStatement();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void test1() throws UnsupportedEncodingException, SQLException {
		
		
		String query = "insert into user_info (user_id,first_name,last_name,city)"
				+ " values (4,'Angel', 'John', 'Sydney')";

		statement.executeUpdate(query);
		
	}

	@Test
	public void test2() {
		String query = "";
		query = "select * from user_info";
		try {

			result = statement.executeQuery(query);
			while (result.next()) {
				int id = result.getInt("user_id");

				String first = new String(result.getString("first_name").getBytes(), "UTF-8");

				String last = new String(result.getString("last_name").getBytes(), "UTF-8");

				String city = new String(result.getString("city").getBytes(), "UTF-8");

				System.out.println("ID:" + id);
				System.out.println("First Name:" + first);
				System.out.println("Last Name:" + last);
				System.out.println("City:" + city);
			}
			result.close();
		} catch (SQLException se) {
			se.printStackTrace();

		} catch (Exception te) {
			te.printStackTrace();
		}

	}

	@AfterClass
	public static void afterClass() {

		try {

			if (result != null) {
				result.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (conn != null) {
				conn.close();
			}

		} catch (Exception ae) {
			ae.printStackTrace();
		}
	}

}
