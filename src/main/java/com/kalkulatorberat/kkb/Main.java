package com.kalkulatorberat.kkb;

import java.util.logging.*;
import java.io.*;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    static final double KILOGRAM_TO_GRAM = 1000.0;
    static final double TON_TO_GRAM = 1000000.0;
    static final double KUINTAL_TO_GRAM = 100000.0;

    public static void main(String[] args) {
        launch(args);
    }

    public static class data {
        private final SimpleStringProperty f1;
        private final SimpleStringProperty f2;
        private final SimpleStringProperty f3;
        private final SimpleStringProperty f4;

        public String getF1() {
            return f1.get();
        }

        public String getF2() {
            return f2.get();
        }

        public String getF3() {
            return f3.get();
        }

        public String getF4() {
            return f4.get();
        }

        data(String f1, String f2, String f3, String f4) {
            this.f1 = new SimpleStringProperty(f1);
            this.f2 = new SimpleStringProperty(f2);
            this.f3 = new SimpleStringProperty(f3);
            this.f4 = new SimpleStringProperty(f4);
        }
    }

    private final TableView<data> tableView = new TableView<>();

    private final ObservableList<data> dataList
            = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Kalkulator Konversi Berat");

        VBox vbox = new VBox();
        Scene scene = new Scene(vbox, 330, 300);
        primaryStage.setScene(scene);

        TextField inputField = new TextField();
        inputField.setPromptText("Masukkan berat");

        ComboBox<String> inputUnit = new ComboBox<>();
        inputUnit.getItems().addAll("KG", "G", "Ton", "Kuintal");
        inputUnit.setValue("KG");

        ComboBox<String> outputUnit = new ComboBox<>();
        outputUnit.getItems().addAll("KG", "G", "Ton", "Kuintal");
        outputUnit.setValue("KG");

        Label outputLabel = new Label();

        Button convertButton = new Button("Konversi");

        convertButton.setOnAction(e -> {
            try {
                double inputWeight = Double.parseDouble(inputField.getText());
                double weightInGrams;

                switch (inputUnit.getValue()) {
                    case "KG" -> weightInGrams = inputWeight * KILOGRAM_TO_GRAM;
                    case "Ton" -> weightInGrams = inputWeight * TON_TO_GRAM;
                    case "Kuintal" -> weightInGrams = inputWeight * KUINTAL_TO_GRAM;
                    default -> weightInGrams = inputWeight;
                }

                double outputWeight;
                switch (outputUnit.getValue()) {
                    case "KG" -> outputWeight = weightInGrams / KILOGRAM_TO_GRAM;
                    case "Ton" -> outputWeight = weightInGrams / TON_TO_GRAM;
                    case "Kuintal" -> outputWeight = weightInGrams / KUINTAL_TO_GRAM;
                    default -> outputWeight = weightInGrams;
                }

                String resultText = "Hasil konversi: " + outputWeight + " " + outputUnit.getValue();
                outputLabel.setText(resultText);

                // Menambahkan riwayat ke dalam file
                saveToHistory(inputWeight, inputUnit.getValue(), outputWeight, outputUnit.getValue());

            } catch (NumberFormatException ex) {
                outputLabel.setText("Masukkan angka yang valid untuk berat.");
            }
        });
        TableColumn columnF1 = new TableColumn("Berat Masuk");
        columnF1.setCellValueFactory(new PropertyValueFactory<>("f1"));

        TableColumn columnF2 = new TableColumn("Unit Masuk");
        columnF2.setCellValueFactory(new PropertyValueFactory<>("f2"));

        TableColumn columnF3 = new TableColumn("Berat Keluar");
        columnF3.setCellValueFactory(new PropertyValueFactory<>("f3"));

        TableColumn columnF4 = new TableColumn("Unit Keluar");
        columnF4.setCellValueFactory(new PropertyValueFactory<>("f4"));

        tableView.setItems(dataList);
        tableView.getColumns().addAll(columnF1, columnF2, columnF3, columnF4);
        vbox.getChildren().addAll(inputField, inputUnit, outputUnit, convertButton, outputLabel, tableView);
        readCSV();

        primaryStage.show();
    }


    private void saveToHistory(double inputWeight, String inputUnit, double outputWeight, String outputUnit) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("riwayat.csv", true))) {
            if (new File("riwayat.csv").length() == 0) {
                writer.write("InputWeight,InputUnit,OutputWeight,OutputUnit");
                writer.newLine();
            }

            String historyEntry = String.format("%.3f,%s,%.3f,%s", inputWeight, inputUnit, outputWeight, outputUnit);
            writer.write(historyEntry);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readCSV() {
        String CsvFile = "C:/Users/ASUS/Downloads/vscode/KKB/riwayat.csv";
        String FieldDelimiter = ",";

        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(CsvFile));

            String line;
            boolean isFirstLine = true; // Flag untuk menandai baris pertama (header)
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Lewati baris header
                }

                String[] fields = line.split(FieldDelimiter, -1);

                // Pastikan untuk sesuai dengan jumlah kolom yang benar
                data record = new data(fields[0], fields[1], fields[2], fields[3]);
                dataList.add(record);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}