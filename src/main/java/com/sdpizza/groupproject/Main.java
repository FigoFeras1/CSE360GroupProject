package com.sdpizza.groupproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static Controller controller;
    public static FXMLLoader fxmlLoader;
    @Override
    public void start(Stage stage) throws IOException {
        fxmlLoader =
                new FXMLLoader(
                    Main.class.getResource("home-view.fxml")
                );
        /* Setting the controller for the application here
           NOTE: You may run into weird behavior if you are using the
           SceneBuilder (i.e., the fields aren't being recognized)
           or you may get an error that the controller is already defined;
           Just go into the fxml file and remove the fx:controller attribute */
//        fxmlLoader.setController(controller);

        /* Adding event handlers to the scene */
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        controller = fxmlLoader.getController();
        scene.setOnKeyPressed(controller::keyPressed);

        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}