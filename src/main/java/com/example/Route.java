package com.example;

public class Route {
    private String start;
    private String end;
    private int distance;

    public Route(String start, String end, int distance) {
        this.start = start;
        this.end = end;
        this.distance = distance;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public int getDistance() {
        return distance;
    }
}
