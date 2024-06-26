package com.management.hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Patient {
	
	//Connection interface
	private Connection connection;
	private Scanner scanner;
	private PreparedStatement preparedStatement;
	
	//creating constructor
	public Patient(Connection connection, Scanner scanner) {
		this.connection = connection;
		this.scanner = scanner;
	}
	
	//method to add patient details
	public void addPatient() {
		System.out.println("Enter patient name: ");
		String patientName = scanner.next();
		System.out.println("Enter patient age:");
		String patientAge = scanner.next();
		System.out.println("Enter patient gender: ");
		String patientGender = scanner.next();
		
		//connection to database
		try {
			String query = "INSERT INTO patients(patientName, patientAge, gender) VALUES(?, ?, ?)";
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, patientName);
			preparedStatement.setString(2, patientAge);
			preparedStatement.setString(3, patientGender);
			
			int affectedRows = preparedStatement.executeUpdate();
			if(affectedRows>0) {
				System.out.println("Patient details added successfully...");
			}
			else {
				System.out.println("Failed to add patient details!!!");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void viewPatient() {
		try {
			String query = "SELECT * FROM patients";
			preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			System.out.println("Patients table");
			System.out.println("+-------------+-------------------------------+--------------+-----------------+");
			System.out.println("| patientId   | patientName                   | patientAge   | gender          |");
			System.out.println("+-------------+-------------------------------+--------------+-----------------+");
			
			while(resultSet.next()) {
				int patientId = resultSet.getInt("id");
				String patientName = resultSet.getString("patientName");
				String patientAge = resultSet.getString("patientAge");
				String gender = resultSet.getString("gender");
				
				System.out.printf("| %-12s| %-30s| %-13s| %-16s| \n", patientId, patientName, patientAge, gender);
				System.out.println("+-------------+-------------------------------+--------------+-----------------+");
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean getPatientById(int id) {
		String query = "SELECT * FROM patients WHERE id=?";
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
