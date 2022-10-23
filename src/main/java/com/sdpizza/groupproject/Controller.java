package com.sdpizza.groupproject;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;

public class Controller {
    @FXML
    private Label welcomeText, loginText;

    @FXML
    private Button loginButton;

    @FXML
    private CheckBox hiCheckBox;

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
        boolean fieldsFilled = (usernameField.getCharacters().length() > 0
                                && passwordField.getCharacters().length() > 0);
        Color borderColor = (fieldsFilled) ? Color.BLACK : Color.RED;
        if (fieldsFilled) {
            loginText.setText("Login Successful!");
            loginText.setTextFill(Color.color(0, 1, 0));
        } else {
            loginText.setText("Please enter username and password");
            loginText.setTextFill(Color.color(1, 0, 0));
        }
        usernameField.setBorder(Border.stroke(borderColor));
        passwordField.setBorder(Border.stroke(borderColor));

        loginText.setVisible(true);
    }

    @FXML
    protected void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                if (!usernameField.isFocused()
                    && usernameField.getCharacters().length() == 0)
                {
                    usernameField.requestFocus();
                } else if (passwordField.getCharacters().length() > 0) {
                    login();
                } else {
                    passwordField.requestFocus();
                }

                break;
            case TAB:
            default: break;
        }
    }

}