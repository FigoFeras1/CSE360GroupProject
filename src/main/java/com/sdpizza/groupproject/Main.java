package com.sdpizza.groupproject;

import com.sdpizza.groupproject.controller.Controller;
import com.sdpizza.groupproject.database.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;

/* Life-Cycle of javafx.application.Application:
   1. Constructs an instance of Application class
   2. Calls init()
   3. Calls start(javafx.stage.Stage)
   4. Waits for application to finish, which happens when:
      - Application calls javafx.application.Platform.exit()
      - Last window is closed and the implicitExit attribute on Platform is true
   5. Calls stop() */

public class Main extends Application {
    public static Controller controller;
    public static FXMLLoader fxmlLoader;
    public static Rectangle2D screenBounds;
    @Override
    public void start(Stage stage) throws IOException {
        Controller.stage = stage;
        stage.setMaximized(true); //Opens Webapp in maximized window rather than full screen

        fxmlLoader =
                new FXMLLoader(Main.class.getResource("anon-home.fxml"));
        /* Setting the controller for the application here
           NOTE: You may run into weird behavior if you are using the
           SceneBuilder (i.e., the fields aren't being recognized)
           or you may get an error that the controller is already defined;
           Just go into the fxml file and remove the fx:controller attribute */

        /* fxmlLoader.setController(controller); */

        /* Could help with dynamic size issue? */
        screenBounds = Screen.getPrimary().getVisualBounds();
        /* Adding event handlers to the scene */
        Scene scene = new Scene(fxmlLoader.load(),
                                screenBounds.getWidth(),
                                screenBounds.getHeight());
        controller = fxmlLoader.getController();
        scene.setOnKeyPressed(controller::keyPressed);

        stage.setTitle("SunDevil Pizza"); /* All rights reserved TM */

        stage.setScene(scene);
        stage.setX(screenBounds.getMinX());
        stage.setY(screenBounds.getMinY());
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());

        stage.show();
    }

    @Override
    public void stop() {
        DatabaseConnection.close();
    }

    public static void main(String... args) {
        DatabaseConnection.init();
        Application.launch();
    }
}