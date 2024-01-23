package com.example.dijkstraproject;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    double width;
    double height;
    String start;
    double y;
    double x;

    @Override
    public void start(Stage stage) throws IOException {
        VBox mainbox = new VBox();
        AnchorPane pane = new AnchorPane();

        pane.setPrefWidth(728);
        pane.setPrefHeight(619);
        TextArea TextPath = new TextArea();

        TextPath.setPrefWidth(375);
        TextPath.setPrefHeight(149);

        TextPath.setLayoutX(190);
        TextPath.setLayoutY(446);
        Image imgpath = new Image("file:/C:/Users/ahmad/Downloads/1pro2+3/DijkstraProject/src/gaza1.png");
        ImageView myImage = new ImageView(imgpath);


        myImage.setFitWidth(375);
        myImage.setFitHeight(442);

        myImage.setLayoutX(195);

        width =  myImage.getFitWidth();
        height = myImage.getFitHeight();

        Group group = new Group(myImage);
        double[] d = new double[2];
        Graph g = new Graph();
        BufferedReader br1 = new BufferedReader(new FileReader("C:\\Users\\ahmad\\Downloads\\1pro2+3\\DijkstraProject\\src\\Data\\Gaza.txt"));
        BufferedReader br2 = new BufferedReader(new FileReader("C:\\Users\\ahmad\\Downloads\\1pro2+3\\DijkstraProject\\src\\Data\\coo.txt"));
        String Line1;
        String Line2;
        boolean isStreets = false;
        int i = 0;

        double minLongitude = Double.MAX_VALUE;
        double maxLongitude = Double.MIN_VALUE;
        double minLatitude = Double.MAX_VALUE;
        double maxLatitude = Double.MIN_VALUE;

        Label src = new Label("Source");
        ComboBox<String> source = new ComboBox<>();
        source.setPrefWidth(150);

        Label dst = new Label("Distinitaion");
        ComboBox<String> Dist = new ComboBox<>();
        Dist.setPrefWidth(150);

        final boolean[] flag = {false};
        while ((Line1 = br1.readLine()) != null) {
            String[] data = Line1.split(", ");
            if (Line1.trim().equalsIgnoreCase("Streets")) {
                isStreets = true;
                continue;
            }
            if (!isStreets) {
                String City = data[0].trim();
                double latitude = Double.parseDouble(data[1].trim());
                double longitude = Double.parseDouble(data[2].trim());
                Vertex v = new Vertex(City, latitude, longitude);
                g.addVertex(v);
                source.getItems().add(City);
                Dist.getItems().add(City);
                Button b = new Button();
                b.setGraphic(new Label(City));

                b.setOnAction(event -> {
                    if (!flag[0] == true) {
                        source.getSelectionModel().select(City);
                        x = b.getLayoutX();
                        y = b.getLayoutY();
                        flag[0] = true;
                    } else {
                        Dist.getSelectionModel().select(City);
                        flag[0] = false;
                    }
                });
                d[0] = convertLatLonToPixel(latitude, longitude)[0];
                d[1] = convertLatLonToPixel(latitude, longitude)[1];
//                d[0]=convertLatLonToPixel(latitude,longitude)[0];
//                d[1]=convertLatLonToPixel(latitude,longitude)[1];
                b.setLayoutX(d[0]);
                b.setLayoutY(d[1]);
//                b.setStyle(
//                        "-fx-background-color: #4CAF50; " +
//                                "-fx-text-fill: white; " +
//                                "-fx-font-size: 14px; " +
//                                "-fx-min-width: 40px; " +
//                                "-fx-min-height: 40px; " +
//                                "-fx-max-width: 40px; " +
//                                "-fx-max-height: 40px; " +
//                                "-fx-shape: 'M20 40 C8.954 40 0 31.046 0 20 S8.954 0 20 0s20 8.954 20 20s-8.954 20-20 20z';"
//                );
                group.getChildren().add(b);
//                System.out.println("City " + City);
            } else {
                String streets = data[0].trim();
                double latitude = Double.parseDouble(data[1].trim());
                double longitude = Double.parseDouble(data[2].trim());
                Vertex v = new Vertex(streets, latitude, longitude);
                g.addVertex(v);
                //                System.out.println("Streets" + streets);
            }
        }
        Button find = new Button("Find shortest path");



        Dijkstra dijkstra = new Dijkstra();
        List<String> printPath = new ArrayList<>();
        StringBuilder storepath = new StringBuilder();
        source.setOnAction(e -> {
            String s = source.getSelectionModel().getSelectedItem();
            start = s;
            Dist.setOnAction(event -> {
                String ds = Dist.getSelectionModel().getSelectedItem();
                printPath.addAll(dijkstra.ShortestPath(g, g.getVertexByName(start), g.getVertexByName(ds)));
                find.setOnAction(action->{
                    storepath.append("The Distance is " + printPath.get(0) + "\t");
                    for (int index = 1; index < printPath.size() - 1; index++) {
                        Line line = new Line();
                        storepath.append(printPath.get(index) + "->");
                    }
                    TextPath.setText(storepath + ds);
                });

                for (int index = 1; index < printPath.size() - 1; index++) {
                    String cityName = printPath.get(index);
                    Button cityButton = findButtonByCityName(group, cityName);
                    if (cityButton != null) {
                        Line line = new Line(x, y, cityButton.getLayoutX(), cityButton.getLayoutY());
                        group.getChildren().add(line);
                    }
                }
            });
        });


//        dijkstra.ShortestPath(g,g.getVertexByName(source.getSelectionModel().getSelectedItem()),
//                g.getVertexByName(Dist.getSelectionModel().getSelectedItem()));

        while ((Line2 = br2.readLine()) != null) {
            String[] data = Line2.split(", ");
//            System.out.println(g.getVertexByName(data[0]).getData());
            g.addEdge(g.getVertexByName(data[0]), g.getVertexByName(data[1]));
        }

        VBox List = new VBox();
        List.setAlignment(Pos.TOP_LEFT);
        List.setSpacing(2.5);

        List.setPrefWidth(134);
        List.setPrefHeight(200);

        List.getChildren().addAll(src, source, dst, Dist, find);

        pane.getChildren().addAll(TextPath, List, group);
        mainbox.getChildren().add(pane);
        Scene scene = new Scene(mainbox);
        stage.setTitle("Dijkstra");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private Button findButtonByCityName(Group group, String cityName) {
        for (Node node : group.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (button.getGraphic() instanceof Label) {
                    Label label = (Label) button.getGraphic();
                    if (label.getText().equals(cityName)) {
                        return button;
                    }
                }
            }
        }
        return null;
    }

    private double[] convertLatLonToPixel(double latitude, double longitude) {
        double mapWidth = width;
        double mapHeight = height;
        double mapMinLat = 31.224624341137446;
        double mapMaxLat = 31.608442331932064;
        double mapMinLon = 34.20386296727042;
        double mapMaxLon = 34.592504321470955;
        // calculate the percentage of the way the coordinates are across the bounding box
        double xPercent = (longitude - mapMinLon) / (mapMaxLon - mapMinLon);
        double yPercent = (latitude - mapMinLat) / (mapMaxLat - mapMinLat);

        // convert these percentages into pixel values
        double xPixel = (xPercent * mapWidth);
        // y-coordinates in most graphics systems start at the top, so we invert the yPercent
        double yPixel = ((1 - yPercent) * mapHeight);

        return new double[]{xPixel, yPixel};
    }

    private double[] latpixel(double latitude, double longitude) {
        double mapWidth = width;
        double mapHeight = height;
        double mapMinLat = 31.21682524779672;
        double mapMaxLat = 31.60666996149046;
        double mapMinLon = 34.11915443676544;
        double mapMaxLon = 34.5722355298608;

        double mapLongitude = mapMaxLon - mapMinLon;
        double mapLatitude = mapMinLat - mapMaxLat;
        longitude = longitude - mapMinLon;
        latitude = mapMinLat - latitude;

        double x = (mapWidth * (longitude / mapLongitude));
        double y = (mapHeight * (latitude / mapLatitude));

        return new double[]{x, y};
    }


}