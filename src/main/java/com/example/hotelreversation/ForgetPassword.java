package com.example.hotelreversation;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ForgetPassword {
    private Connection connection;
    private Stage stage;
    TextField np;
    TextField cp;

    public ForgetPassword(Stage stage){
        this.stage= stage;
    }

    public void show() {
        Label newpassword = new Label("New Password");
        np = new TextField();
        Label confirmPassword = new Label("Confirm Password");
        cp = new TextField();

        Button submit = new Button("Submit");
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getChildren().addAll(newpassword, np, confirmPassword, cp, submit);
        Scene scene = new Scene(vbox);
        stage.setWidth(350);
        stage.setHeight(310);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String newPassword = np.getText();
                String confirmPassword = cp.getText();
                if (!newPassword.isEmpty() && !confirmPassword.isEmpty() && newPassword.equals(confirmPassword)) {
                    showVerificationFields(vbox);
                } else {
                    showAlert(Alert.AlertType.INFORMATION, "", "Passwords do not match");
                }
            }
        });
    }

    private void showVerificationFields(VBox vbox) {
        Label code = new Label("Enter the 6-Digit code sent to your mobile No:");
        TextField c = new TextField();
        Button verify = new Button("Verify");

        verify.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String enteredCode = c.getText().trim();
                String expectedCode = "123456"; // Replace with code sent via SMS
                if (enteredCode.equals(expectedCode)) {
                    String newPassword = np.getText();
                    try {
                        establishDBConnection();
                        updatePassword(newPassword);
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Password updated successfully");
                        stage.close();

                    } catch (SQLException e) {
                        showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update password: " + e.getMessage());
                    } finally {
                        try {
                            if (connection != null) {
                                connection.close();
                            }
                        } catch (SQLException e) {
                            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to close connection: " + e.getMessage());
                        }
                    }
                } else {
                    showAlert(Alert.AlertType.INFORMATION, "Failed", "Wrong code");
                }
            }
        });

        vbox.getChildren().addAll(code, c, verify);
    }

    private void establishDBConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/hotelinfo";
        String user = "Admin";
        String password = "pass123";
        connection = DriverManager.getConnection(url, user, password);
    }

    private void updatePassword(String newPassword) throws SQLException {
        String updateQuery = "UPDATE password SET pass = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setString(1, newPassword);
            pstmt.executeUpdate();
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
