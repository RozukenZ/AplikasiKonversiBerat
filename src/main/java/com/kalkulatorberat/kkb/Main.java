package com.kalkulatorberat.kkb;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Kalkulator Konversi Berat");

        VBox vbox = new VBox();
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);

        TextField inputField = new TextField();
        inputField.setPromptText("Masukkan berat");

        ComboBox<String> inputUnit = new ComboBox<>();
        inputUnit.getItems().addAll("KG", "G", "Ton", "Kuintal");
        inputUnit.setValue("KG");

        ComboBox<String> outputUnit = new ComboBox<>();
        outputUnit.getItems().addAll("KG", "G", "Ton", "Kuintal");
        outputUnit.setValue("KG");

        Button convertButton = new Button("Konversi");

        vbox.getChildren().addAll(inputField, inputUnit, outputUnit, convertButton);

        primaryStage.show();
    }
}