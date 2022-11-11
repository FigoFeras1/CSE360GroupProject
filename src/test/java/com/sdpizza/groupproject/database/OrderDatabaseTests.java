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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderDatabaseTests {
    private final OrderRepository orderRepository = new OrderRepository();
    private final User user =
            new User(10, "feras", "a", "password",
                    User.Role.CUSTOMER);

    @BeforeAll
    public static void init() {
        DatabaseConnection.init();
    }

    @Test
    public void createOrder() {
        List<Item> items = new ArrayList<>();
        items.add(new Pizza(4, Pizza.Size.MEDIUM, Pizza.Base.CHEESE,
                            Pizza.Topping.EXTRA_CHEESE,
                            Pizza.Topping.PINEAPPLE));
        items.add(new Pizza(1, Pizza.Size.XLARGE, Pizza.Base.CHEESE,
                            Pizza.Topping.EXTRA_CHEESE));

        Order order = new Order(items, user, Order.Status.PENDING);
        orderRepository.add(order);
        Order queriedOrder = orderRepository.get(order);

        Iterator<Item> itemIterator = items.iterator();
        Iterator<Item> queriedIterator = queriedOrder.getItems().iterator();

        assertEquals(order.getID(), queriedOrder.getID());
        assertEquals(order.getStatus(), queriedOrder.getStatus());
        assertEquals(order.getUser().getID(), queriedOrder.getUser().getID());
        while (itemIterator.hasNext() && queriedIterator.hasNext()) {
            assertEquals(itemIterator.next(), queriedIterator.next());
        }
    }

    @Test
    public void switchStatus() {
        List<Item> items = new ArrayList<>();
        items.add(new Pizza(2, Pizza.Size.SMALL, Pizza.Base.CHEESE,
                Pizza.Topping.OLIVES,
                Pizza.Topping.PINEAPPLE));
        items.add(new Pizza(2, Pizza.Size.SMALL, Pizza.Base.CHEESE,
                Pizza.Topping.MUSHROOMS));

        Order order = new Order(items, user, Order.Status.PENDING);
        orderRepository.add(order);

        orderRepository.remove(order);
        order.setStatus(Order.Status.PROCESSED);
        orderRepository.add(order);

        Order queriedOrder = orderRepository.get(order);

        Iterator<Item> itemIterator = items.iterator();
        Iterator<Item> queriedIterator = queriedOrder.getItems().iterator();

        assertEquals(queriedOrder.getStatus(), Order.Status.PROCESSED);
        assertEquals(order.getID(), queriedOrder.getID());
        assertEquals(order.getUser().getID(), queriedOrder.getUser().getID());
        while (itemIterator.hasNext() && queriedIterator.hasNext()) {
            assertEquals(itemIterator.next(), queriedIterator.next());
        }

    }
}
