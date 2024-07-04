package com.example.hotelreversation;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.sql.*;
import java.util.ArrayList;

public class AvailableRooms {
    private Connection connection = null;
    private Statement stmt = null;
    private ResultSet resultSet = null;
    private final String url = "jdbc:mysql://localhost:3306/hotelinfo";
    private final String user = "Admin";
    private final String password = "pass123";
    private ToggleGroup toggleGroup;
    ArrayList<Integer> availableRooms = new ArrayList<>();

    // Establishes the database connection
    public void setConnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection established successfully");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Retrieves available room numbers from the database
    public ArrayList<Integer> getAvailableRooms() {
        try {
            stmt = connection.createStatement();
            String query = "SELECT * FROM Room_available";
            resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                int room = resultSet.getInt(1);
                System.out.println("Room number: " + room);
                availableRooms.add(room);
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
        } finally {
            closeResources();
        }
        return availableRooms;
    }

    // Closes all database resources
    private void closeResources() {
        try {
            if (resultSet != null) resultSet.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }

    // Retrieves available rooms and displays them as RadioButtons in an HBox
    public FlowPane displayAvailableRooms() {
        setConnection();
        ArrayList<Integer> availableRooms = getAvailableRooms();
        toggleGroup = new ToggleGroup();
        FlowPane flowPane= new FlowPane(); /////////yesma xa sabain ui elements haru
        flowPane.setHgap(20); // Horizontal gap between nodes
        flowPane.setVgap(10); // Vertical gap between nodes

        for (Integer roomNumber : availableRooms) {
            RadioButton radioButton = new RadioButton(String.valueOf(roomNumber));
            Font font = Font.font("Arial", 18); // Example: Arial font, size 14
            radioButton.setFont(font);
            radioButton.setUserData(roomNumber);
            radioButton.setToggleGroup(toggleGroup); // Group buttons in ToggleGroup
            flowPane.getChildren().add(radioButton);
        }
        return flowPane;
    }
    public ArrayList<Integer> avairooms(){
        setConnection();
        return getAvailableRooms();
    }
}
