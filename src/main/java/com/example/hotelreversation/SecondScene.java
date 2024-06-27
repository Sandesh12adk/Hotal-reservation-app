package com.example.hotelreversation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SecondScene {
    public static Scene secondScene() {
        HBox hBox = new HBox();
        Label label = new Label("Successfully logged In");
        hBox.getChildren().add(label);
        return new Scene(hBox, 1000, 650, Color.GRAY);
    }
}

