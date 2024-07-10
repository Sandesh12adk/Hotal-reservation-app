package com.example.hotelreversation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ViewCHeckouts {
    private final String url = "jdbc:mysql://localhost:3306/hotelinfo";
    private final String user = "Admin";
    private final String password = "pass123";
    private final String query = "SELECT * FROM guests;";
    private GridPane gridPane;
    private ScrollPane scrollPane;
    private StackPane stackPane;
    private SecondScene secondScene; // Reference to SecondScene

    public ViewCHeckouts() {
        gridPane = new GridPane();
        gridPane.getChildren().clear();
        gridPane.setHgap(10);

        scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefSize(600, 400);
    }

    public void setSecondScene(SecondScene secondScene){
        this.secondScene= secondScene;
    }

    public StackPane viewCheckout() {
        gridPane.getChildren().clear();
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(query)) {

            String[] headers = {"Registration ID", "Guest Name", "Guest Mobile No", "Check In Date", "Check Out Date", "Room No"};

            for (int i = 0; i < headers.length; i++) {
                Label headerLabel = new Label(headers[i]);
                headerLabel.setStyle("-fx-font-family: Arial; -fx-font-size: 18px; -fx-text-fill: green; -fx-font-weight: bold; -fx-alignment: center-left; -fx-padding: 10px;");
                gridPane.add(headerLabel, i, 0);
            }

            int row = 1;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            while (resultSet.next()) {
                int regId = resultSet.getInt("id");
                String guestFirstName = resultSet.getString("Guest_fname");
                String guestLastName = resultSet.getString("Guest_lname");
                String mobileNo = resultSet.getString("Contact");
                Timestamp checkInTimestamp = resultSet.getTimestamp("Check_in");
                Timestamp checkOutTimestamp = resultSet.getTimestamp("Checkout");
                int roomNo = resultSet.getInt("Room_No");

                Label regIdLabel = new Label(String.valueOf(regId));
                regIdLabel.setStyle("-fx-font-family: Arial; -fx-font-size: 16px; -fx-text-fill: blue; -fx-font-weight: bold; -fx-alignment: center-left; -fx-padding: 10px;");

                Label guestNameLabel = new Label(guestFirstName + " " + guestLastName);
                guestNameLabel.setStyle("-fx-font-family: Arial; -fx-font-size: 16px; -fx-text-fill: blue; -fx-font-weight: bold; -fx-alignment: center-left; -fx-padding: 10px;");

                Label mobileLabel = new Label(mobileNo);
                mobileLabel.setStyle("-fx-font-family: Arial; -fx-font-size: 16px; -fx-text-fill: blue; -fx-font-weight: bold; -fx-alignment: center-left; -fx-padding: 10px;");

                Label checkInLabel = new Label(dateFormat.format(checkInTimestamp));
                checkInLabel.setStyle("-fx-font-family: Arial; -fx-font-size: 16px; -fx-text-fill: blue; -fx-font-weight: bold; -fx-alignment: center-left; -fx-padding: 10px;");

                Label checkOutLabel = new Label(dateFormat.format(checkOutTimestamp));
                checkOutLabel.setStyle("-fx-font-family: Arial; -fx-font-size: 16px; -fx-text-fill: blue; -fx-font-weight: bold; -fx-alignment: center-left; -fx-padding: 10px;");

                Label roomLabel = new Label(String.valueOf(roomNo));
                roomLabel.setStyle("-fx-font-family: Arial; -fx-font-size: 16px; -fx-text-fill: blue; -fx-font-weight: bold; -fx-alignment: center-left; -fx-padding: 10px;");

                gridPane.add(regIdLabel, 0, row);
                gridPane.add(guestNameLabel, 1, row);
                gridPane.add(mobileLabel, 2, row);
                gridPane.add(checkInLabel, 3, row);
                gridPane.add(checkOutLabel, 4, row);
                gridPane.add(roomLabel, 5, row);

                row++;
            }

        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }

        // Back button setup
        Button backbutton = new Button("Back");
        backbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (secondScene != null) {
                    secondScene.BackfromViewCheckouts();
                } else {
                    System.out.println("SecondScene is not initialized.");
                }
            }
        });
        backbutton.setStyle("-fx-background-color: red; -fx-text-fill: white;" +
                " -fx-background-radius: 30; -fx-padding: 4px 20px 4px 20px; -fx-opacity: 0.7;");

        // StackPane setup
        stackPane = new StackPane();
        StackPane.setAlignment(backbutton, Pos.BOTTOM_LEFT);
        stackPane.getChildren().addAll(scrollPane, backbutton);

        return stackPane;
    }
}
