package com.management.hospital;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider {
	private static Connection connection;
	private static final String url = "jdbc:mysql://localhost:3306/hospital_management";
	private static final String username = "root";
	private static final String password = "Admin@123";
	
	public static Connection getConnection() {
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return connection;
	}
}
