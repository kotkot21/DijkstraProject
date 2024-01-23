package com.example.dijkstraproject;

public class Edge implements Comparable<Edge>{
    private Vertex start;
    private Vertex end;
    private Double weight;

    public Edge(Vertex start, Vertex end, Double weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public Vertex getStart() {
        return start;
    }

    public Vertex getEnd() {
        return end;
    }

    public Double getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Double.compare(weight, other.getWeight());
    }
}
