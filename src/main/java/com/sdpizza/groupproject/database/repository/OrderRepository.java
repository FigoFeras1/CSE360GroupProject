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
                DatabaseConnection.read(SELECT_ORDER.replace("$", order.getStatus().toString()),
                                        order.getID());

        assert queryResult != null;
        if (queryResult.nextRow()) {
            HashMap<String, Object> results = queryResult.getRowWithColumns();
            newOrder =
                    new Order(
                              OrderDeserializer.deserialize(results.get("items").toString()).getItems(),
                              order.getUser(),
                              order.getStatus());
            newOrder.setID(Long.parseLong(results.get("id").toString()));
        }
        return newOrder;
    }
    public List<Order> get(Order.Status status) {
        QueryResult queryResult
                = DatabaseConnection.read(SELECT_ORDER_BY_STATUS.replace("$", status.toString()));
        assert queryResult != null;
        return orderList(queryResult);
    }

    public List<Order> get(long userID, Order.Status status) {
        QueryResult queryResult
                = DatabaseConnection.read(SELECT_ORDER_BY_USER.replace("$",
                                          status.toString()), userID);
        assert queryResult != null;
        return orderList(queryResult);
    }

    public boolean add(Order order) {
        String itemJSON = OrderSerializer.serialize(order);
        if (order.getID() == -1) {
            long generatedKey =
                    DatabaseConnection.create(
                            INSERT_ORDER.replace("$", order.getStatus().toString()),
                            itemJSON,
                            java.time.LocalDate.now().toString(),
                            order.getUser().getID()
                    );
            order.setID(generatedKey);

            return (generatedKey != -1);
        }

        long generatedKey =
                DatabaseConnection.create(
                        INSERT_ORDER_WITH_ID.replace("$", order.getStatus().toString()),
                        order.getID(),
                        itemJSON,
                        java.time.LocalDate.now().toString(),
                        order.getUser().getID()
                );

        return (generatedKey == -1);
    }

    /* Updating the status of the order */
/*
    public Order update(Order order, Order.Status newStatus) {
    }
*/

    public boolean remove(Order order) {
        return DatabaseConnection.delete(
                DELETE_ORDER.replace("$", order.getStatus().toString()),
                order.getID());
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

//        return queryResult.getResults()
//                .stream()
//                .map(r -> ItemSerializer.deserialize(r.get("items")
//                        .toString()))
//                .map(Order.class::cast)
//                .collect(Collectors.toList());
    }
}
