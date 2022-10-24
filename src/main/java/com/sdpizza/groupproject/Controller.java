package com.sdpizza.groupproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Controller {
    @FXML
    @SuppressWarnings("unused")
    private Label welcomeText, loginText;

    @FXML
    @SuppressWarnings("unused")
    private Button loginButton, homeLoginButton;

    @FXML
    @SuppressWarnings("unused")
    private TextField idField;

    @FXML
    @SuppressWarnings("unused")
    private PasswordField passwordField;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void login() {
        boolean fieldsFilled = (idField.getCharacters().length() > 0
                                && passwordField.getCharacters().length() > 0);
        Color borderColor = (fieldsFilled ? Color.GREY : Color.RED);

        idField.setBorder(Border.stroke(borderColor));
        passwordField.setBorder(Border.stroke(borderColor));

        /* TODO: Add another if statement that checks the id and password */
        if (!fieldsFilled) {
            loginText.setText("Please enter your ASUID and password");
            loginText.setTextFill(borderColor);
            loginText.setVisible(true);
            return;
        }

        /* View switching code */
        loadView("home-view.fxml",loginButton);
    }

    @FXML
    protected void homeLogin() {
        loadView("login-form.fxml",homeLoginButton);
    }

    /* Use this function if you need specific behavior for a keyPressed event */
    @FXML
    protected void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                if (!idField.isFocused()
                    && idField.getCharacters().length() == 0)
                {
                    idField.requestFocus();
                }
                else if (passwordField.getCharacters().length() > 0)
                    login();
                else
                    passwordField.requestFocus();

                break;
            case TAB:
            default: break;
        }
    }

    /* Use this method to transition through views */
    private void loadView(String name, Button button) {
        try {
            /* Which view to display */
            URL location = getClass().getResource(name);

            Parent root = FXMLLoader.load(Objects.requireNonNull(location));
            ((Stage) button
                        .getScene()
                        .getWindow())
                        .setScene(new Scene(root, 750, 500));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    
    /* May want to start using this in the future */
    @SuppressWarnings("unused")
    enum Message {
        SUCCESS,
        FAILURE,
    }
}