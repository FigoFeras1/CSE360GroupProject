package com.sdpizza.groupproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Controller controller = new Controller();
        FXMLLoader fxmlLoader =
                new FXMLLoader(
                    Main.class.getResource("login-form.fxml")
                );
        fxmlLoader.setController(controller);

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        scene.setOnKeyPressed(controller::keyPressed);

        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}