package com.sdpizza.groupproject;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label loginText;

    @FXML
    private CheckBox hiCheckBox;

    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onHelloButtonCheck() {
        if (hiCheckBox.isSelected()) {
            welcomeText.setText("Welcome to JavaFX Application!");
        } else {
            welcomeText.setText("");
        }
    }

    @FXML
    protected void login() {
        CharSequence username = usernameField.getCharacters();
        CharSequence password = passwordField.getCharacters();

        if (username.length() > 0 && password.length() > 0) {
            loginText.setText("Login Successful!");
            loginText.setTextFill(Color.color(0, 1, 0));
        } else {
            loginText.setText("Please enter username and password");
            loginText.setTextFill(Color.color(1, 0, 0));
        }

        loginText.setVisible(true);
    }
}