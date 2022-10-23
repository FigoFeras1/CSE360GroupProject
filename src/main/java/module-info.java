module com.group25.cse360groupproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.sdpizza.groupproject to javafx.fxml;
    exports com.sdpizza.groupproject;
}