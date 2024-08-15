package com.example;

import java.io.IOException;
import java.util.Scanner;

public class UGNavigate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            EnvLoader envLoader = new EnvLoader(".env");
            String apiKey = envLoader.get("API_KEY");

            GoogleMapsGeocoder geocoder = new GoogleMapsGeocoder(apiKey);
            GoogleMapsAPI googleMapsAPI = new GoogleMapsAPI(apiKey);

            while (true) {
                System.out.println("\n=== UG Navigate - Get Directions ===");

                String startLocation = getInput(scanner, "Enter the start location (or 'quit' to exit): ");
                if (startLocation.equalsIgnoreCase("quit"))
                    break;

                String endLocation = getInput(scanner, "Enter the end location: ");

                try {
                    System.out.println("\nFetching directions...");

                    String[] startCoords = geocoder.getCoordinates(startLocation);
                    String[] endCoords = geocoder.getCoordinates(endLocation);

                    String start = startCoords[0] + "," + startCoords[1];
                    String end = endCoords[0] + "," + endCoords[1];

                    String jsonResponse = googleMapsAPI.getDirections(start, end);
                    googleMapsAPI.parseAndDisplayDirections(jsonResponse);

                    System.out.println("\nWould you like to get directions for another route? (yes/no)");
                    String answer = scanner.nextLine().trim().toLowerCase();
                    if (!answer.equals("yes"))
                        break;

                } catch (IOException e) {
                    System.out.println("Error: Failed to fetch route data. " + e.getMessage());
                    System.out.println("Please check your internet connection and try again.");
                }
            }

        } catch (IOException e) {
            System.out.println("Error: Failed to load .env file. " + e.getMessage());
            System.out.println("Please make sure the .env file exists and contains the API_KEY.");
        } finally {
            scanner.close();
            System.out.println("Thank you for using UG Navigate!");
        }
    }

    private static String getInput(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            }
        } while (input.isEmpty());
        return input;
    }
}