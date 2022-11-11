package com.sdpizza.groupproject.database.repository;

import com.sdpizza.groupproject.database.DatabaseConnection;
import com.sdpizza.groupproject.database.serializer.ItemSerializer;
import com.sdpizza.groupproject.database.QueryResult;
import com.sdpizza.groupproject.database.serializer.OrderDeserializer;
import com.sdpizza.groupproject.database.serializer.OrderSerializer;
import com.sdpizza.groupproject.entity.model.Order;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
                DatabaseConnection.read(SELECT_ORDER.replace("$", order.getType().toString()),
                                        order.getID());

        assert queryResult != null;
        if (queryResult.nextRow()) {
            HashMap<String, Object> results = queryResult.getRowWithColumns();
            newOrder =
                    new Order(
                              OrderDeserializer.deserialize(results.get("items").toString()).getItems(),
                              order.getUser(),
                              order.getType());
            newOrder.setID((Integer) results.get("id"));
        }
        return newOrder;
    }
    public List<Order> get(Order.Status status) {
        QueryResult queryResult
                = DatabaseConnection.read(SELECT_ORDER_BY_STATUS.replace("$", status.toString()));
        assert queryResult != null;
        List<Order> orders = orderList(queryResult);
        System.out.println(orders);
        return orders;
    }

    public List<Order> get(long userID, Order.Status status) {
        QueryResult queryResult
                = DatabaseConnection.read(SELECT_ORDER_BY_USER.replace("$",
                                          status.toString()), userID);
        assert queryResult != null;
//        results.forEach(i -> items.add(i.get("items")));
//        for (HashMap<String, Object> m : results) {
//            items.add(m.get("items"));
//        }
//        for (Object item : items) {
//            order = OrderDeserializer.deserialize(item.toString());
//        }
//        items.forEach(i -> ItemSerializer.deserialize((String) i));
//        return orders;

        List<Order> orders = orderList(queryResult);
        System.out.println(orders);
        return orders;
    }

    public boolean add(Order order) {
        String itemJSON = OrderSerializer.serialize(order);

        return DatabaseConnection.create(
                INSERT_ORDER.replace("$", order.getType().toString()),
                itemJSON,
                java.time.LocalDate.now().toString(),
                order.getUser().getID());
    }

    /* Updating the status of the order */
/*
    public Order update(Order order, Order.Status newStatus) {
    }
*/

    public boolean remove(Order order) {
        return DatabaseConnection.delete(
                DELETE_ORDER.replace("$", order.getType().toString()),
                order.getID());
    }

    private List<Order> orderList(QueryResult queryResult) {
        return queryResult.getResults()
                .stream()
                .map(r -> ItemSerializer.deserialize(r.get("items")
                        .toString()))
                .map(Order.class::cast)
                .collect(Collectors.toList());
    }
}
