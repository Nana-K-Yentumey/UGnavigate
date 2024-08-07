package com.example;

import java.util.List;

public class QuickSort {
    public static void quickSort(List<Route> routes, int low, int high) {
        if (low < high) {
            int pi = partition(routes, low, high);
            quickSort(routes, low, pi - 1);
            quickSort(routes, pi + 1, high);
        }
    }

    private static int partition(List<Route> routes, int low, int high) {
        Route pivot = routes.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (routes.get(j).getDistance() < pivot.getDistance()) {
                i++;
                Route temp = routes.get(i);
                routes.set(i, routes.get(j));
                routes.set(j, temp);
            }
        }
        Route temp = routes.get(i + 1);
        routes.set(i + 1, routes.get(high));
        routes.set(high, temp);
        return i + 1;
    }
}
