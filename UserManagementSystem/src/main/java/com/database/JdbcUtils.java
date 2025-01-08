package com.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException, IOException {
		FileInputStream fis = new FileInputStream(
				"D:\\Pratik\\Servlet and JSP\\UserManagementSystem\\src\\main\\java\\com\\properties\\applications.properties");
		Properties properties = new Properties();
		properties.load(fis);

		Connection connection = DriverManager.getConnection(properties.getProperty("url"),
				properties.getProperty("userName"), properties.getProperty("password"));
		return connection;
	}

	public static void closeConnection(Connection connection, PreparedStatement prepareStatement, ResultSet resultSet)
			throws SQLException {
		if (connection != null) {
			connection.close();
		}
		if (prepareStatement != null) {
			prepareStatement.close();
		}
		if (resultSet != null) {
			resultSet.close();
		}
	}
}
