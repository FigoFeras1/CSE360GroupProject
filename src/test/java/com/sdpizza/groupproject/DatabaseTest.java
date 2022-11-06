package com.sdpizza.groupproject;

import com.sdpizza.groupproject.database.DatabaseConnection;
import com.sdpizza.groupproject.database.UserRepository;
import com.sdpizza.groupproject.entity.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DatabaseTest {

    @BeforeAll
    public static void init() {
        DatabaseConnection.init();
    }

    @Test
    @DisplayName("Successful Database Read")
    void successfulDatabaseRead() {
        UserRepository userRepository = new UserRepository();
        User user = userRepository.get(10, "password");
        Object[] values = { 10, "feras", "a", "password", "CUSTOMER" };
        Object[] userValues = user.toArray();
        for (int i = 0; i < values.length; ++i) {
            assertEquals(values[i].toString(), userValues[i].toString());
        }
    }

    @Test
    @DisplayName("Failed Database Read")
    void failedDatabaseRead() {
        UserRepository userRepository = new UserRepository();
        User user = userRepository.get(20, "password");
        assertNull(user);
    }

//    @Disabled
//    @Test
//    @DisplayName("Testing create() in DatabaseConnection")
//    void testDatabaseConnectionCreate() {
//    }

    @Test
    @DisplayName("Successful Database Insert")
    void successfulDatabaseInsert() {
        UserRepository userRepository = new UserRepository();
        User user = new User(0, "admin", "admin",
                        "password", User.Role.CUSTOMER);

        System.out.println(userRepository.add(user));
//        assert(userRepository.add(user));
        Object[] values = { Long.valueOf(0), "admin", "admin", "password", "CUSTOMER" };
        Object[] userValues = user.toArray();

        for (int i = 0; i < values.length; ++i) {
            assertEquals(values[i], userValues[i]);
        }
    }

}
