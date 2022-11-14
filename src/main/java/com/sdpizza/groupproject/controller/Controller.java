package com.sdpizza.groupproject.controller;

import com.sdpizza.groupproject.Main;
import com.sdpizza.groupproject.controller.util.ControllerUtils;
import com.sdpizza.groupproject.entity.item.Pizza;
import com.sdpizza.groupproject.entity.model.Order;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import com.sdpizza.groupproject.entity.model.User;

import static javafx.scene.paint.Color.RED;


public class Controller {

    protected static User activeUser = null;
    protected static Order currentOrder = new Order();

    private final UserController userController = new UserController();
    private final OrderController orderController = new OrderController();

    @FXML
    private Label loginText, pizzasInOrder, registerText, statusLabel,
                  quantitySpinnerLabel, costLabel, orderNumberLabel;

    @SuppressWarnings("unused")
    @FXML
    private Button loginButton, adminLoginButton, registerButton, homeLoginButton,
                   homeLogoutButton, homeRegisterButton, orderCancelButton,
                   logoutButton, orderHistoryButton, orderButton, sendToKitchenButton,
                   addToCartButton, orderConfirmButton, homeOrderButton, orderStatusButton,
                   csrButton, chefButton, finishButton;

    @FXML
    private TextField idField, firstField, lastField, confirmField;


    @FXML
    private PasswordField passwordField;

    @FXML
    private ProgressBar statusProgressBar;

    @FXML
    private RadioButton small, medium, large, cheese, pepperoni, vegan;

    @FXML
    private ToggleGroup sizeToggleGroup, baseToggleGroup;

    @FXML
    private Spinner<Integer> quantitySpinner;

    @FXML
    private CheckBox extraCheese, mushrooms, olives, onions;
    private static final ArrayList<CheckBox> toppingChoices = new ArrayList<>();

    @FXML
    private static String pizzaOptions = "";

    @FXML
    private ListView<Order> pastOrderListView, readyToCookList, cookingList, acceptedListView;


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
        if (sizeToggleGroup != null && baseToggleGroup != null) initToggleGroups();
        if (extraCheese != null) {
            toppingChoices.add(extraCheese);
            toppingChoices.add(mushrooms);
            toppingChoices.add(olives);
            toppingChoices.add(onions);
        }
        if (pizzaOptions != null ) pizzaOptions = "";
        if (pastOrderListView != null) initPastOrders();
        if (cookingList != null) initChefLists();
        if (acceptedListView != null) initAccepted();
    }

    @SuppressWarnings("unused")
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

        if (!fieldsFilled) {
            ControllerUtils.error(loginText, "Please enter your ASUID and password");
            return;
        }

        long id = ControllerUtils.validateASUID(idField, loginText);
        String pass = passwordField.getText();
        activeUser = userController.login(id, pass);

        /* View switching code */
        if (activeUser == null) {
            ControllerUtils.error(loginText, "Login failed.");
            return;
        }

        String destination = (activeUser.getRole() == User.Role.CUSTOMER
                              ? "home-known.fxml"
                              : "admin-home.fxml");


        loadView(loginButton, destination);
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

        if (!fieldsFilled) {
            ControllerUtils.error(registerText, "Enter Full name, ASUID, and password");
            return;
        }

        long id = ControllerUtils.validateASUID(idField, registerText);
        String first = firstField.getText();
        String last = lastField.getText();
        String pass = passwordField.getText();
        User tempUser = new User(id, first, last, pass, User.Role.CUSTOMER);

        /* View switching code */
        if(userController.register(tempUser)) {
            loadView(registerButton, "login-form.fxml");
        }
        else {
            ControllerUtils.error(registerText, "Failed to register.");
        }
    }


    @FXML
    protected void addToCart() {
        currentOrder.add(getPizzaInfo());
        orderConfirmButton.setDisable(false);
        sizeToggleGroup.selectToggle(null);
        baseToggleGroup.selectToggle(null);
        toppingChoices.forEach(c -> c.setSelected(false));
        initQuantitySpinner();
        addToCartButton.setDisable(true);
    }

    @FXML
    protected void orderStatus() {
        boolean fieldsFilled = (confirmField.getCharacters().length() > 0);
        Color borderColor = (fieldsFilled ? Color.GREY : RED);

        confirmField.setBorder(Border.stroke(borderColor));

        // TODO: Add another if statement that checks the id and password
        if(!fieldsFilled) {
            ControllerUtils.error(loginText, "Please enter your ASUID");
            return;
        }

        long id = ControllerUtils.validateASUID(confirmField, loginText);
        if (userController.checkID(id, activeUser)) {
            currentOrder.setStatus(Order.Status.ACCEPTED);
            currentOrder.setUser(activeUser);
            orderController.processOrder(currentOrder);
            System.out.println(orderController.getOrder(currentOrder));
            loadView(orderStatusButton, "order-status.fxml");
        }
    }

    @FXML
    protected void homeLogout() { loadView( homeLogoutButton, "anon-home.fxml");}

    /* Insert potential "order button from home" event here */

    /* Use this function if you need specific behavior for a keyPressed event */
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
    public Pizza getPizzaInfo() {
        RadioButton size = (RadioButton) sizeToggleGroup.getSelectedToggle();
        RadioButton base = (RadioButton) baseToggleGroup.getSelectedToggle();

        Pizza pizza = new Pizza();
        pizza.setQuantity(quantitySpinner.getValue());
        pizza.setSize(Pizza.Size.valueOf(size.getText().toUpperCase()));
        pizza.setBase(Pizza.Base.valueOf(base.getText().toUpperCase()));
        pizza.setToppings(toppingChoices
                            .stream()
                            .filter(CheckBox::isSelected)
                            .map(s -> s.getText()
                                       .toUpperCase()
                                       .replace(" ", "_"))
                            .map(Pizza.Topping::valueOf)
                            .collect(Collectors.toList()));
        pizzaOptions += pizza + "\n";

        return pizza;
    }

    @FXML
    protected void status() {
        orderNumberLabel.setText("Order #" + currentOrder.getID());
        Task<Void> task = new Task<>() {
            @Override
            public Void call() throws Exception {
                List<Order.Status> statuses = List.of(Order.Status.values());
                final int max = statuses.size() - 1;
                Order.Status currentStatus = Order.Status.ACCEPTED;

                while (!isCancelled()) {
                    Platform.runLater(
                            () -> statusLabel.setText(String.valueOf(currentStatus))
                    );

                    updateProgress(statuses.indexOf(currentStatus), max);
                    /* This controls how long it takes between increments (ms) */
                    Thread.sleep(5000);
                }

                return null;
            }
        };

        statusProgressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    @FXML
    protected void sendToKitchen() {
        Order order = acceptedListView.getSelectionModel().getSelectedItem();
        orderController.nextStage(order);
        acceptedListView.getItems().remove(order);
        acceptedListView.refresh();
    }

    @FXML
    protected void startCooking() {
        Order order = readyToCookList.getSelectionModel().getSelectedItem();
        readyToCookList.getItems().remove(order);
        cookingList.getItems().add(order);
        orderController.nextStage(order);

        readyToCookList.refresh();
        cookingList.refresh();
    }

    @FXML
    protected void finishOrder() {
        Order order = cookingList.getSelectionModel().getSelectedItem();
        orderController.nextStage(order);
        cookingList.getItems().remove(order);

        cookingList.refresh();
    }

    @FXML
    protected void kitchenView() {
        loadView(chefButton, "chef.fxml");
    }

    @FXML
    protected void orderTogglesEmpty() {
        addToCartButton.setDisable((sizeToggleGroup.getSelectedToggle() == null
                                      || baseToggleGroup.getSelectedToggle() == null));

    }

    protected void initToggleGroups() {
        small.setToggleGroup(sizeToggleGroup);
        medium.setToggleGroup(sizeToggleGroup);
        large.setToggleGroup(sizeToggleGroup);
        cheese.setToggleGroup(baseToggleGroup);
        pepperoni.setToggleGroup(baseToggleGroup);
        vegan.setToggleGroup(baseToggleGroup);
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

    protected void initPastOrders() {
        ObservableList<Order> orders =
                FXCollections.observableList(orderController.getOrders(Order.Status.ACCEPTED));
        pastOrderListView.setItems(orders);
        float totalCost = orders.stream()
                                .map(Order::getCost)
                                .reduce(0f,Float::sum);
        costLabel.setText(costLabel.getText()
                                   .replace("?", String.valueOf(totalCost)));
    }

    protected void initChefLists() {
        ObservableList<Order> readyToCook =
                FXCollections.observableList(orderController.getOrders(Order.Status.READY_TO_COOK));
        ObservableList<Order> cooking =
                FXCollections.observableList(orderController.getOrders(Order.Status.COOKING));

        readyToCookList.setItems(readyToCook);
        cookingList.setItems(cooking);
    }

    protected void initAccepted() {
        ObservableList<Order> accepted =
                FXCollections.observableList(orderController.getOrders(Order.Status.ACCEPTED));
        acceptedListView.setItems(accepted);
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
        loadView(orderConfirmButton, "order-confirmation.fxml");
    }

    @FXML
    protected void logout() {activeUser = null; loadView(logoutButton, "anon-home.fxml"); }

    @FXML
    protected void placeOrder() {loadView(orderButton, "orders.fxml");}

    @FXML
    protected void orderHistory() {loadView(orderHistoryButton, "order-history.fxml");}

    @FXML
    protected void csrView() {
        loadView(csrButton, "customer-service.fxml");
    }

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
}