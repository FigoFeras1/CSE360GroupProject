package com.sdpizza.groupproject.controller;

import com.sdpizza.groupproject.database.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import com.sdpizza.groupproject.entity.model.User;

public class UserController {

        @FXML
        private UserRepository uRepo;

        @FXML
        private User activeUser;

        @FXML
        private Label loginText, registerText, statusLabel;

        @FXML
        private Button loginButton, registerButton, homeLoginButton, homeLogoutButton,
                homeRegisterButton, orderCancelButton, orderConfirmButton, homeOrderButton,
                orderStatusButton;

        @FXML
        private TextField idField, firstField, lastField;


        @FXML
        private PasswordField passwordField;

        @FXML
        private ProgressBar statusProgressBar;

        @FXML
        private ToggleGroup sizeToggleGroup, baseToggleGroup;

        @FXML
        protected User login() {
                boolean fieldsFilled = (idField.getCharacters().length() > 0
                        && passwordField.getCharacters().length() > 0);
                Color borderColor = (fieldsFilled ? Color.GREY : Color.RED);

                idField.setBorder(Border.stroke(borderColor));
                passwordField.setBorder(Border.stroke(borderColor));

                /* TODO: Add another if statement that checks the id and password */
                if (!fieldsFilled) {
                        loginText.setText("Please enter your ASUID and Password");
                        loginText.setTextFill(borderColor);
                        loginText.setVisible(true);
                        return null;
                }

                User user = new User();
                user.setPassword(passwordField.getText());
                long varLong=Long.parseLong(idField.getText());
                user.setID(varLong);

                activeUser = null;
                activeUser = uRepo.get(user);


                if(activeUser == null) {
                        loginText.setText("Invalid ASUID and/or Password");
                        loginText.setTextFill(borderColor);
                        loginText.setVisible(true);
                }



                /* View switching code */
                //loadView(loginButton, "orders.fxml");

                return activeUser;
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
                }

                /* View switching code */
                //loadView(registerButton, "login-form.fxml");
        }

    }

