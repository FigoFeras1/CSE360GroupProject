package com.sdpizza.groupproject.database.repository;

import com.sdpizza.groupproject.database.DatabaseConnection;
import com.sdpizza.groupproject.database.QueryResult;
import com.sdpizza.groupproject.entity.model.User;

import java.util.HashMap;

public class UserRepository {
    private static final String SELECT_USER =
            "SELECT * FROM users WHERE id = ? AND password = ? ";
    private static final String INSERT_USER =
            "INSERT INTO users (id, first_name, last_name, password, role) " +
                    "VALUES (?, ?, ?, ?, ?) ";

    private static final String DELETE_USER =
            "DELETE FROM users WHERE id = ? ";

    private static final String UPDATE_USER_PASSWORD =
            "UPDATE users SET password = ? WHERE id = ? ";

    /**
     * Queries database for the user with the desired ID and password.
     * @param user object containing the ASUID and password
     * @return User object if found in database, null otherwise
     */
    public User get(User user) {
        QueryResult queryResult = DatabaseConnection.read(SELECT_USER, user.getID(),
                user.getPassword());

        assert queryResult != null;
        return create(queryResult);
    }

    /**
     * Adds user to the database.
     * @param user user to add
     * @return true if user was added successfully, false otherwise.
     */
    public boolean add(User user) {
        return (DatabaseConnection.create(INSERT_USER, user.toArray()) != -1);
    }

    /**
     * Updates user password
     * @param user user object that contains the new password
     * @return true if the user was added "successfully", else false.
     */
    public boolean update(User user) {
        return DatabaseConnection.update(UPDATE_USER_PASSWORD, user.getPassword(),
                                         user.getID());
    }

    /**
     * Removes user with specified ID from the database
     * @param user user with ID to remove
     * @return true if the removal is a "success"
     */
    public boolean remove(User user) {
        return DatabaseConnection.delete(DELETE_USER, user.getID());
    }

    /* Helpter methods */
    /* TODO: Make this better */
    private User create(QueryResult queryResult) {
        if (queryResult.nextRow()) {
            HashMap<String, Object> values = queryResult.getRowWithColumns();

            long id = Long.parseLong((String) values.get("id"));
            String first_name = values.get("first_name").toString();
            String last_name = values.get("last_name").toString();
            String password = values.get("password").toString();
            User.Role role = User.Role.valueOf(values.get("role").toString().toUpperCase());

            return new User(id, first_name, last_name, password, role);
        }

        return null;
    }
}
