package com.sdpizza.groupproject.database.repository;

import com.sdpizza.groupproject.database.DatabaseConnection;
import com.sdpizza.groupproject.database.QueryResult;
import com.sdpizza.groupproject.database.serializer.OrderDeserializer;
import com.sdpizza.groupproject.database.serializer.OrderSerializer;
import com.sdpizza.groupproject.entity.model.Order;
import com.sdpizza.groupproject.entity.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderRepository {
    /* Bruh this is the same as the UserRepository */
    /* TODO: Fix this */
    private static final String SELECT_ORDER_BY_USER =
            "SELECT * FROM ORDERS.$ WHERE CUSTOMER_ID = ? ";

    private static final String SELECT_ORDER =
            "SELECT * FROM ORDERS.$ WHERE ID = ? ";

    private static final String SELECT_ORDER_BY_STATUS =
            "SELECT * FROM ORDERS.$ ";

    private static final String INSERT_ORDER =
            "INSERT INTO ORDERS.$ (ITEMS, ORDER_DATE, CUSTOMER_ID)" +
                    "VALUES (?, ?, ?) ";

    private static final String INSERT_ORDER_WITH_ID =
            "INSERT INTO ORDERS.$ (ID, ITEMS, ORDER_DATE, CUSTOMER_ID)" +
                    "VALUES (?, ?, ?, ?) ";

    private static final String DELETE_ORDER =
            "DELETE FROM ORDERS.$ WHERE ID = ?";

    /**
     * Gets order based on order ID (order number)
     * @param order object with order number and status
     * @return order object or null if order is not found
     */
    public Order get(Order order) {
        Order newOrder = null;
        QueryResult queryResult =
                DatabaseConnection.read(queryString(SELECT_ORDER, order.getStatus()));

        assert queryResult != null;
        if (queryResult.nextRow()) {
            HashMap<String, Object> results = queryResult.getRowWithColumns();
            String items = results.get("items").toString();
            newOrder =
                    new Order(
                              OrderDeserializer.deserialize(items).getItems(),
                              order.getUser(),
                              order.getStatus());
            newOrder.setID(Long.parseLong(results.get("id").toString()));
        }

        return newOrder;
    }

    /**
     * Returns orders based on status alone
     * @param status status of orders to be returned
     * @return list of orders
     */
    public List<Order> get(Order.Status status) {
        QueryResult queryResult =
                DatabaseConnection.read(queryString(SELECT_ORDER_BY_STATUS, status));

        assert queryResult != null;
        return orderList(queryResult);
    }

    /**
     * Gets orders based on the user
     * @param userID ID of the customer that placed the order
     * @param status status of the order
     * @return list of orders from that user
     */
    public List<Order> get(long userID, Order.Status status) {
        QueryResult queryResult =
                DatabaseConnection.read(queryString(SELECT_ORDER_BY_USER, status),
                                        userID);

        assert queryResult != null;
        return orderList(queryResult);
    }

    /**
     * Adds order to the database. Once the order is added successfully,
     * this method sets the order object's ID to the number in the database.
     * This ID will and must remain the same throughout all different status' of
     * the order, and therefore must be preserved
     * @param order order object that must contain the status and user
     * @return true if the order is inserted "successfully", else false
     */
    public boolean add(Order order) {
        String itemJSON = OrderSerializer.serialize(order);

        if (order.getID() == -1) {
            long generatedKey =
                    DatabaseConnection.create(
                            queryString(INSERT_ORDER, order.getStatus()),
                            itemJSON,
                            java.time.LocalDate.now().toString(),
                            order.getUser().getID()
                    );
            order.setID(generatedKey);

            return (generatedKey != -1);
        }

        long generatedKey =
                DatabaseConnection.create(
                        queryString(INSERT_ORDER_WITH_ID, order.getStatus()),
                        order.getID(),
                        itemJSON,
                        java.time.LocalDate.now().toString(),
                        order.getUser().getID()
                );

        return (generatedKey == -1);
    }

    /* public Order update(Order order, Order.Status newStatus) {} */

    /**
     * Deletes order from the database
     * @param order order with status and id to remove
     * @return true if the order was deleted "successfully", else false
     */
    public boolean remove(Order order) {
        return DatabaseConnection.delete(queryString(DELETE_ORDER, order.getStatus()),
                                         order.getID());
    }

    /* Helper methods */
    private String queryString(String query, Order.Status status) {
        return query.replace("$", status.toString());
    }

    private List<Order> orderList(QueryResult queryResult) {
        List<Order> orders = new ArrayList<>();
        while (queryResult.nextRow()) {
            HashMap<String, Object> row = queryResult.getRowWithColumns();

            User user = new User();
            user.setID(Long.parseLong(row.get("customer_id").toString()));

            Order order = OrderDeserializer.deserialize(row.get("items").toString());
            order.setID(Long.parseLong(row.get("id").toString()));
            order.setUser(user);
            order.setStatus(Order.Status.valueOf(queryResult.getTableName()));

            orders.add(order);
        }

        return orders;
    }
}
