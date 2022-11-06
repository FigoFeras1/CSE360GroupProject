package com.sdpizza.groupproject.database;

import com.sdpizza.groupproject.entity.model.Order;
import com.sdpizza.groupproject.entity.model.User;
import java.sql.ResultSet;
import java.util.List;

public class UserRepository implements Repository<User> {
    private static final String SELECT_USER =
            "SELECT id, first_name, last_name, password, role " +
                    "FROM users WHERE id = ? AND password = ? ";
    private static final String INSERT_USER =
            "INSERT INTO users (id, first_name, last_name, password, role)" +
                    "VALUES (?, ?, ?, ?, ?);";
    @Override
    public User get(long id) {
        QueryResult queryResult = DatabaseConnection.read(SELECT_USER, id);
        return null;
//        return ((User) EntityResolver.resolve(resultSet, User.class));
    }

    public User get(long id, String password) {
        QueryResult queryResult = DatabaseConnection.read(SELECT_USER, id,
                                                          password);
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
