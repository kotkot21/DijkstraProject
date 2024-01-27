package com.example.dijkstraproject;

import java.util.*;


public class Dijkstra {
    private final Double INF = Double.MAX_VALUE;

    //T(n)=O(E+V)
    public static HashMap[] Dijkstra(Graph g, Vertex startVertex) {

        HashMap<String, Double> Distances = new HashMap<>();
        HashMap<String, Vertex> prev = new HashMap<>();

        PriorityQueue<PriVertex> pri = new PriorityQueue<PriVertex>();
        pri.add(new PriVertex(startVertex, 0.0));

        for (Vertex v : g.getVertices()) {
            if (v != startVertex) {
                Distances.put(v.getData(), Double.MAX_VALUE);
            }
            prev.put(v.getData(), new Vertex("NUll", 0, 0));
        }

        Distances.put(startVertex.getData(), 0.0);
        double distance;

        while (!pri.isEmpty()) {
            Vertex current = pri.poll().vertex;

            for (Edge e : current.getEdges()) {
                double alt = Distances.get(current.getData()) + e.getWeight();
                String adj = e.getEnd().getData();
                if (alt < Distances.get(adj)) {
                    Distances.put(adj, alt);
                    prev.put(adj, current);
                    pri.add(new PriVertex(e.getEnd(), Distances.get(adj)));
                    distance = Distances.get(adj);
                }
            }
        }
        return new HashMap[]{Distances, prev};

    }

    public List<String> ShortestPath(Graph g, Vertex Source, Vertex Target) {
        HashMap[] DijkstraMap = Dijkstra(g, Source);
        HashMap Distances = DijkstraMap[0];
        HashMap Previous = DijkstraMap[1];
        List<String> spath = new ArrayList<>();

        double distance = (double) Distances.get(Target.getData());
        spath.add(String.valueOf(distance));
        ArrayList<Vertex> path = new ArrayList<>();
        Vertex v = Target;

        while (!v.getData().equals("NUll")) {
            path.add(0, v);
            v = (Vertex) Previous.get(v.getData());
        }
        String[] arr = new String[path.size()];
        for (Vertex PathVertex : path) {
            spath.add(PathVertex.getData());
        }
        return spath;
    }

}
