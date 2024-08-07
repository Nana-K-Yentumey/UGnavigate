package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UGNavigate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Graph graph = new Graph();
        List<Route> routes = new ArrayList<>();
        GeocoderAPI geocoder = new GeocoderAPI();

        System.out.println("Enter the start location: ");
        String startLocation = scanner.nextLine();

        System.out.println("Enter the end location: ");
        String endLocation = scanner.nextLine();

        try {
            String[] startCoords = geocoder.getCoordinates(startLocation);
            String[] endCoords = geocoder.getCoordinates(endLocation);

            // Check if the coordinates are identical
            if (startCoords[0].equals(endCoords[0]) && startCoords[1].equals(endCoords[1])) {
                System.out.println("Start and end locations are the same. Please enter different locations.");
                return;
            }

            String start = startCoords[0] + "," + startCoords[1];
            String end = endCoords[0] + "," + endCoords[1];

            System.out.println("Start coordinates: " + start); // Debug print
            System.out.println("End coordinates: " + end); // Debug print

            GraphHopperAPI api = new GraphHopperAPI();
            RouteParser parser = new RouteParser();

            String jsonResponse = api.getRoute(start, end);
            List<Edge> edges = parser.parseRoute(jsonResponse);

            // Add edges to the graph and routes list
            for (Edge edge : edges) {
                graph.addEdge(edge.getStart(), edge.getEnd(), edge.getWeight());
                routes.add(new Route(edge.getStart(), edge.getEnd(), edge.getWeight()));
            }

            // Get the shortest path
            List<String> path = graph.shortestPath(start, end);
            if (!path.isEmpty()) {
                System.out.println("Shortest path from " + startLocation + " to " + endLocation + ": " + path);
            }
        } catch (IOException e) {
            System.out.println("Failed to fetch route data: " + e.getMessage());
        }

        // Sort the routes by distance
        QuickSort.quickSort(routes, 0, routes.size() - 1);
        System.out.println("Sorted routes by distance:");
        for (Route route : routes) {
            System.out.println(route.getStart() + " -> " + route.getEnd() + ": " + route.getDistance() + " meters");
        }

        // Search routes by landmark
        System.out.println("Enter a landmark to search routes: ");
        String landmark = scanner.nextLine();
        List<Route> filteredRoutes = RouteSearcher.searchRoutesByLandmark(routes, landmark);
        System.out.println("Routes passing through " + landmark + ":");
        for (Route route : filteredRoutes) {
            System.out.println(route.getStart() + " -> " + route.getEnd() + ": " + route.getDistance() + " meters");
        }
    }
}
