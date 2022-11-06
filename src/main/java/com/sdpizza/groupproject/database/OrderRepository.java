package com.sdpizza.groupproject.database;

import com.sdpizza.groupproject.entity.model.Order;

public class OrderRepository implements Repository<Order> {
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
    public void remove(long orderNumber) {

    }
}
