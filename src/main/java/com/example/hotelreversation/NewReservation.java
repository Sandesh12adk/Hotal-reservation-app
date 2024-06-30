package com.example.hotelreversation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.Statement;

public class NewReservation {

    private Label error = null;
    private Label error1 = null;

    private TextField textField1;
    private TextField textField2;
    private TextField textField3;
    private TextField textField4;
    private TextField textField5;




    public VBox newReservation(Connection connection) {
        String inputStyle = "-fx-font-family: 'Arial'; -fx-font-size: 18px;";
        // Hotel Name
        Label hotelname = new Label("Fill this form for new Entry ");
        VBox.setMargin(hotelname, new Insets(20, 5, 5, 300));
        hotelname.setTextFill(Color.RED);
        hotelname.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        VBox vBox = new VBox();
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
        label5.setTextFill(Color.BLACK);
        label5.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        textField5 = new TextField();
        textField5.setStyle(inputStyle);

        Button submitButton = new Button("Entry");
        submitButton.setTextFill(Color.WHITE);
        submitButton.setStyle("-fx-background-color: dodgerblue; -fx-font-size: 14pt; -fx-cursor: hand;");

        vBox.getChildren().addAll(hotelname, label1, textField1, label2, textField2, label3, textField3, label4, textField4, label5, textField5, submitButton);

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String firstname = textField1.getText();
                String lastname = textField2.getText();
                String address = textField3.getText();
                String mobilenumber = textField4.getText();
                String roomno = textField5.getText();

                // Clear previous errors
                vBox.getChildren().removeAll(error, error1);
                error = null;
                error1 = null;

                if (firstname.isEmpty() || lastname.isEmpty() || address.isEmpty() || roomno.isEmpty() || mobilenumber.length() != 10) {
                    if (mobilenumber.length() != 10) {
                        if (error == null) {
                            error = new Label("Wrong mobile number");
                            error.setTextFill(Color.RED);
                            vBox.getChildren().add(error);
                        }
                    } else {
                        if (error1 == null) {
                            error1 = new Label("Please fill all fields");
                            error1.setTextFill(Color.RED);
                            vBox.getChildren().add(error1);
                        }
                    }
                } else {
                    SubmitClass submitClass = new SubmitClass(textField1, textField2, textField3, textField4, textField5, vBox, connection,
                            firstname, lastname, address, mobilenumber, roomno);
                    submitClass.start();
                }
            }
        });

        return vBox;
    }
}

class SubmitClass extends Thread {
    private final Connection connection;
    private final String firstname;
    private final String lastname;
    private final String address;
    private final String mobilenumber;
    private final String roomno;
    private VBox vBox;
    private TextField textField1;
    private TextField textField2;
    private TextField textField3;
    private TextField textField4;
    private TextField textField5;
    private Label success = null;
    private Label successLabel = null;

    public SubmitClass(TextField textfield1, TextField textField2, TextField textField3,
                       TextField textField4, TextField textField5, VBox vbox, Connection connection, String firstname,
                       String lastname, String address, String mobilenumber, String roomno) {
        this.connection = connection;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.mobilenumber = mobilenumber;
        this.roomno = roomno;
        this.vBox = vbox;
        this.textField1 = textfield1;
        this.textField2 = textField2;
        this.textField3 = textField3;
        this.textField4 = textField4;
        this.textField5 = textField5;
    }

    @Override
    public void run() {
        submitToDatabase();
    }

    private void submitToDatabase() {
        int room_no = Integer.parseInt(roomno);
        String query = "INSERT INTO hotelinfo (guest_name, guest_lastname, guest_address, guest_mobile_no, room_no) " +
                "VALUES ('" + firstname + "', '" + lastname + "', '" + address + "', '" + mobilenumber + "', " + room_no + ")";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
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

            // Remove successLabel after 1 second
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
