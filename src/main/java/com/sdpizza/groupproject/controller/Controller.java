package com.sdpizza.groupproject.controller;

import com.sdpizza.groupproject.Main;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
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
import com.sdpizza.groupproject.entity.model.User;

import static javafx.scene.paint.Color.RED;


public class Controller {

    private static User activeUser=null;
    private static User tempUser=new User();

    private UserController userController=new UserController();

    @FXML
    private Label loginText, pizzasInOrder, registerText, statusLabel,
                  quantitySpinnerLabel;

    @FXML
    private Button loginButton, adminLoginButton, registerButton, homeLoginButton, homeLogoutButton,
                   homeRegisterButton, orderCancelButton, orderConfirmButton, homeOrderButton,
                   orderStatusButton;

    @FXML
    private TextField idField, firstField, lastField, confirmField;


    @FXML
    private PasswordField passwordField;

    @FXML
    private ProgressBar statusProgressBar;

    @FXML
    private ToggleGroup sizeToggleGroup, baseToggleGroup;

    @FXML
    private Spinner<Integer> quantitySpinner;

    @FXML
    private static String pizzaOptions = "Test";


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
        if (pizzasInOrder != null) pizzasInOrder.setText(pizzaOptions);
        if (quantitySpinner != null) initQuantitySpinner();
    }

    @FXML
    public void exitApplication(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    protected void login() {
        boolean fieldsFilled = (idField.getCharacters().length() > 0
                                && passwordField.getCharacters().length() > 0);
        Color borderColor = (fieldsFilled ? Color.GREY : RED);

        idField.setBorder(Border.stroke(borderColor));
        passwordField.setBorder(Border.stroke(borderColor));

        // TODO: Add another if statement that checks the id and password
        if (!fieldsFilled) {
            loginText.setText("Please enter your ASUID and password");
            loginText.setTextFill(borderColor);
            loginText.setVisible(true);
            return;
        }

        try {
            int value = Integer.parseInt(idField.getText());
            System.out.println(value);
        } catch (NumberFormatException e) {
            loginText.setText("Invalid ASUID");
            loginText.setTextFill(RED);
            loginText.setVisible(true);
            return;
        }
        long varLong=Long.parseLong(idField.getText());
        String pass=passwordField.getText();
        activeUser=userController.login(varLong,pass);

        /* View switching code */
        if(activeUser != null && activeUser.getRole()==User.Role.CUSTOMER) loadView(loginButton, "orders.fxml");
        else {
            loginText.setText("Failed to login.");
            loginText.setTextFill(RED);
            loginText.setVisible(true);
        }
    }

    @FXML
    protected void register() {
        boolean fieldsFilled = (idField.getCharacters().length() > 0
                                && passwordField.getCharacters().length() > 0);
        Color borderColor = (fieldsFilled ? Color.GREY : RED);

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

        try {
            int value = Integer.parseInt(idField.getText());
            System.out.println(value);
        } catch (NumberFormatException e) {
            registerText.setText("Enter valid ID");
            registerText.setTextFill(RED);
            registerText.setVisible(true);
            return;
        }

        long longval=Long.parseLong(idField.getText());
        String first=firstField.getText();
        String last=lastField.getText();
        String pass=passwordField.getText();
        tempUser.setFirstName(first);
        tempUser.setLastName(last);
        tempUser.setPassword(pass);
        tempUser.setID(longval);
        tempUser.setRole(User.Role.CUSTOMER);
        boolean regCheck=userController.register(tempUser);
        /* View switching code */
        if(regCheck) loadView(registerButton, "login-form.fxml");
        else {
            registerText.setText("Failed to register.");
            registerText.setTextFill(RED);
            registerText.setVisible(true);
        }
    }

    @FXML
    protected void adminLogin() {
        boolean fieldsFilled = (idField.getCharacters().length() > 0
                && passwordField.getCharacters().length() > 0);
        Color borderColor = (fieldsFilled ? Color.GREY : RED);

        idField.setBorder(Border.stroke(borderColor));
        passwordField.setBorder(Border.stroke(borderColor));

        // TODO: Add another if statement that checks the id and password
        if (!fieldsFilled) {
            loginText.setText("Please enter your ASUID and password");
            loginText.setTextFill(borderColor);
            loginText.setVisible(true);
            return;
        }

        try {
            int value = Integer.parseInt(idField.getText());
            System.out.println(value);
        } catch (NumberFormatException e) {
            loginText.setText("Invalid ID");
            loginText.setTextFill(RED);
            loginText.setVisible(true);
            return;
        }
        long varLong=Long.parseLong(idField.getText());
        String pass=passwordField.getText();
        activeUser=userController.login(varLong,pass);

        /* View switching code */
        if(activeUser != null && activeUser.getRole()!=User.Role.CUSTOMER) loadView(adminLoginButton, "admin-home.fxml");
        else {
            loginText.setText("Failed to login as admin.");
            loginText.setTextFill(RED);
            loginText.setVisible(true);
        }
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
        loadView( orderCancelButton, "anon-home.fxml");
    }

    @FXML
    protected void orderConfirm() {
        getPizzaInfo();
        loadView(orderConfirmButton, "order-confirmation.fxml");
    }

    @FXML
    protected void orderStatus() {
        boolean fieldsFilled = (confirmField.getCharacters().length() > 0);
        Color borderColor = (fieldsFilled ? Color.GREY : RED);

        confirmField.setBorder(Border.stroke(borderColor));

        // TODO: Add another if statement that checks the id and password
        if(!fieldsFilled) {
            loginText.setText("Please enter your ASUID");
            loginText.setTextFill(borderColor);
            loginText.setVisible(true);
            return;
        }

        try {
            int value = Integer.parseInt(confirmField.getText());
            System.out.println(value);
        } catch (NumberFormatException e) {
            loginText.setText("Invalid ASUID");
            loginText.setTextFill(RED);
            loginText.setVisible(true);
            return;
        }
        long varLong=Long.parseLong(confirmField.getText());
        boolean idCheck=userController.checkID(varLong,activeUser);

        /* View switching code */
        if(idCheck) loadView(orderStatusButton, "order-status.fxml");
        else {
            loginText.setText("Failed to login.");
            loginText.setTextFill(RED);
            loginText.setVisible(true);
        }
    }

    @FXML
    protected void homeLogout() { loadView( homeLogoutButton, "anon-home.fxml");}

    /* Insert potential "order button from home" event here */

    /* Use this function if you need specific behavior for a keyPressed event */
    @FXML
    public void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            /* The code here is generalizing the behavior of enter so that
               it either moves from an empty box to another or fires the button
               action and possibly moves onto another JavaFX Node. I will be
               fixing this up later, so don't worry about the functionality not
               being complete. */
            /* TODO: Figure out what the hell this is does again and fix it */
            case ESCAPE:
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
                            } else if (ButtonBase.class.isAssignableFrom(
                                        node.getClass())) {
                                if (node instanceof ToggleButton) {
                                    ToggleButton currentToggle =
                                            (ToggleButton) node;
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
            case TAB:
                System.out.println("TAB");
                break;
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

    /* TODO: This method will collect info and present it on order confirmation page */
    public void getPizzaInfo() {
        pizzaOptions = "No pizzas :("; /* Flush out pizza options */
        /*Toggle size = sizeToggleGroup.getSelectedToggle();
        pizzaOptions = size.toString();*/
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

                    if (isCancelled()) break;

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

    @FXML
    protected void orderTogglesEmpty() {
        orderConfirmButton.setDisable((sizeToggleGroup.getSelectedToggle() == null
                                      || baseToggleGroup.getSelectedToggle() == null));
    }

    protected void initQuantitySpinner() {
        final int quantityMin = 1;
        final int quantityMax = 100;
        quantitySpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(quantityMin,
                                                                   quantityMax,
                                                                   quantityMin)
        );

        quantitySpinner.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (!oldValue.equals(newValue)) {
                        if (newValue.compareTo(quantityMin) < 0
                            || newValue.compareTo(quantityMax) > 0)
                        {
                            quantitySpinner.setBorder(Border.stroke(Color.RED));
                            quantitySpinnerLabel.setText("min: 1,\nmax: 100");
                            quantitySpinnerLabel.setTextFill(RED);
                            quantitySpinner.getValueFactory().setValue(oldValue);
                        } else {
                            quantitySpinner.commitValue();
                        }
                    }
        });
    }



    /* May want to start using this in the future */
    @SuppressWarnings("unused")
    enum Message {
        SUCCESS,
        FAILURE,
    }
}