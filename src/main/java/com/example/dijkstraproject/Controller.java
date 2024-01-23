package com.example.dijkstraproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Controller {

    @FXML
    ImageView myImage;
    Button[] b;
    @FXML
    public void initialize() throws IOException {
        Readfile();
    }
    public double getWidth(){
       return myImage.getFitWidth();
    }
    public double getheight(){
        return myImage.getFitHeight();
    }
    public void Readfile() throws IOException {
        Graph g = new Graph();
        int width = (int) getWidth();
        int height = (int) getheight();
        BufferedReader br1 = new BufferedReader(new FileReader("C:\\Users\\ahmad\\Downloads\\1pro2+3\\DijkstraProject\\src\\Data\\Gaza.txt"));
        BufferedReader br2 = new BufferedReader(new FileReader("C:\\Users\\ahmad\\Downloads\\1pro2+3\\DijkstraProject\\src\\Data\\coo.txt"));
        int x = Integer.parseInt(br1.readLine());
        b = new Button[x];
        System.out.println(x);
        String Line1;
        String Line2;
        boolean isStreets = false;
        int i = 0;
        while ((Line1 = br1.readLine()) != null){
            String[] data = Line1.split(", ");
            if (Line1.trim().equalsIgnoreCase("Streets"))
            {
                isStreets = true;
                continue;
            }
            if (!isStreets){
                String City = data[0].trim();
                double latitude = Double.parseDouble(data[1].trim());
                double longitude= Double.parseDouble(data[2].trim());
                Vertex v = new Vertex(City,latitude,longitude);
                g.addVertex(v);
//                System.out.println("City " + City);
            }
            else {
                String streets = data[0].trim();
                double latitude = Double.parseDouble(data[1].trim());
                double longitude= Double.parseDouble(data[2].trim());
                Vertex v = new Vertex(streets,latitude,longitude);
                g.addVertex(v);
                int xPixel = (int) ((longitude + 180.0) * (width / 360.0));
                int yPixel = (int) ((90.0 - latitude) * (height / 180.0));
                if (i < b.length) {
                b[i] = new Button();
                b[i].setLayoutX(xPixel);
                b[i].setLayoutY(yPixel);
                i++;
                }
                //                System.out.println("Streets" + streets);
            }
        }
        while ((Line2 = br2.readLine()) != null){
            String[] data = Line2.split(", ");
//            System.out.println(g.getVertexByName(data[0]).getData());
            g.addEdge(g.getVertexByName(data[0]),g.getVertexByName(data[1]));
        }
        Dijkstra d = new Dijkstra();
        d.ShortestPath(g,g.getVertexByName("Beit Hanoun"),g.getVertexByName("Gaza"));
    }
}
