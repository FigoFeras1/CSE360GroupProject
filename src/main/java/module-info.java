module com.sdpizza.groupproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.h2database;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;


    opens com.sdpizza.groupproject to javafx.fxml;
    exports com.sdpizza.groupproject;
    exports com.sdpizza.groupproject.entity.item
            to com.fasterxml.jackson.databind;
}