package com.example.dijkstraproject;

import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {

    private ArrayList<Vertex> vertices;


    public Graph() {
        this.vertices = new ArrayList<>();
    }

    public Vertex addVertex(Vertex v) {
        Vertex newvertex = v;
        if (this.vertices != null)
            this.vertices.add(newvertex);
        return newvertex;
    }
    public void addEdge(Vertex vertex1, Vertex vertex2){
        //calculate the weight using the Latitude and the Longitude
        double weight = DistanceBetween(vertex1.getLatitude(),vertex1.getLongitude()
                ,vertex2.getLatitude(),vertex2.getLongitude());
        vertex1.addEdge(vertex2,weight);
        vertex2.addEdge(vertex1,weight);
    }

    //Haversine Formula
    private double DistanceBetween(double v1lat, double v1lon, double v2lat, double v2lon) {
        int earthrad = 6371;
        double dlat = degreesToRadians(v2lat - v1lat);
        double dlon = degreesToRadians(v2lon - v1lon);
        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
                Math.cos(degreesToRadians(v1lat)) *
                        Math.cos(degreesToRadians(v2lat)) *
                        Math.sin(dlon / 2) * Math.sin(dlon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthrad * c;
        return distance;
    }
    public Vertex getVertexByName(String VName) {
        Vertex v = null;

        for (Vertex vertex : vertices) {
            if (vertex.getData().equals(VName)) {
                v = vertex;
                break;
            }
        }

        return v;
    }

    public double degreesToRadians(double deg) {
        return deg * Math.PI / 180;
    }


    public ArrayList<Vertex> getVertices() {
        return vertices;
    }
}
