package com.example.hotelreversation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;

public class SecondScene {

    private final String url = "jdbc:mysql://localhost:3306/hotelinfo";
    private final String user = "Admin";
    private final String password = "pass123";
    private String buttonStyle;
    private StackPane mainStackpane;
    private HBox mainHBox;
    private StackPane stackPane;
    private VBox ButtonsVBox;
    private Button btn1;
    private StackPane vbox1 = null;
    private StackPane scrollPane, ScrollPane2;
    private GridPane gridPane = null;

    // Object of the other classes
    private VIewReservations viewReservations = new VIewReservations();
    private ViewCHeckouts viewCHeckouts = new ViewCHeckouts();

    public Scene secondScene() {
        mainStackpane = new StackPane();

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

        // Right side VBox for image and hotel image
        stackPane.getChildren().addAll(hotelimage, hotelname);

        mainHBox = new HBox();
        mainHBox.setPrefWidth(600); // Set preferred width to 600 pixels

        ButtonsVBox = new VBox();
        ButtonsVBox.setSpacing(3);

        btn1 = new Button("New Reservation");
        Button btn2 = new Button("Check Reservation");
        Button btn3= new Button("Customize");
        Button btn4 = new Button("Check-Outs");
        Button btn5 = new Button("Delete Reservation");
        Button btn6 = new Button("Exit");

        buttonStyle = "-fx-font-weight: bold; " +
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

        ButtonsVBox.getChildren().addAll(btn1, btn2, btn4, btn5, btn6,btn3);
        mainHBox.getChildren().addAll(ButtonsVBox, stackPane);

        btn1.setOnMouseExited(event -> {
            if (vbox1 == null) {
                btn1.setStyle(buttonStyle);
            }
        });

        btn1.setOnMouseEntered(event -> {
            String newButtonStyle = buttonStyle.replace("-fx-background-color: #4CAF50;",
                            "-fx-background-color: #FFFF00;")
                    .replace("-fx-text-fill: white;", "-fx-text-fill: black;");
            btn1.setStyle(newButtonStyle);
        });

        btn1.setOnAction(event -> {
            String newButtonStyle = buttonStyle.replace("-fx-background-color: #4CAF50;",
                            "-fx-background-color: #FFFF00;")
                    .replace("-fx-text-fill: white;", "-fx-text-fill: black;");
            btn1.setStyle(newButtonStyle);
            if (vbox1 == null) {
                try {
                    NewReservation newReservation = new NewReservation();
                    newReservation.setSecondScene(SecondScene.this);
                    vbox1 = newReservation.newReservation();
                    mainHBox.getChildren().remove(stackPane);
                    mainHBox.getChildren().remove(gridPane);
                    gridPane= null;
                    btn5.setStyle(buttonStyle);
                    gridPane = null;
                    mainHBox.getChildren().add(vbox1);
                } catch (Exception e) {
                    System.out.println("Error" + e.getMessage());
                }
            }
        });

        btn2.setOnAction(event -> {
            System.out.println("Clicked");
            if(scrollPane== null) {
                try {
                    viewReservations.setSecondScene(SecondScene.this);
                    scrollPane = viewReservations.viewReservations();
                    mainHBox.getChildren().remove(stackPane);
                    mainHBox.getChildren().remove(vbox1);
                    vbox1 = null;
                    mainHBox.getChildren().remove(gridPane);
                    gridPane = null;
                    mainStackpane.getChildren().add(scrollPane);
                } catch (Exception e) {
                    System.out.println("Error" + e.getMessage());
                }
            }
        });

        btn2.setOnMouseEntered(event -> {
            String newButtonStyle = buttonStyle.replace("-fx-background-color: #4CAF50;",
                            "-fx-background-color: #FFFF00;")
                    .replace("-fx-text-fill: white;", "-fx-text-fill: black;");
            btn2.setStyle(newButtonStyle);
        });

        btn2.setOnMouseExited(event -> {
            btn2.setStyle(buttonStyle);
        });

        btn6.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Confirm Exit");
            alert.setContentText("Are you sure you want to exit?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    System.out.println("Exiting application...");
                    Stage stage = (Stage) mainStackpane.getScene().getWindow();
                    stage.close();
                }
            });
        });

        btn6.setOnMouseExited(event -> {
            btn6.setStyle(buttonStyle);
        });

        btn6.setOnMouseEntered(event -> {
            String newButtonStyle = buttonStyle.replace("-fx-background-color: #4CAF50;",
                            "-fx-background-color: #FFFF00;")
                    .replace("-fx-text-fill: white;", "-fx-text-fill: black;");
            btn6.setStyle(newButtonStyle);
        });

        btn5.setOnMouseExited(event -> {
            if (gridPane == null) {
                btn5.setStyle(buttonStyle);
            }
        });

        btn5.setOnMouseEntered(event -> {
            String newButtonStyle = buttonStyle.replace("-fx-background-color: #4CAF50;",
                            "-fx-background-color: #FFFF00;")
                    .replace("-fx-text-fill: white;", "-fx-text-fill: black;");
            btn5.setStyle(newButtonStyle);
        });

        btn5.setOnAction(event -> {
            btn1.setStyle(buttonStyle);
            if (gridPane == null) {
            mainHBox.getChildren().remove(stackPane);
            mainHBox.getChildren().remove(vbox1);
            vbox1 = null;
                gridPane = new DeleteReservation(SecondScene.this).Delete();
                mainHBox.getChildren().add(gridPane);
            }
        });

        btn4.setOnAction(event -> {
            System.out.println("Clicked");
            if(ScrollPane2== null) {
                try {
                    ScrollPane2 = viewCHeckouts.viewCheckout();
                    viewCHeckouts.setSecondScene(SecondScene.this); // Ensure SecondScene reference is set here
                    mainHBox.getChildren().remove(stackPane);
                    mainHBox.getChildren().remove(vbox1);
                    vbox1 = null;
                    mainHBox.getChildren().remove(gridPane);
                    gridPane = null;
                    mainStackpane.getChildren().add(ScrollPane2);
                } catch (Exception e) {
                    System.out.println("Error" + e.getMessage());
                }
            }
        });
        btn4.setOnMouseExited(event -> {

                btn4.setStyle(buttonStyle);

        });

        btn4.setOnMouseEntered(event -> {
            String newButtonStyle = buttonStyle.replace("-fx-background-color: #4CAF50;",
                            "-fx-background-color: #FFFF00;")
                    .replace("-fx-text-fill: white;", "-fx-text-fill: black;");
            btn4.setStyle(newButtonStyle);
        });

        try {
            Image formbg = new Image(getClass().getResourceAsStream("/com/example/hotelreversation/form-bg.jpg"));
            ImageView imageView = new ImageView(formbg);
            imageView.setFitWidth(1130);
            imageView.setFitHeight(650);
            mainStackpane.getChildren().add(imageView);
        } catch (Exception e) {
            System.out.println("Error loading image");
        }

        mainStackpane.getChildren().add(mainHBox);
        Scene scene = new Scene(mainStackpane);
        return scene;
    }

    public void showMainMenu() {
        btn1.setStyle(buttonStyle);
        mainHBox.getChildren().remove(vbox1);
        mainHBox.getChildren().add(stackPane);
        vbox1 = null;
    }

    public void BackfromviewReservation() {
        mainStackpane.getChildren().remove(scrollPane);
        scrollPane= null;
        mainHBox.getChildren().add(stackPane);
    }

    public void BackfromViewCheckouts() {
        mainStackpane.getChildren().remove(ScrollPane2);
        ScrollPane2= null;
        mainHBox.getChildren().add(stackPane);
    }
    public void BackfromDeletereservation() {
        mainHBox.getChildren().remove(gridPane);
        gridPane= null;// Remove the GridPane containing delete reservation functionality
        mainHBox.getChildren().remove(vbox1);
        vbox1= null;
        mainHBox.getChildren().remove(ScrollPane2);
        ScrollPane2= null;
        mainHBox.getChildren().add(stackPane);   // Add back the main menu stackPane

    }

}
