module com.amin.waterpipe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;


    exports com.amin.waterpipe.view;
    opens com.amin.waterpipe.view to javafx.fxml;
    exports com.amin.waterpipe.view.controllers;
    opens com.amin.waterpipe.view.controllers to javafx.fxml;
}