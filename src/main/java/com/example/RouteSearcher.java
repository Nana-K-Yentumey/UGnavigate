package com.example;

import java.util.ArrayList;
import java.util.List;

public class RouteSearcher {
    public static List<Route> searchRoutesByLandmark(List<Route> routes, String landmark) {
        List<Route> result = new ArrayList<>();
        for (Route route : routes) {
            if (route.getStart().equalsIgnoreCase(landmark) || route.getEnd().equalsIgnoreCase(landmark)) {
                result.add(route);
            }
        }
        return result;
    }
}
