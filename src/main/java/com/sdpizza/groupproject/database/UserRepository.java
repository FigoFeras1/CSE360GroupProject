package com.sdpizza.groupproject.database;

import com.sdpizza.groupproject.entity.model.Order;
import com.sdpizza.groupproject.entity.model.User;
import java.sql.ResultSet;
import java.util.List;

public class UserRepository implements Repository<User> {
    private static final String SELECT_USER =
            "SELECT id, first_name, last_name, password, role " +
                    "FROM users WHERE id = ?";
    @Override
    public User get(long id) {
        QueryResult queryResult = DatabaseConnection.read(SELECT_USER, id);
        return null;
//        return ((User) EntityResolver.resolve(resultSet, User.class));
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
