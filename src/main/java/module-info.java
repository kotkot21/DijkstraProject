module com.example.dijkstraproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dijkstraproject to javafx.fxml;
    exports com.example.dijkstraproject;
}