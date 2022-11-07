package com.sdpizza.groupproject.database;

import com.sdpizza.groupproject.entity.model.Order;

public class OrderRepository implements IRepository<Order> {
    private static final String SELECT_SAVED_ORDER =
            "SELECT * FROM orders.saved WHERE user =";

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

        return false;
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
