package com.sdpizza.groupproject;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {
    @FXML
    @SuppressWarnings("unused")
    private Label loginText, registerText, statusLabel;

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

    /* May come in useful in the future */
    public Controller() {
        super();
    }

    /* When load() is called on a FXMLLoader, the following happens:
       1. The FXML file gets loaded
       2. An instance of Controller gets created by calling the default
          Constructor (specified by the fx:controller attribute)
       3. @FXML-annotated fields values are set with elements matching fx:id
          attribute
       4. Registers event handlers mapping to methods in the controller
       5. Call initialize() on Controller, if one is present */
    public void initialize() {
        /* Here, if the statusProgressBar value is set (meaning that the order
           status page has been loaded), then I can call the status() function
           to start the task and animate the progress bar. This function is very
           useful for similar use-cases, where you want a function to run or a
           specific element to have a certain property as soon as the FXML is
           loaded */
        if (statusProgressBar != null) status();
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
            /* The code here is generalizing the behavior of enter so that
               it either moves from an empty box to another or fires the button
               action and possibly moves onto another JavaFX Node. I will be
               fixing this up later, so don't worry about the functionality not
               being complete. */
            case ENTER:
                Object eventSource = event.getSource();

                if (eventSource instanceof Pane) {
                    Pane source = (Pane) eventSource;
                    /* This gets ALL children, need to figure out how to get
                       current child + the remaining ones */
                    List<Node> nodes = source.getChildren()
                                             .stream()
                                             .filter(c -> (c instanceof Button
                                                           || (c instanceof TextField)))
                                             .collect(Collectors.toList());
                    Optional<Node> focusedNode = nodes
                                        .stream()
                                        .filter(Node::isFocused)
                                        .findFirst();

                    System.out.println(nodes);
                    int focusedNodeIndex =
                            nodes.indexOf(focusedNode.orElse(nodes.get(0)));
                    ListIterator<Node> listIterator =
                            nodes.listIterator(focusedNodeIndex);
                    while (listIterator.hasNext()) {
                        Node node = listIterator.next();
                        TextField currentField;

                        if (node instanceof TextField) {
                            currentField = (TextField) node;
                            if (!currentField.isFocused()
                                && currentField.getCharacters().length() == 0)
                            {
                                currentField.requestFocus();
                                break;
                            }

                            node = listIterator.next();
                            if (node instanceof TextField) {
                                node.requestFocus();
                            } else if (ButtonBase.class.isAssignableFrom(node.getClass())) {
                                if (node instanceof ToggleButton) {
                                    ToggleButton currentToggle = (ToggleButton) node;
                                    Node nextNode =
                                            nodes.get(nodes.indexOf(currentToggle) + 1);
                                    if (nextNode instanceof ToggleButton) {
                                        ToggleGroup nextToggleGroup =
                                                ((ToggleButton) nextNode).getToggleGroup();
                                        nextToggleGroup
                                                .selectToggle(nextToggleGroup.getToggles().get(0));
                                    }
                                }
                                assert node instanceof Button;
                                ((Button) node).fire();
                            }
                        }
                    }
                }
            case TAB: break;
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
        Task<Void> task = new Task<>() {
            @Override
            public Void call() throws Exception {
                final int max = 3;
                for (int i = 1; i <= max; ++i) {
                    int finalI = i;
                    Platform.runLater(
                            () -> statusLabel.setText(String.valueOf(finalI))
                    );
                    if (isCancelled()) {
                        break;
                    }
                    updateProgress(i, max);

                    /* This controls how long it takes between increments (ms) */
                    Thread.sleep(1500);
                }
                return null;
            }
        };
        statusProgressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    /* May want to start using this in the future */
    @SuppressWarnings("unused")
    enum Message {
        SUCCESS,
        FAILURE,
    }
}