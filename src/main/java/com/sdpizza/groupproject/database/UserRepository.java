package com.sdpizza.groupproject.database;

import com.sdpizza.groupproject.entity.model.Order;
import com.sdpizza.groupproject.entity.model.User;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class UserRepository implements Repository<User> {
    private static final String SELECT_USER =
            "SELECT id, first_name, last_name, password, role " +
                    "FROM users WHERE id = ? AND password = ? ";
    private static final String INSERT_USER =
            "INSERT INTO users (id, first_name, last_name, password, role) " +
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

        assert queryResult != null;
        return create(queryResult);
    }

    public List<Order> getOrders(long id, Order.Type orderType) {

        return null;
    }

    @Override
    public boolean add(User user) {
        return DatabaseConnection.create(INSERT_USER, user.toArray());
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public boolean remove(User user) {

        return false;
    }

    private ResultSet query(String query) {

        return null;
    }

    /* TODO: Make this better */
    private User create(QueryResult queryResult) {
        if (queryResult.nextRow()) {
            HashMap<String, Object> values = queryResult.getRowWithColumns();

            long id = Long.valueOf((Integer) values.get("id"));
            String first_name = values.get("first_name").toString();
            String last_name = values.get("last_name").toString();
            String password = values.get("password").toString();
            User.Role role = User.Role.valueOf(values.get("role").toString());

            return new User(id, first_name, last_name, password, role);
        }

        return null;
    }
}
