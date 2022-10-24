package com.sdpizza.groupproject.database;

import com.sdpizza.groupproject.util.Result;

import javax.sql.RowSet;
import java.sql.*;
import java.util.Optional;

class DatabaseConnection {
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static Connection connection = null;

    private DatabaseConnection() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public Optional<Connection> getConnection() {
        return Optional.ofNullable(connection);
    }

    /* Maybe use Result if there is a need for more robust error handling */
    public Optional<ResultSet> execute(PreparedStatement preparedStatement) {
        try (ResultSet resultSet = preparedStatement.executeQuery()){
            return Optional.of(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());

            return Optional.empty();
        }
    }
}
