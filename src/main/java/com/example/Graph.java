package com.example;

import java.util.*;

public class Graph {
    private final Map<String, List<Edge>> adjList = new HashMap<>();

    public void addEdge(String start, String end, int weight) {
        adjList.computeIfAbsent(start, k -> new ArrayList<>()).add(new Edge(start, end, weight));
        adjList.computeIfAbsent(end, k -> new ArrayList<>()).add(new Edge(end, start, weight));
    }

    public List<String> shortestPath(String start, String end) {
        if (!adjList.containsKey(start) || !adjList.containsKey(end)) {
            System.out.println("Start or end node not found in the graph.");
            return Collections.emptyList();
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getDistance));
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        Set<String> visited = new HashSet<>();

        for (String vertex : adjList.keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(start, 0);

        pq.add(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();
            String currentVertex = currentNode.getVertex();

            if (visited.contains(currentVertex))
                continue;
            visited.add(currentVertex);

            for (Edge edge : adjList.get(currentVertex)) {
                String neighbor = edge.getEnd();
                int newDist = distances.get(currentVertex) + edge.getWeight();

                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previous.put(neighbor, currentVertex);
                    pq.add(new Node(neighbor, newDist));
                }
            }
        }

        List<String> path = new ArrayList<>();
        for (String at = end; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);

        if (path.size() == 1 && !start.equals(end)) {
            System.out.println("No path exists between " + start + " and " + end);
            return Collections.emptyList();
        }

        return path;
    }
}
