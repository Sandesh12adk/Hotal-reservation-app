package com.example.hotelreversation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.sql.*;

public class DeleteReservation {
    TextField customerNameField;
    TextField roomNumberField;
    private Connection connection;
    private final String url = "jdbc:mysql://localhost:3306/hotelinfo";
    private final String user = "Admin";
    private final String password = "pass123";
    private SecondScene secondscene;

   public DeleteReservation(SecondScene secondscene){
       this.secondscene= secondscene;
   }
    public GridPane Delete() {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setVgap(20);
        gridPane.setHgap(10);

        Label roomNumberLabel = new Label("Room Number:");
        roomNumberLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        GridPane.setConstraints(roomNumberLabel, 0, 0);

        roomNumberField = new TextField();
        GridPane.setConstraints(roomNumberField, 1, 0);

        Label customerNameLabel = new Label("Customer Name:");
        customerNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        GridPane.setConstraints(customerNameLabel, 0, 1);

        customerNameField = new TextField();
        GridPane.setConstraints(customerNameField, 1, 1);

        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-background-radius: 10; -fx-padding:" +
                " 4px 8px 4px 8px; -fx-opacity: 0.7;");
        deleteButton.setTextFill(Color.WHITE);
        GridPane.setConstraints(deleteButton, 1, 2);
        deleteButton.setOnAction(event -> {
            String roomNumberText = roomNumberField.getText().trim();
            if (!roomNumberText.isEmpty()) {
                try {
                    int roomNumber = Integer.parseInt(roomNumberText);
                    String customerName = customerNameField.getText().trim();
                    deleteReservation(roomNumber, customerName);
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Room Number", "Room Number must be a valid integer.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Missing Room Number", "Room Number cannot be empty.");
            }
        });
        Button backbutton = new Button("Go Back");
        backbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (secondscene != null) {
                    secondscene.BackfromDeletereservation();
                } else {
                    System.out.println("SecondScene is not initialized.");
                }
            }
        });
        backbutton.setStyle("-fx-background-color: blue; -fx-text-fill: white;" +
                " -fx-background-radius: 30; -fx-padding: 4px 10px 4px 10px; -fx-opacity: 0.7;");
        GridPane.setConstraints(backbutton, 1, 3); // Set backbutton to first column, fourth row

        gridPane.getChildren().addAll(roomNumberLabel, roomNumberField, customerNameLabel,
                customerNameField, deleteButton,backbutton);

        return gridPane;
    }

    private void deleteReservation(int roomNumber, String customerName) {
        try {
            connection = DriverManager.getConnection(url, user, password);
            String query = "DELETE FROM hotelinfo WHERE room_no = ? AND guest_name = ?";

            PreparedStatement pstmt = connection.prepareStatement(query);
            Statement stmt= connection.createStatement();
            String query2 = "INSERT INTO room_available(Room_no) VALUES('" + roomNumber + "');";
            pstmt.setInt(1, roomNumber);
            pstmt.setString(2, customerName);
            new CheckOuts().checkOuts(roomNumber);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                customerNameField.setText("");
                roomNumberField.setText("");
                stmt.executeUpdate(query2);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Reservation deleted successfully.");
                // Update room availability (example: insert logic here)
            } else {
                showAlert(Alert.AlertType.INFORMATION, "No Reservation Found", "No reservation " +
                        "found matching the provided details.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error deleting reservation: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Connection Error", "Error closing connection: " + e.getMessage());
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
