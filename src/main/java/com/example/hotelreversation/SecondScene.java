package com.example.hotelreversation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


import java.sql.Connection;
import java.sql.DriverManager;

public class SecondScene {
    private Connection connection= null;
    private final String url="jdbc:mysql://localhost:3306/hotelinfo";
    private final String user="Admin";
    private final String password= "pass123";


    VBox vbox1= null;


    public Scene secondScene() {
        //FOr image and hotel name
        StackPane stackPane= new StackPane();
        stackPane.setAlignment(Pos.TOP_CENTER);

        // Hotel Title
        Label hotelname = new Label("Hotel Kunja ");
        VBox.setMargin(hotelname, new Insets(20, 5, 5, 300));
        hotelname.setTextFill(Color.BLUE);
        hotelname.setFont(Font.font("Arial", FontWeight.BOLD, 50));

        //Hote Image
        Image Hotelimage1 = new Image(getClass().getResourceAsStream("/final.jpg"));
        ImageView hotelimage = new ImageView(Hotelimage1);
        hotelimage.setFitWidth(880);

        //Right side ko vbox for image hotel ko image vako wala
        stackPane.getChildren().addAll(hotelimage,hotelname);

        try {
            connection= DriverManager.getConnection(url,user,password);
            System.out.println("Connection established successfully");
        } catch(Exception e){
            System.out.println("Error" + e.getMessage());
        }

        HBox hBox = new HBox();

        VBox vBox = new VBox();
        vBox.setSpacing(0.5);

        Button btn1 = new Button("New Reservation");
        Button btn2 = new Button("Check Reservation");
        Button btn3 = new Button("Get Room Number");
        Button btn4 = new Button("Update Reservation");
        Button btn5 = new Button("Delete Reservation");
        Button btn6 = new Button("Exit");

        String buttonStyle = "-fx-font-weight: bold; " +
                "-fx-font-size: 16px; " +
                "-fx-background-color: #4CAF50; " +
                "-fx-text-fill: white; " +
                "-fx-padding: 35.5px 50px; " +
                "-fx-background-radius: 5px; " +
                "-fx-border-radius: 5px; " +
                "-fx-border-color: #388E3C; " +
                "-fx-border-width: 2px; " +
                "-fx-cursor: hand; " +
                "-fx-min-width: 200px; " +
                "-fx-max-width: 2000px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 1);";  // Drop shadow effect




        btn1.setStyle(buttonStyle);
        btn2.setStyle(buttonStyle);
        btn3.setStyle(buttonStyle);
        btn4.setStyle(buttonStyle);
        btn5.setStyle(buttonStyle);
        btn6.setStyle(buttonStyle);


        vBox.getChildren().addAll(btn1, btn2, btn3, btn4, btn5, btn6);
        hBox.getChildren().add(vBox);
        hBox.getChildren().add(stackPane);
        btn1.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(vbox1==null) {
                    btn1.setStyle(buttonStyle);
                }
            }
        });
        btn1.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("hi");
                String newButtonStyle = buttonStyle.replace("-fx-background-color: #4CAF50;", "-fx-background-color: #FFFF00;")
                        .replace("-fx-text-fill: white;", "-fx-text-fill: black;");
                btn1.setStyle(newButtonStyle);

            }
        });
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Update background color dynamically
                String newButtonStyle = buttonStyle.replace("-fx-background-color: #4CAF50;", "-fx-background-color: #FFFF00;")
                        .replace("-fx-text-fill: white;", "-fx-text-fill: black;");

                btn1.setStyle(newButtonStyle);
                if(vbox1==null && connection!= null) {
                    vbox1 = new NewReservation().newReservation(connection);
                    hBox.getChildren().remove(stackPane);
                    hBox.getChildren().add(vbox1);
                }
            }
        });
    Scene scene= new Scene(hBox);

        return scene;
    }
}
