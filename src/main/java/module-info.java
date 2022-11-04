module com.example.paintoblig {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.paintoblig to javafx.fxml;
    exports com.example.paintoblig;
}