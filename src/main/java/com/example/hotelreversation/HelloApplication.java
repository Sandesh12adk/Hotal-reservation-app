package com.example.hotelreversation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;

public class HelloApplication extends Application {
    private final String url = "jdbc:mysql://localhost:3306/hotelinfo";
    private final String user = "Admin";
    private final String passwordd = "pass123";
    private ResultSet resultset = null;
    private Connection connection = null;
    private PreparedStatement stmt = null;
    String password;

    @Override
    public void start(Stage stage) throws IOException {
        //**************login page
        VBox vbox = new VBox();
        vbox.setSpacing(8);
        vbox.setPadding(new Insets(10, 0, 20, 10));
        //Hotel logo

        Image Hotelimage1 = new Image(getClass().getResourceAsStream("/logo.jpeg"));
        ImageView hotellogo = new ImageView(Hotelimage1);


        //Hotel Name
        Label hotelname = new Label("Hotel Kunja ");
        hotelname.setTextFill(Color.RED);
        hotelname.setFont(Font.font("Arial", FontWeight.BOLD, 50));

        //username and input
        Label label1 = new Label("UserName");
        label1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        VBox.setMargin(label1, new Insets(20, 5, 5, 65));  // Margin around the label

        TextField textField = new TextField();
        textField.setFont(Font.font(15));
        VBox.setMargin(textField, new Insets(2, 5, 0, 0));  // Margin around the text field


        //passoword and input
        Label label2 = new Label("Password");
        label2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        VBox.setMargin(label2, new Insets(10, 5, 5, 65));  // Margin around the label

        PasswordField passwordField = new PasswordField();
        passwordField.setFont(Font.font(15));
        VBox.setMargin(passwordField, new Insets(5, 0, 0, 0));  // Margin around the text field

        //Login Button
        Button loginbutton = new Button("LOGIN");
        // Styling the button
        loginbutton.setTextFill(Color.WHITE); // Setting text color
        loginbutton.setStyle("-fx-background-color: dodgerblue; " + // Setting background color
                "-fx-font-size: 14pt; " +// Setting font size
                "-fx-cursor: hand;"); // Changing mouse pointer to hand
        loginbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                establishConnection();
                getPassword(); /////////////////////////////////////////////////////////////////////////////////////
                String username = textField.getText();
                String passworddd = passwordField.getText();
                if (passworddd.equals(password) && username.equals("Admin")) {
                    Scene secondscene = new SecondScene().secondScene();
                    stage.setHeight(650);
                    stage.setWidth(1130);
                    stage.setScene(secondscene);
                    stage.show();
                } else {
                    showtoast(vbox, textField, "Invalid username or password");
                    showAlert(Alert.AlertType.INFORMATION, "Cannot login", "Wrong username or password");
                }
            }
        });
        Button forgetpassword = new Button("Forget Password");
        forgetpassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ForgetPassword forgetPassword = new ForgetPassword(new Stage());
                forgetPassword.show();
            }
        });
        VBox.setMargin(forgetpassword, new Insets(5, 0, 0, 70));  // Ma
        // Adding above controls to the vbox
        vbox.getChildren().addAll(hotelname, hotellogo, label1, textField, label2, passwordField, loginbutton, forgetpassword);
        // Increase left margin of loginbutton by 50 pixels
        VBox.setMargin(loginbutton, new Insets(0, 0, 0, 70));


        // ****** Side image of hotel login page*****
        HBox hbox = new HBox();
        // Load the image from the resources
        Image Hotelimage = new Image(getClass().getResourceAsStream("/final.jpg"));
        ImageView imageView1 = new ImageView(Hotelimage);
        imageView1.setFitWidth(650);
        imageView1.setFitHeight(700);
        hbox.getChildren().addAll(imageView1);

        // Everything inside the main Hbox
        HBox root = new HBox();
        root.getChildren().addAll(hbox, vbox);

        // ************ Create the scene and add the main Hbox in the scene
        Scene scene = new Scene(root, 900, 1900, Color.RED);

        Scene secondscene = new SecondScene().secondScene();
        stage.setHeight(650);
        stage.setWidth(1130);
        stage.setScene(secondscene);
        stage.show();

        stage.setScene(scene);
        stage.setWidth(1000);
        stage.setHeight(650);
        stage.setResizable(false); // Make the stage non-resizable

        // Show the stage
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

    private Label toast = null; // Initialize toast to null

    public void showtoast(VBox vbox, TextField textField, String message) {
        if (toast == null) {
            // Create the toast label only if it is not already created
            toast = new Label(message);
            toast.setTextFill(Color.RED);
            vbox.getChildren().add(toast);
        }

        // Remove toast when user interacts with textField
        textField.setOnKeyTyped(event -> {
            fadeOutAndRemove();
        });
    }

    private void fadeOutAndRemove() {
        if (toast != null) {
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, new KeyValue(toast.opacityProperty(), 1.0)),
                    new KeyFrame(Duration.seconds(1), new KeyValue(toast.opacityProperty(), 0.0))
            );
            timeline.setOnFinished(event -> {
                VBox parent = (VBox) toast.getParent();
                if (parent != null) {
                    parent.getChildren().remove(toast);
                    toast = null; // Reset toast to null after removal
                }
            });
            timeline.play();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void establishConnection() {
        System.out.println("establishConnection called");
        try {
            String query = "SELECT * FROM password";
            connection = DriverManager.getConnection(url, user, passwordd);
            stmt = connection.prepareStatement(query);
            resultset = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void getPassword() {
        System.out.println("getpassword called");
        try {
                 resultset.next();
                 password = resultset.getString("PASS");
                System.out.println("Password: " + password);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (resultset != null) resultset.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

}