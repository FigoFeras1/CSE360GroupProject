module com.sdpizza.groupproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.h2database;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;


    opens com.sdpizza.groupproject
            to javafx.fxml;
    exports com.sdpizza.groupproject
            to javafx.graphics;

    opens com.sdpizza.groupproject.controller
            to javafx.fxml;
    exports com.sdpizza.groupproject.controller
            to javafx.graphics;

    exports com.sdpizza.groupproject.database
            to com.fasterxml.jackson.databind;
    exports com.sdpizza.groupproject.entity.item
            to com.fasterxml.jackson.databind;
    exports com.sdpizza.groupproject.entity.model
            to com.fasterxml.jackson.databind;
    exports com.sdpizza.groupproject.database.serializer
            to com.fasterxml.jackson.databind;
}
