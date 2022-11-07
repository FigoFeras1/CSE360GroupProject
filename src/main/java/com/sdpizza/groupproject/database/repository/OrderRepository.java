package com.sdpizza.groupproject.database.repository;

import com.sdpizza.groupproject.database.DatabaseConnection;
import com.sdpizza.groupproject.database.PizzaSerializer;
import com.sdpizza.groupproject.database.QueryResult;
import com.sdpizza.groupproject.database.repository.IRepository;
import com.sdpizza.groupproject.entity.item.Pizza;
import com.sdpizza.groupproject.entity.model.Order;

import java.util.stream.Collectors;

public class OrderRepository implements IRepository<Order> {
    /* Bruh this is the same as the UserRepository */
    /* TODO: Fix this */
    private static final String SELECT_ORDER =
            "SELECT * FROM ORDERS.$ WHERE CUSTOMER_ID = ? ";

    private static final String INSERT_ORDER =
            "INSERT INTO ORDERS.$ (ITEMS, ORDER_DATE, CUSTOMER_ID)" +
                    "VALUES (?, ?, ?) ";

    /**
     * Gets order based on order ID (order number)
     * @param id order number
     * @return order object or null if order is not found
     */
    @Override
    public Order get(long id) {
        return null;
    }

    /* This needs to return a list ughhhhhh */
    public Order get(Order order) {
        QueryResult queryResult
                = DatabaseConnection.read(SELECT_ORDER,
                                          order.getType().toString(),
                                          order.getUser().getID());
        assert queryResult != null;
        /* Stream the items column and map deserialize on them */
        if (queryResult.nextRow()) {
            Pizza pizza = PizzaSerializer.deserialize((String) queryResult.getRowWithColumns().get("ITEMS"));

            System.out.println(pizza);
        }
        return null;
    }

    @Override
    public boolean add(Order order) {
        String pizzaJSON = order.getItems()
                                .stream()
                                .map(i -> PizzaSerializer.serialize((Pizza) i))
                                .collect(Collectors.joining(","));

        return DatabaseConnection.create(
                INSERT_ORDER.replace("$", order.getType().toString()),
                pizzaJSON,
                java.time.LocalDate.now().toString(),
                order.getUser().getID());
    }

    @Override
    public Order update(Order order) {
        return null;
    }

    @Override
    public boolean remove(long orderNumber) {

        return false;
    }
}
