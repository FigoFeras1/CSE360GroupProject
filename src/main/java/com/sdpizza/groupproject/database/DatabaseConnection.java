package com.sdpizza.groupproject.database;

import java.sql.*;

public class DatabaseConnection {
    private final static String DRIVER = "org.h2.Driver";
    private final static String USER = "admin";
    private final static String PASSWORD = "password";
    private final static String URL = "jdbc:h2:./database/db/sdpizza;FILE_LOCK=NO";
    private static Connection connection;

    public static void init() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception ex) {
            handleException(ex, true);
        }

        System.out.println("Database connected successfully.");
    }

    public static Connection getConnection() {
        return connection;
    }

    public static boolean create() { return false; }

    /* TODO: Figure out if I want to use ConnectionPool */
    public static QueryResult read(String query, Object... values) {

        try(PreparedStatement pstmt =
                    connection.prepareStatement(query))
        {
            connection.setAutoCommit(false);
            for (int i = 1; i <= values.length; ++i) {
                pstmt.setObject(i, values[i - 1]);
            }

            ResultSet resultSet = pstmt.executeQuery();

            return (new QueryResult(pstmt.executeQuery()));
        } catch (SQLException ex) {
            handleException(ex, false);

            return null;
        }
    }

    public static boolean update() { return false; }
    public static boolean delete() { return false; }

    private static void handleException(Exception ex, boolean exit) {
        ex.printStackTrace();
        System.err.println("[ERROR]: " + ex.getMessage());
        if (exit) System.exit(-1);
    }
}