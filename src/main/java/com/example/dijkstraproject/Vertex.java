package com.example.dijkstraproject;

import java.util.ArrayList;

public class Vertex {
    private String data;
    private double latitude;
    private double longitude;
    private ArrayList<Edge> edges;

    public Vertex(String data, double latitude, double longitude) {
        this.data = data;
        this.latitude = latitude;
        this.longitude = longitude;
        this.edges = new ArrayList<Edge>();
    }

    public void addEdge(Vertex endvertex, Double Weigth) {
        this.edges.add(new Edge(this, endvertex, Weigth));
    }

    public String getData() {
        return data;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
