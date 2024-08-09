package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UGNavigate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GoogleMapsGeocoder geocoder = new GoogleMapsGeocoder();
        GoogleMapsAPI googleMapsAPI = new GoogleMapsAPI();

        System.out.println("Enter the start location: ");
        String startLocation = scanner.nextLine();

        System.out.println("Enter the end location: ");
        String endLocation = scanner.nextLine();

        try {
            String[] startCoords = geocoder.getCoordinates(startLocation);
            String[] endCoords = geocoder.getCoordinates(endLocation);

            String start = startCoords[0] + "," + startCoords[1];
            String end = endCoords[0] + "," + endCoords[1];

            String jsonResponse = googleMapsAPI.getDirections(start, end);
            googleMapsAPI.parseAndDisplayDirections(jsonResponse);

        } catch (IOException e) {
            System.out.println("Failed to fetch route data: " + e.getMessage());
        }
    }
}
