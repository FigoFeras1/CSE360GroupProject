package com.sdpizza.groupproject.database.repository;

import com.sdpizza.groupproject.database.DatabaseConnection;
import com.sdpizza.groupproject.database.serializer.ItemSerializer;
import com.sdpizza.groupproject.database.QueryResult;
import com.sdpizza.groupproject.entity.model.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class OrderRepository {
    /* Bruh this is the same as the UserRepository */
    /* TODO: Fix this */
    private static final String SELECT_ORDER =
            "SELECT * FROM ORDERS.$ WHERE CUSTOMER_ID = ? ";

    private static final String INSERT_ORDER =
            "INSERT INTO ORDERS.$ (ITEMS, ORDER_DATE, CUSTOMER_ID)" +
                    "VALUES (?, ?, ?) ";

    private static final String DELETE_ORDER =
            "DELETE FROM ORDERS.$ WHERE ID = ?";

    /**
     * Gets order based on order ID (order number)
     * @param order object with order number and status
     * @return order object or null if order is not found
     */
    public Order get(Order order) {
        return null;
    }

    /* This needs to return a list ughhhhhh */
    @SuppressWarnings("unchecked")
    public List<Object> get(Order order, long userID) {
        QueryResult queryResult
                = DatabaseConnection.read(SELECT_ORDER.replace("$", order.getType().toString()),
                                          order.getUser().getID());
        assert queryResult != null;
        List<HashMap<String, Object>> results = queryResult.getResults();
        List<Object> items = new ArrayList<>();
//        results.forEach(i -> items.add(i.get("items")));
        for (HashMap<String, Object> m : results) {
            items.add(m.get("items"));
        }

        List<Object> items2 = new ArrayList<>();
        for (Object item : items) {
            items2.add(ItemSerializer.deserialize(item.toString()));
        }
        items.forEach(i -> ItemSerializer.deserialize((String) i));
        return items2;

        /* Stream the items column and map deserialize on them */
/*
        return queryResult.getResults()
                          .stream()
                          .map(r -> ItemSerializer.deserialize(r.get("items")
                                                                .toString()))
                          .map(Order.class::cast)
                          .collect(Collectors.toList());
*/
    }

    public boolean add(Order order) {
//        String itemJSON = order.getItems()
//                                .stream()
//                                .map(ItemSerializer::serialize)
//                                .collect(Collectors.joining(","));
//        System.out.println("itemJSON " + itemJSON);
//
//        return DatabaseConnection.create(
//                INSERT_ORDER.replace("$", order.getType().toString()),
//                itemJSON,
//                java.time.LocalDate.now().toString(),
//                order.getUser().getID());
        return false;
    }

    public Order update(Order order) {
        return null;
    }

    public boolean remove(Order order) {
        return DatabaseConnection.delete(
                DELETE_ORDER.replace("$", order.getType().toString()),
                order.getID());
    }
}
