package com.sdpizza.groupproject.database;

import com.sdpizza.groupproject.database.repository.OrderRepository;
import com.sdpizza.groupproject.entity.item.Item;
import com.sdpizza.groupproject.entity.item.Pizza;
import com.sdpizza.groupproject.entity.model.Order;
import com.sdpizza.groupproject.entity.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderDatabaseTests {
    private final OrderRepository orderRepository = new OrderRepository();
    private final User user =
            new User(1000000000L, "feras", "a", "password",
                    User.Role.CUSTOMER);

    @BeforeAll
    protected static void init() {
        DatabaseConnection.init();
    }

    @Test
    protected void createOrder() {
        List<Item> items = new ArrayList<>();
        items.add(new Pizza(4, Pizza.Size.MEDIUM, Pizza.Base.CHEESE,
                            Pizza.Topping.EXTRA_CHEESE,
                            Pizza.Topping.PINEAPPLE));
        items.add(new Pizza(1, Pizza.Size.XLARGE, Pizza.Base.CHEESE,
                            Pizza.Topping.EXTRA_CHEESE));

        Order order = new Order(items, user, 45.33f, Order.Status.ACCEPTED);
        orderRepository.add(order);
        Order queriedOrder = orderRepository.get(order);
        System.out.println(queriedOrder.getItems());
        assertNotNull(queriedOrder);

        Iterator<Item> itemIterator = items.iterator();
        Iterator<Item> queriedIterator = queriedOrder.getItems().iterator();

        assertEquals(order.getID(), queriedOrder.getID());
        assertEquals(order.getStatus(), queriedOrder.getStatus());
        assertEquals(order.getUser().getID(), queriedOrder.getUser().getID());
        assertEquals(order.getCost(), queriedOrder.getCost());

        while (itemIterator.hasNext() && queriedIterator.hasNext()) {
            assertEquals(itemIterator.next().toString(), queriedIterator.next().toString());
        }
    }

    @Test
    protected void switchStatus() {
        List<Item> items = new ArrayList<>();
        items.add(new Pizza(2, Pizza.Size.SMALL, Pizza.Base.CHEESE,
                Pizza.Topping.OLIVES,
                Pizza.Topping.PINEAPPLE));
        items.add(new Pizza(2, Pizza.Size.SMALL, Pizza.Base.CHEESE,
                Pizza.Topping.MUSHROOMS));

        Order order = new Order(items, user, 20.57f, Order.Status.ACCEPTED);
        orderRepository.add(order);

        /* Delete order from database */
        orderRepository.remove(order);

        /* Change order status and re-insert into database */
        order.setStatus(Order.Status.READY_TO_COOK);
        orderRepository.add(order);

        Order queriedOrder = orderRepository.get(order);
        assertNotNull(queriedOrder);

        Iterator<Item> itemIterator = items.iterator();
        Iterator<Item> queriedIterator = queriedOrder.getItems().iterator();

        assertEquals(queriedOrder.getStatus(), Order.Status.READY_TO_COOK);
        assertEquals(order.getID(), queriedOrder.getID());
        assertEquals(order.getUser().getID(), queriedOrder.getUser().getID());
        assertEquals(order.getCost(), queriedOrder.getCost());

        while (itemIterator.hasNext() && queriedIterator.hasNext()) {
            assertEquals(itemIterator.next().toString(), queriedIterator.next().toString());
        }
    }

    @Test
    protected void populateDatabase() {
        Order.Status[] statusOptions = Order.Status.values();
        Pizza.Size[] sizeOptions = Pizza.Size.values();
        Pizza.Base[] baseOptions = Pizza.Base.values();
        Pizza.Topping[] toppingOptions = Pizza.Topping.values();

        Random random = new Random();
        List<Order> orders = new ArrayList<>();

        for (Order.Status status : statusOptions) {
            for (int i = 0; i < (random.nextInt(10) + 5); ++i) {

            }
        }


    }
}
