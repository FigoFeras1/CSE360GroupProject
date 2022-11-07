package com.sdpizza.groupproject.database;

import com.sdpizza.groupproject.entity.item.Item;
import com.sdpizza.groupproject.entity.item.Pizza;
import com.sdpizza.groupproject.entity.model.Order;

public class OrderRepository implements IRepository<Order> {
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

    @Override
    public boolean add(Order order) {
        StringBuilder pizzaJSON = new StringBuilder();

        for (Item item : order.getItems()) {
            pizzaJSON.append(PizzaSerializer.serialize((Pizza) item));
        }

        return DatabaseConnection.create(
                INSERT_ORDER.replace("$", order.getType().toString()),
                pizzaJSON.toString(),
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
