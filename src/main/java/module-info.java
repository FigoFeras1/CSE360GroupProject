module com.sdpizza.groupproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.sdpizza.groupproject to javafx.fxml;
    exports com.sdpizza.groupproject;
}