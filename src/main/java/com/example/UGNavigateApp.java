package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.io.IOException;

public class UGNavigateApp extends Application {
    private GoogleMapsGeocoder geocoder;
    private GoogleMapsAPI googleMapsAPI;

    @Override
    public void start(Stage primaryStage) {
        // Load the API key from the .env file
        String apiKey;
        try {
            EnvLoader envLoader = new EnvLoader(".env");
            apiKey = envLoader.get("API_KEY");
        } catch (IOException e) {
            System.out.println("Error loading .env file: " + e.getMessage());
            return;
        }

        // Initialize API classes with the API key
        geocoder = new GoogleMapsGeocoder(apiKey);
        googleMapsAPI = new GoogleMapsAPI(apiKey);

        primaryStage.setTitle("UG Navigate");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Start location input
        Label startLabel = new Label("Start Location:");
        GridPane.setConstraints(startLabel, 0, 0);
        TextField startInput = new TextField();
        GridPane.setConstraints(startInput, 1, 0);

        // End location input
        Label endLabel = new Label("End Location:");
        GridPane.setConstraints(endLabel, 0, 1);
        TextField endInput = new TextField();
        GridPane.setConstraints(endInput, 1, 1);

        // Directions output
        TextArea directionsOutput = new TextArea();
        directionsOutput.setWrapText(true);
        directionsOutput.setEditable(false);
        GridPane.setConstraints(directionsOutput, 0, 3, 2, 1);

        // Get directions button
        Button getDirectionsButton = new Button("Get Directions");
        GridPane.setConstraints(getDirectionsButton, 1, 2);

        getDirectionsButton.setOnAction(e -> {
            String startLocation = startInput.getText().trim();
            String endLocation = endInput.getText().trim();

            if (!startLocation.isEmpty() && !endLocation.isEmpty()) {
                try {
                    String[] startCoords = geocoder.getCoordinates(startLocation);
                    String[] endCoords = geocoder.getCoordinates(endLocation);

                    String start = startCoords[0] + "," + startCoords[1];
                    String end = endCoords[0] + "," + endCoords[1];

                    String jsonResponse = googleMapsAPI.getDirections(start, end);
                    directionsOutput.setText(googleMapsAPI.parseAndDisplayDirections(jsonResponse));
                } catch (IOException ex) {
                    directionsOutput.setText("Error fetching directions: " + ex.getMessage());
                }
            }
        });

        grid.getChildren().addAll(startLabel, startInput, endLabel, endInput, getDirectionsButton, directionsOutput);

        Scene scene = new Scene(grid, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
