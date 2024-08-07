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
        JsonArray paths = obj.getAsJsonArray("paths");

        if (paths.size() > 0) {
            JsonArray instructions = paths.get(0).getAsJsonObject().getAsJsonArray("instructions");
            for (int i = 0; i < instructions.size() - 1; i++) {
                JsonObject current = instructions.get(i).getAsJsonObject();
                JsonObject next = instructions.get(i + 1).getAsJsonObject();
                String start = current.get("text").getAsString();
                String end = next.get("text").getAsString();
                int distance = current.get("distance").getAsInt();
                edges.add(new Edge(start, end, distance));
            }
        }
        return edges;
    }
}
