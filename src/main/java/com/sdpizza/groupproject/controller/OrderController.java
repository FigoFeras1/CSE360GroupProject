package com.sdpizza.groupproject.controller;

import com.sdpizza.groupproject.database.repository.OrderRepository;
import com.sdpizza.groupproject.entity.model.Order;

import java.util.List;


public class OrderController {
    private final OrderRepository orderRepository = new OrderRepository();

    public boolean processOrder(Order order) {
        return orderRepository.add(order);
    }

    public Order getOrder(Order order) {
        return orderRepository.get(order);
    }

    public List<Order> getOrders(Order.Status status) {
        return orderRepository.get(status);
    }

    public List<Order> getPastUserOrders() {
        return orderRepository.get(Controller.activeUser.getID(), Order.Status.READY);
    }

    public void nextStage(Order order) {
        Order.Status[] stages = Order.Status.values();
        List<Order.Status> status = List.of(stages);
        orderRepository.remove(order);
        order.setStatus(stages[status.indexOf(order.getStatus()) + 1]);
        orderRepository.add(order);
    }
}
