package com.example.hotelreversation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.sql.Connection;
import java.sql.DriverManager;

public class SecondScene {
    private Connection connection = null;
    private final String url = "jdbc:mysql://localhost:3306/hotelinfo";
    private final String user = "Admin";
    private final String password = "pass123";

    private StackPane mainStackpane;
    private VBox vbox1 = null;
    private HBox mainHBox;
    private StackPane stackPane;
    private VBox ButtonsVBox;

    public Scene secondScene() {
        mainStackpane= new StackPane();

        // For image and hotel name
        stackPane = new StackPane();
        stackPane.setAlignment(Pos.TOP_CENTER);

        // Hotel Title
        Label hotelname = new Label("Hotel Kunja ");
        VBox.setMargin(hotelname, new Insets(20, 5, 5, 300));
        hotelname.setTextFill(Color.BLUE);
        hotelname.setFont(Font.font("Arial", FontWeight.BOLD, 50));

        // Hotel Image
        Image Hotelimage1 = new Image(getClass().getResourceAsStream("/final.jpg"));
        ImageView hotelimage = new ImageView(Hotelimage1);
        hotelimage.setFitWidth(880);

        // Right side VBox for image hotel image
        stackPane.getChildren().addAll(hotelimage, hotelname);

        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection established successfully");
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }

        mainHBox = new HBox();
        ButtonsVBox = new VBox();
        ButtonsVBox.setSpacing(0.5);

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

        ButtonsVBox.getChildren().addAll(btn1, btn2, btn3, btn4, btn5, btn6);
        mainHBox.getChildren().addAll(ButtonsVBox,stackPane);

        btn1.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (vbox1 == null) {
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
                if (vbox1 == null && connection != null) {
                    NewReservation newReservation = new NewReservation();
                   newReservation.setSecondScene(SecondScene.this); // Set reference to SecondScene Reuse and Flexibility:
                    // By passing the instance (SecondScene.this), you can reuse the same SecondScene instance across different
                    // parts of your application. This promotes code reuse and makes it easier to maintain and extend your application.
                   // The reason why we pass the instance of SecondScene (SecondScene.this) to NewReservation rather than creating
                // new instance within NewReservation boils down to how object-oriented design and encapsulation work.
                    vbox1 = newReservation.newReservation(connection);
                    mainHBox.getChildren().remove(stackPane);
                    mainHBox.getChildren().add(vbox1);
                }
            }
        });

        try {
            Image formbg = new Image(getClass().getResourceAsStream("/com/example/hotelreversation/form-bg.jpg"));
            // Set the background image to stackplane1
            ImageView imageView= new ImageView(formbg);
            imageView.setFitWidth(1130);
            imageView.setFitHeight(650);
            mainStackpane.getChildren().add(imageView);
        } catch (Exception e){
            System.out.println("Error loading image");
        }
        mainStackpane.getChildren().add(mainHBox);
        Scene scene = new Scene(mainStackpane);
        return scene;
    }

    // Method to return to the main menu
    public void showMainMenu() {
        mainHBox.getChildren().remove(vbox1);
        mainHBox.getChildren().add(stackPane);
        vbox1 = null;
    }
}