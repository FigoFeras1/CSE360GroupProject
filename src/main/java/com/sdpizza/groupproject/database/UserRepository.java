package com.sdpizza.groupproject.database;

import com.sdpizza.groupproject.model.Order;
import com.sdpizza.groupproject.model.User;
import java.sql.ResultSet;
import java.util.List;

public class UserRepository implements Repository<User> {
    private static final String SELECT_USER =
            "SELECT id, first_name, last_name, password, role " +
                    "FROM users WHERE id = ?";
    @Override
    public User get(long id) {
        ResultSet resultSet = DatabaseConnection.execute(SELECT_USER, id);
        try {
            System.out.println(resultSet.isClosed());
        } catch(Exception e) {

        }
        return ((User) ModelResolver.resolve(resultSet, User.class));
    }
    public List<Order> getOrders(long id, Order.Type orderType) {

        return null;
    }

    @Override
    public void add(User user) {

    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void remove(User user) {

    }

    private ResultSet query(String query) {

        return null;
    }
}
