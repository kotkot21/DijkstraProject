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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    Streets street;
    double width;
    double height;




    private Line pathLine = new Line();

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

        TextPath.setLayoutX(250);
        TextPath.setLayoutY(446);
        Image imgpath = new Image("file:/C:/Users/ahmad/Downloads/1pro2+3/DijkstraProject/src/gaza1.png");
        ImageView myImage = new ImageView(imgpath);


        myImage.setFitWidth(500);
        myImage.setFitHeight(442);

        myImage.setLayoutX(195);

        width = myImage.getFitWidth();
        height = myImage.getFitHeight();

        Group group = new Group(myImage);
        double[] d = new double[2];
        double[] st = new double[2];

        Graph g = new Graph();
        BufferedReader br1 = new BufferedReader(new FileReader("C:\\Users\\ahmad\\Downloads\\1pro2+3\\DijkstraProject\\src\\Data\\gaza"));
        BufferedReader br2 = new BufferedReader(new FileReader("C:\\Users\\ahmad\\Downloads\\1pro2+3\\DijkstraProject\\src\\Data\\coo"));
        String Line1;
        String Line2;
        boolean isStreets = false;
        int i = 0;


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
                b.setLayoutX(d[0] + 210);
                b.setLayoutY(d[1] - 15);
//                Label cityLabel = new Label(City);
//                cityLabel.setLayoutX(d[0] + 220);
//                cityLabel.setLayoutY(d[1] - 15);
//                group.getChildren().add(cityLabel);
//                cityLabel.setStyle("-fx-font-size: 10px;");
                b.setPrefWidth(8);
                b.setPrefHeight(8);
                b.setStyle(
                        "-fx-background-color: #fc7f8f; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 14px; " +
                                "-fx-min-width: 8px; " +
                                "-fx-min-height: 8px; " +
                                "-fx-max-width: 8px; " +
                                "-fx-max-height: 8px; " +
                                "-fx-shape: 'M20 40 C8.954 40 0 31.046 0 20 S8.954 0 20 0s20 8.954 20 20s-8.954 20-20 20z';"
                );
                b.setOnMouseEntered(e -> {
                    b.setStyle(
                            "-fx-background-color: #73d4fc; " +
                                    "-fx-text-fill: white; " +
                                    "-fx-font-size: 14px; " +
                                    "-fx-min-width: 12px; " +
                                    "-fx-min-height: 12px; " +
                                    "-fx-max-width: 12px; " +
                                    "-fx-max-height: 12px; " +
                                    "-fx-shape: 'M20 40 C8.954 40 0 31.046 0 20 S8.954 0 20 0s20 8.954 20 20s-8.954 20-20 20z';"
                    );
                    showTooltip(b, City);
                });

                b.setOnMouseExited(e -> {
                    b.setStyle(
                            "-fx-background-color: #fc7f8f; " +
                                    "-fx-text-fill: white; " +
                                    "-fx-font-size: 14px; " +
                                    "-fx-min-width: 8px; " +
                                    "-fx-min-height: 8px; " +
                                    "-fx-max-width: 8px; " +
                                    "-fx-max-height: 8px; " +
                                    "-fx-shape: 'M20 40 C8.954 40 0 31.046 0 20 S8.954 0 20 0s20 8.954 20 20s-8.954 20-20 20z';");
                    Tooltip tooltip = b.getTooltip();
                    if (tooltip != null) {
                        tooltip.hide();
                    }
                });
                group.getChildren().add(b);
            } else {
                Button str = new Button();
                String streets = data[0].trim();
                double latitude = Double.parseDouble(data[1].trim());
                double longitude = Double.parseDouble(data[2].trim());
                st[0] = convertLatLonToPixel(latitude, longitude)[0] + 185;
                st[1] = convertLatLonToPixel(latitude, longitude)[1] - 15;
                str.setLayoutX(st[0]);
                str.setLayoutY(st[1]);
                street = new Streets(streets, st[0], st[1]);

                Vertex v = new Vertex(streets, latitude, longitude);
                g.addVertex(v);
            }
        }
        Button find = new Button("Find shortest path");
        find.setStyle(
                "-fx-background-color: linear-gradient(to right,#00acc1, #9ce8ec); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-min-width: 140px; " +
                        "-fx-min-height: 30px; " +
                        "-fx-max-width: 80px; " +
                        "-fx-max-height: 30px; " +
                        "-fx-border-color: #45a080; " +
                        "-fx-border-radius: 50%; "
        );


        Dijkstra dijkstra = new Dijkstra();
        List<String> printPath = new ArrayList<>();
        StringBuilder storepath = new StringBuilder();
        source.setOnAction(e -> {
            String s = source.getSelectionModel().getSelectedItem();
            start = s;
            Dist.setOnAction(event -> {

                String ds = Dist.getSelectionModel().getSelectedItem();
                printPath.addAll(dijkstra.ShortestPath(g, g.getVertexByName(start), g.getVertexByName(ds)));

                find.setOnAction(action -> {
                    storepath.append("The Distance is " + printPath.get(0) + "\t");

                    for (int index = 1; index < printPath.size() - 1; index++) {
                        storepath.append(printPath.get(index) + "->");
                        Vertex currentVertex = g.getVertexByName(printPath.get(index));
                        Vertex nextVertex = g.getVertexByName(printPath.get(index + 1));

                        if (nextVertex != null || currentVertex != null) {
                            double[] currentCoords = convertLatLonToPixel(currentVertex.getLatitude(), currentVertex.getLongitude());
                            double[] nextCoords = convertLatLonToPixel(nextVertex.getLatitude(), nextVertex.getLongitude());

                            Line pathLineSegment = new Line(currentCoords[0] + 210, currentCoords[1] - 8, nextCoords[0] + 210, nextCoords[1] - 8);
                            pathLineSegment.setStrokeWidth(2);

                            if (index==printPath.size()-2){

                                double angle = Math.toDegrees(Math.atan2(nextCoords[1] - currentCoords[1], nextCoords[0] - currentCoords[0]));

                                Polygon arrowhead = createArrowhead(nextCoords[0] + 215, nextCoords[1] - 10, angle);
                                arrowhead.setFill(Paint.valueOf("96E9C6"));
                                group.getChildren().add(arrowhead);
                            }

                            group.getChildren().add(pathLineSegment);
                        }

                    }
                    TextPath.setText(storepath + ds);

                });
            });
            printPath.clear();
        });


        while ((Line2 = br2.readLine()) != null) {
            String[] data = Line2.split(", ");
            g.addEdge(g.getVertexByName(data[0]), g.getVertexByName(data[1]));
        }

        VBox List = new VBox();
        List.setAlignment(Pos.TOP_LEFT);
        List.setSpacing(2.5);

        List.setPrefWidth(134);
        List.setPrefHeight(200);

        List.getChildren().addAll(src, source, dst, Dist, find);
        Button clearButton = new Button("Clear");
        clearButton.setStyle(
                "-fx-background-color: linear-gradient(to right,#00acc1, #9ce8ec); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-min-width: 80px; " +
                        "-fx-min-height: 30px; " +
                        "-fx-max-width: 80px; " +
                        "-fx-max-height: 30px; " +
                        "-fx-border-color: #45a080; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 10px; "
        );
        List.getChildren().add(clearButton);

        clearButton.setOnAction(event -> {

            group.getChildren().removeIf(node -> node instanceof Line || node instanceof Polygon);
            TextPath.clear();

        });
        TextPath.setStyle(
                "-fx-control-inner-background: #ffffff; " +  // Background color
                        "-fx-font-size: 14px; " +                    // Font size
                        "-fx-font-family: 'Arial'; " +               // Font family
                        "-fx-text-fill: #333333; " +                 // Text color
                        "-fx-border-color: #cccccc; " +              // Border color
                        "-fx-border-width: 1px; " +                   // Border width
                        "-fx-border-radius: 5px; " +                 // Border radius
                        "-fx-padding: 5px;"                          // Padding
        );

        // Set inline styles for ComboBoxes
        source.setStyle(
                "-fx-control-inner-background: #e0f7fa; " +   // Background color
                        "-fx-font-size: 14px; " +                     // Font size
                        "-fx-font-family: 'Arial'; " +                // Font family
                        "-fx-text-fill: #004d40; " +                  // Text color
                        "-fx-border-color: #009688; " +               // Border color
                        "-fx-border-width: 2px; " +                   // Border width
                        "-fx-border-radius: 10px; " +                 // Border radius
                        "-fx-padding: 5px; " +                        // Padding
                        "-fx-background-insets: 0 0 0 25; " +        // Background insets
                        "-fx-background-radius: 10px 0 0 10px; " +   // Background radius
                        "-fx-background-color: linear-gradient(to right, #00acc1, #9ce8ec);" // Gradient background
        );

        Dist.setStyle(
                "-fx-control-inner-background: #e0f7fa; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-family: 'Arial'; " +
                        "-fx-text-fill: #004d40; " +
                        "-fx-border-color: #009688; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 10px; " +
                        "-fx-padding: 5px; " +
                        "-fx-background-insets: 0 25 0 0; " +
                        "-fx-background-radius: 0 10px 10px 0; " +
                        "-fx-background-color: linear-gradient(to right, #00acc1, #9ce8ec);"
        );

        pane.setStyle("-fx-background-color: linear-gradient(to right, rgba(72,152,218,0.66), #a2e6ea);");
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



    private void clearPath(Group group, TextArea textArea) {
        group.getChildren().removeIf(node -> node instanceof Line);
        textArea.clear();
    }
    private Polygon createArrowhead(double x, double y, double angle) {
        double arrowSize = 15;
        Polygon arrowhead = new Polygon();
        arrowhead.getPoints().addAll(x, y, x - arrowSize, y + arrowSize / 2, x - arrowSize-1, y - arrowSize / 2);
        arrowhead.setRotate(angle);
        return arrowhead;
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

    //    private double[] latpixel(double latitude, double longitude) {
//        double mapWidth = width;
//        double mapHeight = height;
//        double mapMinLat = 31.21682524779672;
//        double mapMaxLat = 31.60666996149046;
//        double mapMinLon = 34.11915443676544;
//        double mapMaxLon = 34.5722355298608;
//
//        double mapLongitude = mapMaxLon - mapMinLon;
//        double mapLatitude = mapMinLat - mapMaxLat;
//        longitude = longitude - mapMinLon;
//        latitude = mapMinLat - latitude;
//
//        double x = (mapWidth * (longitude / mapLongitude));
//        double y = (mapHeight * (latitude / mapLatitude));
//
//        return new double[]{x, y};
//    }
    private void showTooltip(Button button, String text) {
        Tooltip tooltip = new Tooltip(text);
        button.setTooltip(tooltip);
        tooltip.show(button, button.localToScreen(button.getBoundsInLocal()).getMinX(), button.localToScreen(button.getBoundsInLocal()).getMinY() - 30);
    }

    private void hideTooltip(Tooltip tooltip) {
        tooltip.hide();
    }


}