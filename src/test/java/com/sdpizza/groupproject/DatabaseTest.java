package com.sdpizza.groupproject;

import com.sdpizza.groupproject.database.DatabaseConnection;
import com.sdpizza.groupproject.database.UserRepository;
import com.sdpizza.groupproject.entity.model.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {
    private final User user = new User(10, "feras",
                                     "a", "password",
                                              User.Role.CUSTOMER);
    private final UserRepository userRepository = new UserRepository();

    @BeforeAll
    @Disabled
    public static void init() {
        DatabaseConnection.init();
    }


    @Test
    @DisplayName("Successful Database Delete")
    void successfulDatabaseDelete() {
        userRepository.remove(user.getID());
        assertNull(userRepository.get(user.getID(), user.getPassword()));
    }

    @Test
    @DisplayName("Successful Database Read")
    void successfulDatabaseRead() {
        User user = userRepository.get(this.user.getID(),
                                       this.user.getPassword());
        assert(Arrays.equals(this.user.toArray(), user.toArray()));
    }

    @Test
    @DisplayName("Failed Database Read")
    void failedDatabaseRead() {
        User user = userRepository.get(-1, "password");
        assertNull(user);
    }


    @Test
    @DisplayName("Successful Database Insert")
    void successfulDatabaseInsert() {
        userRepository.add(user);
        assertNotNull(userRepository.get(user.getID(), user.getPassword()));
    }

    @Test
    @Disabled
    @DisplayName("Brute Force Deletion Using Statement")
    void statementDelete() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        Statement stmt = connection.createStatement();
        String sql = "DELETE FROM users WHERE id = 10";
        stmt.executeUpdate(sql);
        connection.commit();
    }
}
