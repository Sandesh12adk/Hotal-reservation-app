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
   // private Connection connection = null;
    private final String url = "jdbc:mysql://localhost:3306/hotelinfo";
    private final String user = "Admin";
    private final String password = "pass123";
    private String buttonStyle;
    private StackPane mainStackpane;
    private HBox mainHBox;
    private StackPane stackPane;
    private VBox ButtonsVBox;
    Button btn1;
    private StackPane vbox1 = null;  // nam cahi vbox1 nai rakhya hai change gharna jhau xa.
    private StackPane scrollPane;//   you pani same hai nam cahi vbox1 nai rakhya hai change gharna jhau xa.
    GridPane gridPane= null; //  Delete reservation ley reutrn gareko GridPane

    // objects of the other classes
   VIewReservations viewReservations= new VIewReservations();


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

        // Right side VBox for image hotel image
        stackPane.getChildren().addAll(hotelimage, hotelname);

//        try {
//            connection = DriverManager.getConnection(url, user, password);
//            System.out.println("Connection established successfully");
//        } catch (Exception e) {
//            System.out.println("Error" + e.getMessage());
//        }
        mainHBox = new HBox();
        mainHBox.setPrefWidth(600); // Set preferred width to 600 pixels

        ButtonsVBox = new VBox();
        ButtonsVBox.setSpacing(0.5);

        btn1 = new Button("New Reservation");
        Button btn2 = new Button("Check Reservation");
        Button btn3 = new Button("Get Room Number");
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

        ButtonsVBox.getChildren().addAll(btn1, btn2, btn3, btn4, btn5, btn6);
        mainHBox.getChildren().addAll(ButtonsVBox, stackPane);

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
                String newButtonStyle = buttonStyle.replace("-fx-background-color: #4CAF50;",
                                "-fx-background-color: #FFFF00;")
                        .replace("-fx-text-fill: white;", "-fx-text-fill: black;");
                btn1.setStyle(newButtonStyle);
            }
        });
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Update background color dynamically

                String newButtonStyle = buttonStyle.replace("-fx-background-color: #4CAF50;",
                                "-fx-background-color: #FFFF00;")
                        .replace("-fx-text-fill: white;", "-fx-text-fill: black;");
                btn1.setStyle(newButtonStyle);
                if (vbox1 == null) {
                    try {
                        NewReservation newReservation = new NewReservation();
                        newReservation.setSecondScene(SecondScene.this);
                        // Set reference to SecondScene Reuse and Flexibility:
                        // By passing the instance (SecondScene.this), you can reuse the same SecondScene instance across different
                        // parts of your application. This promotes code reuse and makes it easier to maintain and extend your application.
                        // The reason why we pass the instance of SecondScene (SecondScene.this) to NewReservation rather than creating
                        // new instance within NewReservation boils down to how object-oriented design and encapsulation work.
                        vbox1 = newReservation.newReservation();
                        mainHBox.getChildren().remove(stackPane);
                        mainHBox.getChildren().remove(gridPane);
                        btn5.setStyle(buttonStyle);
                        gridPane= null;
                        mainHBox.getChildren().add(vbox1);
                    } catch (Exception e){
                        System.out.println("Error" +e.getMessage());
                    }
                }
            }
        });
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Clicked");
                try{
                scrollPane= viewReservations.viewReservation();
                viewReservations.setSecondScene(SecondScene.this);
                mainHBox.getChildren().remove(stackPane);
                mainHBox.getChildren().remove(vbox1);
                vbox1= null;
                mainHBox.getChildren().remove(gridPane);
                gridPane= null;
                mainStackpane.getChildren().add(scrollPane);
                vbox1= null;
            }catch (Exception e) {
                    System.out.println("Error"+ e.getMessage());
                }
                }
        });

        btn2.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("hi");
                String newButtonStyle = buttonStyle.replace("-fx-background-color: #4CAF50;",
                                "-fx-background-color: #FFFF00;")
                        .replace("-fx-text-fill: white;", "-fx-text-fill: black;");
                btn2.setStyle(newButtonStyle);
            }
        });

        btn2.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                    btn2.setStyle(buttonStyle);
            }
        });
        btn6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Create a confirmation dialog
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Confirm Exit");
                alert.setContentText("Are you sure you want to exit?");

                // Handle the result of the dialog
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        // Perform exit action
                        System.out.println("Exiting application...");
                        Stage stage = (Stage) mainStackpane.getScene().getWindow();
                        /*  mainStackpane: This is presumably a StackPane object in your JavaFX application. mainStackpane is
                         added to a scene (Scene) which in turn is displayed in a Stage.

                      getScene(): This method retrieves the Scene object that contains mainStackpane.

                      getWindow(): This method returns the window (Window) that is the root of the scene. In JavaFX
                      , a Stage is a top-level container for your application's UI, and it represents the main window of the application.

                       (Stage): Casts the Window object returned by getWindow() to a Stage. Since a Stage is a subclass of Window in
                        JavaFX, this cast is safe assuming your application structure adheres to this hierarchy.   */
                        stage.close(); // Close the main stage (application window)
                    }
                });
            }
        });
        btn6.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                    btn6.setStyle(buttonStyle);
            }
        });

        btn6.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("hi");
                String newButtonStyle = buttonStyle.replace("-fx-background-color: #4CAF50;",
                                "-fx-background-color: #FFFF00;")
                        .replace("-fx-text-fill: white;", "-fx-text-fill: black;");
                btn6.setStyle(newButtonStyle);
            }
        });

        btn5.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(gridPane== null) {
                    btn5.setStyle(buttonStyle);
                }
            }
        });

        btn5.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("hi");
                String newButtonStyle = buttonStyle.replace("-fx-background-color: #4CAF50;",
                                "-fx-background-color: #FFFF00;")
                        .replace("-fx-text-fill: white;", "-fx-text-fill: black;");
                btn5.setStyle(newButtonStyle);
            }
        });
        btn5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                btn1.setStyle(buttonStyle);
                mainHBox.getChildren().remove(stackPane);
                mainHBox.getChildren().remove(vbox1);
                vbox1= null;
                if(gridPane== null) {
                    gridPane = new DeleteReservation().Delete();
                    mainHBox.getChildren().add(gridPane);
                }
            }
        });
        try {
            Image formbg = new Image(getClass().getResourceAsStream("/com/example/hotelreversation/form-bg.jpg"));
            // Set the background image to stackplane1
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
    // Method to return to the main menu
    public void showMainMenu() {
        btn1.setStyle(buttonStyle);
            mainHBox.getChildren().remove(vbox1);
            mainHBox.getChildren().add(stackPane);
            vbox1= null;
        }
        public void BackfromviewReservation(){
            mainStackpane.getChildren().remove(scrollPane);
            mainHBox.getChildren().add(stackPane);
        }
}