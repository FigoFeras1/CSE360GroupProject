package com.sdpizza.groupproject;

import javafx.application.Platform;
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
import java.util.concurrent.atomic.AtomicReference;

public class Controller {
    @FXML
    @SuppressWarnings("unused")
    private Label loginText, registerText, statusLabel;
    // comment

    @FXML
    @SuppressWarnings("unused")
    private Button loginButton, registerButton, homeLoginButton,
                   homeRegisterButton, orderCancelButton, orderConfirmButton,
                   orderStatusButton;

    @FXML
    @SuppressWarnings("unused")
    private TextField idField, firstField, lastField;


    @FXML
    @SuppressWarnings("unused")
    private PasswordField passwordField;

    @FXML
    private ProgressBar statusProgressBar;

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
        loadView(loginButton, "orders.fxml");
    }

    @FXML
    protected void register() {
        boolean fieldsFilled = (idField.getCharacters().length() > 0
                && passwordField.getCharacters().length() > 0);
        Color borderColor = (fieldsFilled ? Color.GREY : Color.RED);

        firstField.setBorder(Border.stroke(borderColor));
        lastField.setBorder(Border.stroke(borderColor));
        idField.setBorder(Border.stroke(borderColor));
        passwordField.setBorder(Border.stroke(borderColor));

        /* TODO: Add another if statement that checks the id and password */
        if (!fieldsFilled) {
            registerText.setText("Enter Full Name, ASUID, and password");
            registerText.setTextFill(borderColor);
            registerText.setVisible(true);
            return;
        }

        /* View switching code */
        loadView(registerButton, "login-form.fxml");
    }

    @FXML
    protected void homeLogin() {
        loadView(homeLoginButton, "login-form.fxml");
    }

    @FXML
    protected void homeRegister() {
        loadView(homeRegisterButton, "register-form.fxml");
    }

    @FXML
    protected void orderCancel() {
        loadView( orderCancelButton, "home-view.fxml");
    }

    @FXML
    protected void orderConfirm() {
        loadView( orderConfirmButton, "order-confirmation.fxml");
    }

    @FXML
    protected void orderStatus() {
        loadView( orderStatusButton, "order-status.fxml");
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
    private void loadView(Button button, String name) {
        try {
            /* Which view to display */
            URL location = Main.class.getResource(name);
            Parent root = FXMLLoader.load(Objects.requireNonNull(location));

            Scene scene = new Scene(root, 750, 500);
            ((Stage) button.getScene().getWindow()).setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    @FXML
    protected void status() {
        Thread thread = new Thread(new NotificationDaemon());
        thread.setDaemon(true);
        thread.start();
    }

    public class NotificationDaemon implements Runnable {

        @Override
        public synchronized void run() {
            AtomicReference<Double> progress =
                    new AtomicReference<>(statusProgressBar.getProgress());

            while (progress.get() <= 1) {
                Platform.runLater(
                        () -> statusProgressBar
                                .setProgress(
                                        progress.updateAndGet(v -> v + 0.33)
                                )
                );

                synchronized (this) {
                    try {
                        //noinspection BusyWait
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.err.println(e.getMessage());

                        return;
                    }
                }
            }
        }
    }

    /* May want to start using this in the future */
    @SuppressWarnings("unused")
    enum Message {
        SUCCESS,
        FAILURE,
    }
}