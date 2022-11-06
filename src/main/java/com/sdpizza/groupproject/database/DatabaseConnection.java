package com.sdpizza.groupproject.database;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import java.sql.*;

public class DatabaseConnection {
    private final static String DRIVER = "org.h2.Driver";
    private final static String USER = "admin";
    private final static String PASSWORD = "password";
    private final static String URL = "jdbc:h2:./database/db/sdpizza;AUTO_SERVER=TRUE";
    private static Connection connection;

    public static void init() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
        } catch (Exception ex) {
            handleException(ex, true);
        }

        System.out.println("Database connected successfully.");
    }

    public static Connection getConnection() {
        return connection;
    }


    public static boolean create(String query, Object... values) {
        boolean success = false;

        try (PreparedStatement pstmt = prepareStatement(query, values)) {
            success = pstmt.execute();
            connection.commit();
        } catch (JdbcSQLIntegrityConstraintViolationException ex) {
            System.out.println("User with id already exists.");
            System.out.println(ex.getSQL());
        }
        catch (SQLException ex) {
            handleException(ex, false);

        }

        return success;
    }

    public static QueryResult read(String query, Object... values) {

        try (PreparedStatement pstmt = prepareStatement(query, values)) {
            ResultSet resultSet = pstmt.executeQuery();

            return (new QueryResult(resultSet));
        } catch (SQLException ex) {
            handleException(ex, false);

            return null;
        }
    }

    public static boolean update() { return false; }
    public static boolean delete() { return false; }

    private static PreparedStatement prepareStatement(String query,
                                                      Object... values)
    throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(query);

        for (int i = 1; i <= values.length; ++i) {
            pstmt.setObject(i, values[i - 1]);
        }

        return pstmt;
    }

    private static void handleException(Exception ex, boolean exit) {
        ex.printStackTrace();
        System.err.println("[ERROR]: " + ex.getMessage());
        if (exit) System.exit(-1);
    }
}