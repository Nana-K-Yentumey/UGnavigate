package com.example;

public class Node {
    private String vertex;
    private int distance;

    public Node(String vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    public String getVertex() {
        return vertex;
    }

    public int getDistance() {
        return distance;
    }
}
