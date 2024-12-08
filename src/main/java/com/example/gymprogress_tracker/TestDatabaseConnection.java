//package com.example.gymprogress_tracker;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class TestDatabaseConnection {
//    public static void main(String[] args) {
//        try {
//            // Zorg ervoor dat je de juiste URL, gebruikersnaam en wachtwoord gebruikt
//            String url = "jdbc:postgresql://localhost:5432/gym_progress";
//            String username = "postgres"; // Vervang door je eigen gebruikersnaam
//            String password = "Stripess"; // Vervang door je eigen wachtwoord
//
//            // Maak de verbinding
//            Connection connection = DriverManager.getConnection(url, username, password);
//            System.out.println("Connection successful!"); // Als de verbinding werkt
//        } catch (SQLException e) {
//            System.out.println("Failed to connect to the database.");
//            e.printStackTrace(); // Geeft gedetailleerde foutmelding
//        }
//    }
//}
//
