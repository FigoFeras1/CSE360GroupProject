package com.sdpizza.groupproject.controller.util;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class ControllerUtils {
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
