package com.sdpizza.groupproject.database;

import com.sdpizza.groupproject.database.repository.UserRepository;
import com.sdpizza.groupproject.entity.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDatabaseTests {
    private final UserRepository userRepository = new UserRepository();
    private final User user =
            new User(1000000000, "feras", "a", "password",
                    User.Role.CUSTOMER);
    private final User admin =
            new User(9999999999L, "admin", "admin", "password",
                    User.Role.CUSTOMER);

    @BeforeAll
    protected static void init() {
        DatabaseConnection.init();
    }

    @Test
    protected void insertAndRemoveUser() {
        /* Add user to database */
        userRepository.add(admin);

        /* Get user from database */
        User queriedUser = userRepository.get(admin);
        assertNotNull(queriedUser);
        assertEquals(admin, queriedUser);

        /* Delete user from database */
        userRepository.remove(queriedUser);
        assertNull(userRepository.get(queriedUser));
    }
}
