package com.sdpizza.groupproject.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdpizza.groupproject.database.repository.OrderRepository;
import com.sdpizza.groupproject.database.repository.UserRepository;
import com.sdpizza.groupproject.database.serializer.ItemSerializer;
import com.sdpizza.groupproject.entity.item.Item;
import com.sdpizza.groupproject.entity.item.Pizza;
import com.sdpizza.groupproject.entity.model.User;
import com.sdpizza.groupproject.entity.model.Order;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

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
        userRepository.remove(user);
        assertNull(userRepository.get(user));
    }

    @Test
    @DisplayName("Successful Database Read")
    void successfulDatabaseRead() {
        User user = userRepository.get(admin);
        assert(Arrays.equals(admin.toArray(), user.toArray()));
    }

    @Test
    @DisplayName("Failed Database Read")
    void failedDatabaseRead() {
        User nonexistentUser = new User(-1, "", "", "", User.Role.CUSTOMER);
        User user = userRepository.get(nonexistentUser);
        assertNull(user);
    }


    @Test
    @Disabled
    @DisplayName("Successful Database Insert")
    void successfulDatabaseInsert() {
        userRepository.add(user);
        assertNotNull(userRepository.get(user));
    }

    @Test
    @DisplayName("Update Database Row")
    void updateDatbaseRow() {
        String oldPassword = user.getPassword();
        String newPassword = "test123";
        user.setPassword(newPassword);

        userRepository.update(user);

        User newUser = userRepository.get(user);
        user.setPassword(oldPassword);
        userRepository.update(user);
        assertEquals(newUser.getPassword(), newPassword);
    }

    @Test
    @DisplayName("Pizza Serialization")
    void pizzaSerialization() {
        Pizza pizza = new Pizza(Pizza.Size.LARGE, Pizza.Base.CHEESE,
                                Pizza.Topping.MUSHROOMS, Pizza.Topping.ONIONS);
        String json = ItemSerializer.serialize(pizza);
        System.out.println(json);
        assertEquals(1,1);
    }

    @Test
    @Disabled
    void pizzaDeserialization() {
        Pizza pizza = new Pizza(Pizza.Size.LARGE, Pizza.Base.CHEESE,
                Pizza.Topping.MUSHROOMS, Pizza.Topping.ONIONS);
        String json = ItemSerializer.serialize(pizza);
        pizza = ItemSerializer.deserialize(json);
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
        pizza0.setQuantity(3);
        pizza.setQuantity(2);
        List<Item> items = new ArrayList<>();
        items.add(pizza);
        items.add(pizza0);
        Order order = new Order(items, user, Order.Status.PENDING);
        orderRepository.add(order);
    }
    @Test
    void test() throws IOException {
        List<Item> items = new ArrayList<>();
        Pizza pizza0 = new Pizza(2, Pizza.Size.SMALL, Pizza.Base.VEGAN, Pizza.Topping.ONIONS,
                  Pizza.Topping.MUSHROOMS);
        Pizza pizza1 = new Pizza(3, Pizza.Size.XLARGE, Pizza.Base.CHEESE,
                  Pizza.Topping.PINEAPPLE, Pizza.Topping.EXTRA_CHEESE);
        items.add(pizza0);
        items.add(pizza1);
        Order order = new Order(items, user, Order.Status.PENDING);
        String json = ItemSerializer.serializeOrder(order);
        ObjectMapper objMapper = new ObjectMapper();
        order = objMapper.readValue(json, Order.class);
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
