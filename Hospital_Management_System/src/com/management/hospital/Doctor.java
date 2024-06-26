package com.management.hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Doctor {
	//Connection interface
	private Connection connection;
	private PreparedStatement preparedStatement;
	
	//creating constructor
	public Doctor(Connection connection) {
		this.connection = connection;
	}
	
	public void viewDoctors() {
		try {
			String query = "SELECT * FROM doctors";
			preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			System.out.println("Doctors table");
			System.out.println("+-------------+-------------------------------+--------------------------------+");
			System.out.println("| DoctorId    | doctorName                    | specialization                 |");
			System.out.println("+-------------+-------------------------------+--------------------------------+");
			
			while(resultSet.next()) {
				int doctorId = resultSet.getInt("doctorId");
				String doctorName = resultSet.getString("doctorName");
				String specialization = resultSet.getString("specialization");
				
				System.out.printf("| %-12s| %-30s| %-31s| \n", doctorId, doctorName, specialization);
				System.out.println("+-------------+-------------------------------+--------------------------------+");
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean getDoctorById(int id) {
		String query = "SELECT * FROM doctors WHERE doctorId=?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
