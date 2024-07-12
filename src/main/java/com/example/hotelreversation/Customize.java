package com.example.hotelreversation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;




public class Customize {
    private SecondScene secondScene;
    private final String url = "jdbc:mysql://localhost:3306/hotelinfo";
    private final String user = "Admin";
    private final String password = "pass123";
    private Connection connection;
    private Statement stmt;

    public VBox addAnddeleteRooms(SecondScene secondScene){
        this.secondScene= secondScene;
        Label label1= new Label("Add To Available Room");
        label1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        TextField textField1= new TextField();
        Button btn1= new Button("Add");
        btn1.setStyle("-fx-background-color: red; -fx-text-fill: white;" +
                " -fx-background-radius: 10; -fx-padding:" + " 4px 8px 4px 8px; -fx-opacity: 0.7;");

        Label label2= new Label("Delete From Availabe Room");
        label2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        TextField textField2= new TextField();
        Button btn2= new Button("Delete");
        btn2.setStyle("-fx-background-color: red; " + "-fx-text-fill: white; -fx-background-radius: 10;" +
                " -fx-padding:" +" 4px 8px 4px 8px; -fx-opacity: 0.7;");
        Button backbutton = new Button("Go Back");
        backbutton.setStyle("-fx-background-color: blue; -fx-text-fill: white;" +
                " -fx-background-radius: 30; -fx-padding: 4px 10px 4px 10px; -fx-opacity: 0.7;");
        backbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                secondScene.BackfromCustomize();
            }
        });
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String temp= textField1.getText().trim();
                if(!temp.isEmpty()) {
                    int Room_noToAdd = Integer.parseInt(temp);
                    add(Room_noToAdd, textField1);
                }
            }
        });
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String temp2= textField2.getText();
                if(!temp2.isEmpty()) {
                    int Room_noToDelete = Integer.parseInt(temp2);
                    delete(Room_noToDelete, textField2);
                }
            }
        });
        VBox vBox= new VBox();
        vBox.setSpacing(12);
        vBox.setPadding(new Insets(50, 0, 20, 30));
        vBox.getChildren().addAll(label1,textField1,btn1,label2,textField2,btn2,backbutton);

        return  vBox;
    }
    public void add(int room_no,TextField textField1){
        try {
            connection = DriverManager.getConnection(url, user, password);
            stmt = connection.createStatement();
            String query = "INSERT INTO room_available(Room_no) values (" + room_no + ");";
            int RowAffected= stmt.executeUpdate(query);
            if(RowAffected>0){
                showAlert(Alert.AlertType.INFORMATION, "Success", "Successfully added");
                textField1.setText("");
            }
            else{
                showAlert(Alert.AlertType.INFORMATION, "Failed", "Failed to add");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            try {
                stmt.close();
                connection.close();
            }catch (Exception e){
                System.out.println("Error closing resources"+ e.getMessage());
            }
        }
    }
    public void delete(int room_no,TextField textField2){
        try {
            connection = DriverManager.getConnection(url, user, password);
            stmt = connection.createStatement();
            String query2= "DELETE FROM room_available WHERE Room_no= " + room_no + ";";
            int RowAffected= stmt.executeUpdate(query2);
            if(RowAffected>0){
                showAlert(Alert.AlertType.INFORMATION, "Failed", "Successfully Deleted");
                textField2.setText("");
            }
            else{
                showAlert(Alert.AlertType.INFORMATION, "Failed", "Failed to Delete");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            try {
                stmt.close();
                connection.close();
            }catch (Exception e){
                System.out.println("Error closing resources"+ e.getMessage());
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
