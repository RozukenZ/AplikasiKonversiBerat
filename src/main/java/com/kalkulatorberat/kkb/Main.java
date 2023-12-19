package com.kalkulatorberat.kkb;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    static final double KILOGRAM_TO_GRAM = 1000.0;
    static final double TON_TO_GRAM = 1000000.0;
    static final double KUINTAL_TO_GRAM = 100000.0;

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

        convertButton.setOnAction(e -> {
            try {
                double inputWeight = Double.parseDouble(inputField.getText());
                double weightInGrams;

                switch (inputUnit.getValue()) {
                    case "KG":
                        weightInGrams = inputWeight * KILOGRAM_TO_GRAM;
                        break;
                    case "Ton":
                        weightInGrams = inputWeight * TON_TO_GRAM;
                        break;
                    case "Kuintal":
                        weightInGrams = inputWeight * KUINTAL_TO_GRAM;
                        break;
                    default:
                        weightInGrams = inputWeight;
                }

                double outputWeight;
                switch (outputUnit.getValue()) {
                    case "KG":
                        outputWeight = weightInGrams / KILOGRAM_TO_GRAM;
                        break;
                    case "Ton":
                        outputWeight = weightInGrams / TON_TO_GRAM;
                        break;
                    case "Kuintal":
                        outputWeight = weightInGrams / KUINTAL_TO_GRAM;
                        break;
                    default:
                        outputWeight = weightInGrams;
                }


            } catch (NumberFormatException ex) {

            }
        });

        Label outputLabel = new Label();

        vbox.getChildren().addAll(inputField, inputUnit, outputUnit, convertButton, outputLabel);
        primaryStage.show();
    }
}