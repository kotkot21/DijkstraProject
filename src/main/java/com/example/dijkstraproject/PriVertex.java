package com.example.dijkstraproject;

public class PriVertex implements Comparable<PriVertex>{
    Vertex vertex;
    Double weight;

    public PriVertex(Vertex vertex, Double weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    @Override
    public int compareTo(PriVertex o) {
        return Double.compare(this.weight,o.weight);
    }
}
