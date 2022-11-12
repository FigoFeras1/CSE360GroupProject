package com.sdpizza.groupproject.controller;

import com.sdpizza.groupproject.database.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import com.sdpizza.groupproject.entity.model.User;

public class UserController {

        private UserRepository userRepository=new UserRepository();

        private static User activeUser=null;

        @FXML
        protected User login(long longval,String pass) {
                User user = new User();
                user.setPassword(pass);
                user.setID(longval);

                activeUser = userRepository.get(user);
                System.out.println(activeUser);
                return activeUser;
        }

        @FXML
        protected boolean register(User user) {
                return userRepository.add(user);
        }

        @FXML
        protected boolean checkID(long id,User user) {
                if(user.getID()==id) return true;
                else return false;
        }

    }

