package com.kalkulatorberat.kkb;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private final ObservableList<data> dataList = FXCollections.observableArrayList();
    private final TextField inputField = new TextField();
    private final ComboBox<String> inputUnit = new ComboBox<>(FXCollections.observableArrayList("KG", "G", "Ton", "Kuintal"));
    private final ComboBox<String> outputUnit = new ComboBox<>(FXCollections.observableArrayList("KG", "G", "Ton", "Kuintal"));
    private final Label outputLabel = new Label();
    private final Button convertButton = new Button("Konversi");

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Alat Konversi Berat");

        VBox vbox = new VBox(10);
        Scene scene = new Scene(vbox, 450, 400);
        primaryStage.setScene(scene);

        HBox inputBox = new HBox(10);
        inputBox.getChildren().addAll(new Label("Masukkan berat:"), inputField, inputUnit);

        HBox outputBox = new HBox(10);
        outputBox.getChildren().addAll(new Label("Hasil konversi:"), outputLabel);

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(convertButton);

        HBox unitBox = new HBox(10);
        unitBox.getChildren().addAll(new Label("Dari unit:"), inputUnit, new Label("Ke unit:"), outputUnit);

        VBox historyBox = new VBox(10);
        historyBox.getChildren().addAll(new Label("Riwayat Konversi"), tableView);

        vbox.getChildren().addAll(inputBox, unitBox, buttonBox, outputBox, historyBox);
        primaryStage.show();

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

                // Menambahkan hasil konversi ke dalam tabel
                data convertedData = new data(
                        String.valueOf(inputWeight),
                        inputUnit.getValue(),
                        String.valueOf(outputWeight),
                        outputUnit.getValue()
                );
                dataList.add(convertedData);

                // Update TableView
                tableView.setItems(dataList);

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

        // Create a delete button column
        TableColumn<data, Void> deleteButtonColumn = new TableColumn<>("Delete");
        deleteButtonColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    // Get the selected data item
                    data rowData = getTableView().getItems().get(getIndex());

                    // Remove the item from TableView
                    tableView.getItems().remove(rowData);

                    // Update the CSV file after deletion
                    updateCSVFile();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        tableView.setItems(dataList);
        tableView.getColumns().addAll(columnF1, columnF2, columnF3, columnF4, deleteButtonColumn);
        readCSV();
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

    private void updateCSVFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("riwayat.csv"))) {
            writer.write("InputWeight,InputUnit,OutputWeight,OutputUnit");
            writer.newLine();

            // Write the data from the TableView to the CSV file
            for (data record : dataList) {
                String historyEntry = String.format(
                        "%.3f,%s,%.3f,%s",
                        Double.parseDouble(record.getF1()), record.getF2(),
                        Double.parseDouble(record.getF3()), record.getF4()
                );
                writer.write(historyEntry);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readCSV() {
        String CsvFile = "riwayat.csv";
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
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
