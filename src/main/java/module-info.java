module com.sdpizza.groupproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.h2database;


    opens com.sdpizza.groupproject to javafx.fxml;
    exports com.sdpizza.groupproject;
}