package com.management.hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class HospitalManagement {

	public static void main(String[] args) {
		
		try {
			Connection connection = ConnectionProvider.getConnection();
			Scanner scanner =  new Scanner(System.in);
			Patient patient = new Patient(connection, scanner);
			Doctor doctor = new Doctor(connection);
			
			while(true) {
				System.out.println("+--------HOSPITAL MANAGEMENT SYSTEM---------+");
				System.out.println("1. Add patient");
				System.out.println("2. View Patients");
				System.out.println("3. View Doctors");
				System.out.println("4. Book Appointment");
				System.out.println("5. Exit");
				System.out.println("Enter your choice: ");
			
				int choice = scanner.nextInt();
				switch(choice) {
					case 1:
						patient.addPatient();
						System.out.println();
						break;
					case 2:
						patient.viewPatient();
						System.out.println();
						break;
					case 3:
						doctor.viewDoctors();
						System.out.println();
						break;
					case 4:
						bookAppointment(patient, doctor, connection, scanner);
						System.out.println();
						break;
					case 5:
						return;
					default:
						System.out.println("Enter valid choice...");
						break;	
				}
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
		System.out.println("Enter patient id: ");
		int patientId = scanner.nextInt();
		
		System.out.println("Enter Doctor id: ");
		int doctorId = scanner.nextInt();
		
		System.out.println("Enter appointment date (YYYY-MM-DD)");
		String appointmentDate = scanner.next();
		
		if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)) {
			if(checkDoctorAvailability(doctorId, appointmentDate, connection)) {
				String query = "INSERT INTO appointments(patientId, doctorId, appointmentDate) VALUES(?, ?, ?)";
				try {
					PreparedStatement preparedStatement = connection.prepareStatement(query);
					preparedStatement.setInt(1, patientId);
					preparedStatement.setInt(2, doctorId);
					preparedStatement.setString(3, appointmentDate);
					
					int rowAffected = preparedStatement.executeUpdate();
					if(rowAffected > 0) {
						System.out.println("appoinment booked...");
					}
					else {
						System.out.println("failed to book appointment...");
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			else {
				System.out.println("Doctor not availale on this date...");
			}
		}
		else {
			System.out.println("Either doctor or patient doesn't exist...");
		}
	}
	public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
		String query = "SELECT COUNT(*) FROM appointments WHERE doctorId=? AND appointmentDate=?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, doctorId);
			preparedStatement.setString(2, appointmentDate);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				int count = resultSet.getInt(1);
				if(count == 0) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}

















