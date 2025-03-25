module com.gameoflife {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.gameoflife to javafx.fxml;
    exports com.gameoflife;
}
