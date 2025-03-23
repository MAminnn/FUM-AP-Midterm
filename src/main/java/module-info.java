module com.amin.waterpipe {
    requires javafx.controls;
    requires javafx.fxml;


    exports com.amin.waterpipe.view;
    opens com.amin.waterpipe.view to javafx.fxml;
}