package com.sdpizza.groupproject.controller;

import com.sdpizza.groupproject.database.repository.UserRepository;
import javafx.fxml.FXML;
import com.sdpizza.groupproject.entity.model.User;

public class UserController {

        private final UserRepository userRepository = new UserRepository();

        @FXML
        protected User login(long longval, String pass) {
                User user = new User();
                user.setPassword(pass);
                user.setID(longval);

                Controller.activeUser = userRepository.get(user);
                return Controller.activeUser;
        }

        @FXML
        protected boolean register(User user) {
                return userRepository.add(user);
        }

        @FXML
        protected boolean checkID(long id, User user) {
                return (id == user.getID());
        }

    }

