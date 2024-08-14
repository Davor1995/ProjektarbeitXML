package com.example;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.io.File;

public class Main extends Application {
    private File selectedFile;
    private TextArea textArea;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lieferschein PDF Generator");

        Label label = new Label("Wähle eine XML Datei:");
        Button btnOpen = new Button("Öffnen...");
        textArea = new TextArea();
        textArea.setEditable(false);
        Button btnCreatePdf = new Button("PDF erstellen");

        btnOpen.setOnAction(e -> openFile(primaryStage));
        btnCreatePdf.setOnAction(e -> createPdf());

        VBox vbox = new VBox(label, btnOpen, textArea, btnCreatePdf);
        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
        selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            textArea.setText("XML-Datei ausgewählt: " + selectedFile.getPath());
        }
    }

    private void createPdf() {
        if (selectedFile != null) {
            PdfGenerator pdfGenerator = new PdfGenerator();
            pdfGenerator.generatePdfFromXml(selectedFile.getAbsolutePath(), "output/Lieferschein.pdf");
            textArea.setText("PDF wurde erfolgreich erstellt!");
        } else {
            textArea.setText("Keine XML-Datei ausgewählt.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
