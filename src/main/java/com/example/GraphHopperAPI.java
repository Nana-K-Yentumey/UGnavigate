package com.example;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class GraphHopperAPI {
    private static final String API_KEY = "680e1f26-83df-425c-8d2a-98bb474a8d03";
    private static final String BASE_URL = "https://graphhopper.com/api/1/route";

    public String getRoute(String start, String end) throws IOException {
        String urlString = BASE_URL + "?point=" + start + "&point=" + end + "&vehicle=foot&locale=en&key=" + API_KEY;
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

        return response.toString();
    }
}
