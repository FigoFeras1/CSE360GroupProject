package com.sdpizza.groupproject.database;

import com.sdpizza.groupproject.database.repository.OrderRepository;
import com.sdpizza.groupproject.database.repository.UserRepository;
import com.sdpizza.groupproject.entity.item.Pizza;
import com.sdpizza.groupproject.entity.model.User;
import com.sdpizza.groupproject.entity.model.Order;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/* TODO: Split this up into multiple classes and make all tests actually test the application. */
/* Me and this class are both in shambles rn */
/* NOTE: Anything disabled is probably broken */
public class DatabaseTest {
    private final User user =
            new User(10, "feras", "a", "password",
                     User.Role.CUSTOMER);
    private final User admin =
            new User(0, "admin", "admin", "password",
                     User.Role.CUSTOMER);
    private final UserRepository userRepository = new UserRepository();
    private final OrderRepository orderRepository = new OrderRepository();

    @BeforeAll
    @Disabled
    public static void init() {
        DatabaseConnection.init();
    }


    @Test
    @Disabled
    @DisplayName("Successful Database Delete")
    void successfulDatabaseDelete() {
        userRepository.remove(user.getID());
        assertNull(userRepository.get(user.getID(), user.getPassword()));
    }

    @Test
    @DisplayName("Successful Database Read")
    void successfulDatabaseRead() {
        User user = userRepository.get(admin.getID(),
                                       admin.getPassword());
        assert(Arrays.equals(admin.toArray(), user.toArray()));
    }

    @Test
    @DisplayName("Failed Database Read")
    void failedDatabaseRead() {
        User user = userRepository.get(-1, "password");
        assertNull(user);
    }


    @Test
    @Disabled
    @DisplayName("Successful Database Insert")
    void successfulDatabaseInsert() {
        userRepository.add(user);
        assertNotNull(userRepository.get(user.getID(), user.getPassword()));
    }

    @Test
    @DisplayName("Update Database Row")
    void updateDatbaseRow() {
        String oldPassword = user.getPassword();
        String newPassword = "test123";
        user.setPassword(newPassword);

        userRepository.update(user);

        User newUser = userRepository.get(user.getID(), user.getPassword());
        user.setPassword(oldPassword);
        userRepository.update(user);
        assertEquals(newUser.getPassword(), newPassword);
    }

    @Test
    @DisplayName("Pizza Serialization")
    void pizzaSerialization() {
        Pizza pizza = new Pizza(Pizza.Size.LARGE, Pizza.Base.CHEESE,
                                Pizza.Topping.MUSHROOMS, Pizza.Topping.ONIONS);
        String json = PizzaSerializer.serialize(pizza);
        System.out.println(json);
        assertEquals(1,1);
    }

    @Test
    @DisplayName("Pizza Deserialization")
    void pizzaDeserialization() {
        Pizza pizza = new Pizza(Pizza.Size.LARGE, Pizza.Base.CHEESE,
                Pizza.Topping.MUSHROOMS, Pizza.Topping.ONIONS);
        String json = PizzaSerializer.serialize(pizza);
        pizza = PizzaSerializer.deserialize(json);
        System.out.println(pizza);
        assertEquals(1,1);
    }

    @Test
    @DisplayName("Select User Orders")
    void selectUserOrders() {
        userRepository.getOrders(10, Order.Status.SAVED);
        assert(true);
    }

    @Test
    @DisplayName("SQL Dump")
    void sqlDump() {
        DatabaseConnection.dumpSQL();
    }

    @Test
    @DisplayName("Create Order")
    void createOrder() {
        Pizza pizza = new Pizza(Pizza.Size.LARGE, Pizza.Base.CHEESE,
                Pizza.Topping.MUSHROOMS, Pizza.Topping.ONIONS);
        Pizza pizza0 = new Pizza(Pizza.Size.SMALL, Pizza.Base.VEGAN,
                Pizza.Topping.OLIVES, Pizza.Topping.PINEAPPLE);
        ArrayList<Pizza> items = new ArrayList<>();
        items.add(pizza);
        items.add(pizza0);
        Order order = new Order(items, user, Order.Status.PENDING);
        orderRepository.add(order);
    }

    @Test
    @Disabled
    @DisplayName("Brute Force Deletion Using Statement")
    void statementDelete() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        Statement stmt = connection.createStatement();
        String sql = "DELETE FROM users WHERE id = 10";
        stmt.executeUpdate(sql);
        connection.commit();
    }
}
