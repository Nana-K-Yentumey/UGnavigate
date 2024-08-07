package com.example;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GeocoderAPI {
    private static final String API_KEY = "8a6928c2e60d4241be735eb9ca7e8b0d";
    private static final String BASE_URL = "https://api.opencagedata.com/geocode/v1/json";

    public String[] getCoordinates(String location) throws IOException {
        String encodedLocation = URLEncoder.encode(location + ", University of Ghana",
                StandardCharsets.UTF_8.toString());
        String urlString = BASE_URL + "?q=" + encodedLocation + "&key=" + API_KEY;
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
        JsonObject geometry = jsonObject.getAsJsonArray("results").get(0).getAsJsonObject().getAsJsonObject("geometry");
        String lat = geometry.get("lat").getAsString();
        String lng = geometry.get("lng").getAsString();

        System.out.println("Coordinates for " + location + ": " + lat + ", " + lng); // Debug print

        return new String[] { lat, lng };
    }
}
