package com.example.hotelreversation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.sql.*;

public class VIewReservations {
    private final String url = "jdbc:mysql://localhost:3306/hotelinfo";
    private final String user = "Admin";
    private final String password = "pass123";
    private final String query = "SELECT * FROM hotelinfo;";
    private GridPane gridPane;
    private ScrollPane scrollPane;
    private Label headerLabel;
    private StackPane stackPane;
    private SecondScene secondScene; // Reference to SecondScene

    public VIewReservations() {
        gridPane = new GridPane();
       // gridPane.setPadding(new Insets());
        gridPane.setHgap(10);
       // gridPane.setVgap(0.5);


        scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
        scrollPane.setFitToWidth(true); // Optional: ensure the scroll pane resizes with the window
        scrollPane.setFitToHeight(true); // Optional: ensure the scroll pane resizes with the window
        scrollPane.setPrefSize(600, 400); // Set preferred size as needed
    }
    public void setSecondScene(SecondScene secondScene){
           this.secondScene= secondScene;
    }

    public StackPane viewReservation() {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(query)) {

            // Display column headers
            String[] headers = {"Registration ID    ", "Guest Name    ",
                    "Guest Address    ", "Guest Mobile No   ", "Room No    ", "Check In Date"};


            for (int i = 0; i < headers.length; i++) {
                 headerLabel = new Label(headers[i]);
                headerLabel.setStyle(
                        "-fx-font-family: Arial; " +          // Sets the font family to Arial
                                "-fx-font-size: 18px; " +            // Sets the font size to 16 pixels
                                "-fx-text-fill: green; " +             // Sets the text color to red
                                "-fx-font-weight: bold; " +          // Sets the font weight to bold
                                "-fx-alignment: center-left; " +     // Aligns the text to center-left
                                "-fx-padding: 10px;");                // Sets padding around the text
                gridPane.add(headerLabel, i, 0);
            }
            int row = 1;
            // Display each reservation record
            while (resultSet.next()) {
                int regId = resultSet.getInt("registration_id");
                String guestFirstName = resultSet.getString("guest_name");
                String guestLastName = resultSet.getString("guest_lastname");
                String address = resultSet.getString("guest_address");
                String mobileNo = resultSet.getString("guest_mobile_no");
                int roomNo = resultSet.getInt("room_no");
                Timestamp timestamp = resultSet.getTimestamp("reservation_date");

                // Create labels for each piece of information

// CSS style string
                String labelStyle = "-fx-font-family: Arial; " +  // Sets the font family to Arial
                        "-fx-font-size: 16px; " +    // Sets the font size to 20 pixels
                        "-fx-text-fill: blue; " +     // Sets the text color to red
                        "-fx-font-weight: bold; " +  // Sets the font weight to bold
                        "-fx-alignment: center-left; " +  // Aligns the text to center-left
                        "-fx-padding: 10px;";         // Adds 10 pixels of padding around the text


// Create Labels with customized style
                Label regIdLabel = new Label(String.valueOf(regId));
                regIdLabel.setStyle(labelStyle);

                Label guestNameLabel = new Label(guestFirstName + " " + guestLastName);
                guestNameLabel.setStyle(labelStyle);

                Label addressLabel = new Label(address);
                addressLabel.setStyle(labelStyle);

                Label mobileLabel = new Label(mobileNo);
                mobileLabel.setStyle(labelStyle);

                Label roomLabel = new Label(String.valueOf(roomNo));
                roomLabel.setStyle(labelStyle);

                String timestampstring= timestamp.toString();
                Label registratindate= new Label(timestampstring);
                registratindate.setStyle(labelStyle);

                // Add labels to the GridPane
                gridPane.add(regIdLabel, 0, row);
                gridPane.add(guestNameLabel, 1, row);
                gridPane.add(addressLabel, 2, row);
                gridPane.add(mobileLabel, 3, row);
                gridPane.add(roomLabel, 4, row);
                gridPane.add(registratindate,5,row);

                row++;
            }

        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }
        Button backbutton = new Button("Back");
        backbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               secondScene.BackfromviewReservation();
            }
        });
        backbutton.setStyle("-fx-background-color: red; -fx-text-fill: white; -" +
                "fx-background-radius: 30; -fx-padding: 4px 20px 4px 20px; -fx-opacity: 0.7;");
        backbutton.setTextFill(Color.WHITE);

        stackPane= new StackPane();
        StackPane.setAlignment(backbutton, Pos.BOTTOM_LEFT);
        stackPane.getChildren().add(scrollPane);
        stackPane.getChildren().add(backbutton);
        return stackPane;
    }
}
