package com.sdpizza.groupproject;

import com.sdpizza.groupproject.database.DatabaseConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DatabaseTest {

    @BeforeAll
    public static void init() {
        DatabaseConnection.init();
    }

    @Test
    @DisplayName("QueryResult Test")
    void queryTest() {

    }

}
