package com.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class RouteParser {
    public List<Edge> parseRoute(String jsonResponse) {
        List<Edge> edges = new ArrayList<>();
        Gson gson = new Gson();
        JsonObject obj = gson.fromJson(jsonResponse, JsonObject.class);
        JsonArray routes = obj.getAsJsonArray("routes");

        if (routes.size() > 0) {
            JsonArray legs = routes.get(0).getAsJsonObject().getAsJsonArray("legs");
            JsonArray steps = legs.get(0).getAsJsonObject().getAsJsonArray("steps");

            for (int i = 0; i < steps.size() - 1; i++) {
                JsonObject currentStep = steps.get(i).getAsJsonObject();
                JsonObject nextStep = steps.get(i + 1).getAsJsonObject();
                String start = currentStep.getAsJsonObject("start_location").toString();
                String end = nextStep.getAsJsonObject("end_location").toString();
                int distance = currentStep.getAsJsonObject("distance").get("value").getAsInt();
                edges.add(new Edge(start, end, distance));
            }
        }
        return edges;
    }
}
