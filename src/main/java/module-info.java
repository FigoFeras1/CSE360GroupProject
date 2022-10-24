module com.sdpizza.groupproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;


    opens com.sdpizza.groupproject to javafx.fxml;
    exports com.sdpizza.groupproject;
}