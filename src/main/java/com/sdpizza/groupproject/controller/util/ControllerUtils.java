package com.sdpizza.groupproject.controller.util;

import com.sdpizza.groupproject.Main;
import com.sdpizza.groupproject.controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ControllerUtils {
    public void loadView(String destinationFXML) {
        try {
            /* Which view to display */
            URL location = Main.class.getResource(destinationFXML);
            Parent root = FXMLLoader.load(Objects.requireNonNull(location));

            Controller.stage.setScene(new Scene(root, 750, 500));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
    public static long validateASUID(TextField textField, Label errorLabel) {
        long id = -1;

        try { id = Long.parseLong(textField.getText()); }
        catch (NumberFormatException ex) {
            error(errorLabel, "Enter Valid ASUID (10-digit number)");
        }

        return id;
    }

    public static void error(Label label, String message) {
        label.setText(message);
        label.setTextFill(Color.RED);
        label.setVisible(true);
    }
}
