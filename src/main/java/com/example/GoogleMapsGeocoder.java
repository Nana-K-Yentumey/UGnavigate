package com.example;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GoogleMapsGeocoder {
    private String apiKey;
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json";

    public GoogleMapsGeocoder(String apiKey) {
        this.apiKey = apiKey;
    }

    public String[] getCoordinates(String location) throws IOException {
        String encodedLocation = URLEncoder.encode(location + ", University of Ghana", StandardCharsets.UTF_8.toString());
        String urlString = BASE_URL + "?address=" + encodedLocation + "&key=" + apiKey;
        System.out.println("Request URL: " + urlString); // Debug print
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode); // Debug print
        if (responseCode != 200) {
            throw new IOException("Server returned HTTP response code: " + responseCode + " for URL: " + urlString);
        }

        Scanner scanner = new Scanner(url.openStream());
        StringBuilder response = new StringBuilder();
        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();

        JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
        JsonObject locationObject = jsonObject.getAsJsonArray("results")
                                            .get(0)
                                            .getAsJsonObject()
                                            .getAsJsonObject("geometry")
                                            .getAsJsonObject("location");
        String lat = locationObject.get("lat").getAsString();
        String lng = locationObject.get("lng").getAsString();

        System.out.println("Coordinates for " + location + ": " + lat + ", " + lng); // Debug print

        return new String[] { lat, lng };
    }
}
