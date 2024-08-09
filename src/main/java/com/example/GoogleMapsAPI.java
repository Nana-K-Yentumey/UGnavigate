package com.example;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GoogleMapsAPI {
    private String apiKey;
    private static final String DIRECTIONS_URL = "https://maps.googleapis.com/maps/api/directions/json";

    // Constructor that accepts the API key
    public GoogleMapsAPI(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getDirections(String startCoords, String endCoords) throws IOException {
        String urlString = DIRECTIONS_URL + "?origin=" + startCoords + "&destination=" + endCoords + "&key=" + apiKey;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("Server returned HTTP response code: " + responseCode + " for URL: " + urlString);
        }

        Scanner scanner = new Scanner(url.openStream());
        StringBuilder response = new StringBuilder();
        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();

        return response.toString();
    }

    public void parseAndDisplayDirections(String jsonResponse) {
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonArray routes = jsonObject.getAsJsonArray("routes");

        if (routes.size() > 0) {
            JsonObject route = routes.get(0).getAsJsonObject();
            JsonArray legs = route.getAsJsonArray("legs");
            JsonObject leg = legs.get(0).getAsJsonObject();
            JsonArray steps = leg.getAsJsonArray("steps");

            System.out.println("Directions:");

            for (int i = 0; i < steps.size(); i++) {
                JsonObject step = steps.get(i).getAsJsonObject();
                String instruction = step.get("html_instructions").getAsString().replaceAll("<[^>]*>", "");
                String distance = step.getAsJsonObject("distance").get("text").getAsString();
                String duration = step.getAsJsonObject("duration").get("text").getAsString();
                
                System.out.println((i + 1) + ". " + instruction + " (" + distance + ", " + duration + ")");
            }
        } else {
            System.out.println("No routes found.");
        }
    }
}
