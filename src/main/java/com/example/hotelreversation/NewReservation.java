package com.example.hotelreversation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;

public class NewReservation {
    private StackPane stackPane = null;// Main sab kura yesma xa
    private Label error = null;
    private Label error1 = null;
    private SecondScene secondScene; // Reference to SecondScene
    private FlowPane flowPane; // Available rooms hold gareko hbox

    private TextField textField1;
    private TextField textField2;
    private TextField textField3;
    private TextField textField4;
    private TextField textField5;
    private ArrayList<Integer> availablerooms;


    public void setSecondScene(SecondScene secondScene) {
        this.secondScene = secondScene;
    }

    public StackPane newReservation() {

        stackPane = new StackPane();
        String inputStyle = "-fx-font-family: 'Arial'; -fx-font-size: 18px;";
        // Hotel Name
        Label hotelname = new Label("Fill this form for new Entry ");
        VBox.setMargin(hotelname, new Insets(20, 5, 5, 300));
        hotelname.setTextFill(Color.RED);
        hotelname.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        VBox vBox = new VBox(); ///////////////////// Vbox ko object yeha xa
        vBox.setSpacing(8);
        vBox.setPadding(new Insets(10, 0, 20, 20));

        Label label1 = new Label("First Name");
        label1.setTextFill(Color.BLACK);
        label1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        textField1 = new TextField();
        textField1.setStyle(inputStyle);

        Label label2 = new Label("Last Name");
        label2.setTextFill(Color.BLACK);
        label2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        textField2 = new TextField();
        textField2.setStyle(inputStyle);

        Label label3 = new Label("Permanent Address");
        label3.setTextFill(Color.BLACK);
        label3.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        textField3 = new TextField();
        textField3.setStyle(inputStyle);

        Label label4 = new Label("Mobile Number");
        label4.setTextFill(Color.BLACK);
        label4.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        textField4 = new TextField();
        textField4.setStyle(inputStyle);

        Label label5 = new Label("Room Number");
        label5.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        label4.setTextFill(Color.BLACK);
        label4.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        textField5 = new TextField();
        textField5.setStyle(inputStyle);


        Button showRooms = new Button("Available Rooms");
        showRooms.setTextFill(Color.BLACK);
//        ///////////Available rooms ko Arraylist*******************************************************************
//        availablerooms= new AvailableRooms().avairooms();


        Button submitButton = new Button("Entry");
        submitButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-background-radius: 30;");
        submitButton.setTextFill(Color.WHITE);

        Button okButton = new Button("OK");
        okButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-background-radius: 30;");
        okButton.setTextFill(Color.WHITE);

        // Create back button
        Button backButton = new Button(" Back");
        backButton.setStyle("-fx-background-color:FF0000; -fx-text-fill: white; -fx-background-radius: 30;");
        backButton.setTextFill(Color.WHITE);

        // Handle back button action
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                secondScene.showMainMenu();
            }
        });
    /////////////////////////Main vbox ma controls haru yeha rakheko xa /////////////-*****
        vBox.getChildren().addAll(hotelname, label1, textField1, label2, textField2,
                label3, textField3, label4, textField4,label5,textField5,showRooms, submitButton, backButton);

        showRooms.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                /////////////////////Hbox lai yeah initialze gareko xa hai Available rooms hold gareko HBox
                ///////////Available rooms ko Arraylist*******************************************************************
                availablerooms= new AvailableRooms().avairooms();
                flowPane = new AvailableRooms().displayAvailableRooms();
                flowPane.getChildren().add(okButton);
                stackPane.getChildren().clear(); // Clear existing content in stackPane
                stackPane.getChildren().addAll(flowPane); // Add vBox, hbox and okButton to stackPane
            }
        });


        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stackPane.getChildren().removeAll(flowPane, okButton); // Remove hbox and okButton from stackPane
                stackPane.getChildren().add(vBox); // Restore vBox to stackPane
            }
        });

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String firstname = textField1.getText().trim();
                String lastname = textField2.getText().trim();
                String address = textField3.getText().trim();
                String mobilenumber = textField4.getText().trim();
                String roomno = textField5.getText().trim();

                // Clear previous errors
                vBox.getChildren().removeAll(error, error1);
                error = null;
                error1 = null;

                // Validate mobile number format (10 digits)
                boolean validMobileNumber = mobilenumber.matches("\\d{10}");

                // Validate room number
                boolean validRoom = false;
                for(Integer room: availablerooms){
                    System.out.println(room);
                }
                try {
                    int roomNumber = Integer.parseInt(roomno);
                    if (availablerooms.contains(roomNumber)) {
                        validRoom = true;
                    }
                } catch (NumberFormatException e) {
                    validRoom = false;
                }

                // Simplified validation without room number check
                if (firstname.isEmpty() || lastname.isEmpty() || address.isEmpty() || !validMobileNumber || !validRoom) {
                    if (!validMobileNumber) {
                        error = new Label("Invalid mobile number (must be 10 digits)");
                    } else if (!validRoom) {
                        error = new Label("Invalid room number or room not available");
                    } else {
                        error = new Label("Please fill all fields");
                    }
                    error.setTextFill(Color.RED);
                    vBox.getChildren().add(error);
                } else {
                    // All fields are valid, proceed to submit
                    SubmitClass submitClass = new SubmitClass(textField1, textField2, textField3, textField4, textField5,
                            vBox,availablerooms, firstname, lastname, address, mobilenumber, roomno);
                    submitClass.start(); // Execute submission in a separate thread
                }
            }
        });



//        flowPane = new AvailableRooms().displayAvailableRooms();
        stackPane.getChildren().add(vBox);
        return stackPane;
    }
}

class SubmitClass extends Thread {
    private final String firstname;
    private final String lastname;
    private final String address;
    private final String mobilenumber;
    private final String roomno;
    private final VBox vBox;
    private final TextField textField1;
    private final TextField textField2;
    private final TextField textField3;
    private final TextField textField4;
    private final TextField textField5;
    private Label success = null;
    private Connection connection;
    private final String url = "jdbc:mysql://localhost:3306/hotelinfo";
    private final String user = "Admin";
    private final String password = "pass123";

    public SubmitClass(TextField textfield1, TextField textField2, TextField textField3,
                       TextField textField4,TextField  textField5, VBox vbox,
                       ArrayList<Integer> availablerooms,String firstname,
                       String lastname, String address, String mobilenumber, String roomno) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.mobilenumber = mobilenumber;
        this.roomno= roomno;
        this.vBox = vbox;
        this.textField1 = textfield1;
        this.textField2 = textField2;
        this.textField3 = textField3;
        this.textField4 = textField4;
        this.textField5= textField5;
    }

    @Override
    public void run() {
        submitToDatabase();
    }

    private void submitToDatabase() {
        int roomnoo= Integer.parseInt(roomno);
        String query1 = "INSERT INTO hotelinfo (guest_name, guest_lastname, guest_address, guest_mobile_no, room_no) " +
                "VALUES ('" + firstname + "', '" + lastname + "', '" + address + "', '"
                + mobilenumber + "', '" + roomnoo + "')";
        String query = "DELETE FROM  room_available   WHERE  Room_no  = ?";


        try {
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connection established successfully");
            Statement statement = connection.createStatement();
            statement.executeUpdate(query1);
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, roomnoo); // Use setInt() for integer values
            pstmt.executeUpdate();
            connection.close();
            statement.close();//*****************************************************************************
            showSuccessMessage();
            System.out.println("Successfully submitted to database");

        } catch (Exception e) {
            System.out.println("Error creating statement: " + e.getMessage());
        }
    }

    public void showSuccessMessage() {
        Platform.runLater(() -> {
            textField1.setText("");
            textField2.setText("");
            textField3.setText("");
            textField4.setText("");
            textField5.setText("");

            Label successLabel = new Label("Entry Successful");
            successLabel.setTextFill(Color.GREEN);
            successLabel.setFont(Font.font("Arial", 15));
            vBox.getChildren().add(successLabel);

            // Remove successLabel after 2 seconds
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, new KeyValue(successLabel.opacityProperty(), 1.0)),
                    new KeyFrame(Duration.seconds(2), new KeyValue(successLabel.opacityProperty(), 0.0))
            );
            timeline.setOnFinished(event -> vBox.getChildren().remove(successLabel));
            timeline.play();
        });
    }
}
