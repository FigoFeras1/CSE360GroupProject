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

    public static Connection getConnection() { return connection; }

    /**
     * Inserts row into database.
     * @param query Parameterized SQL query to execute in PreparedStatement
     * @param values Parameters for the query String
     * @return True if row inserted successfully, false otherwise
     */
    public static boolean create(String query, Object... values) {
        boolean success = false;

        try (PreparedStatement pstmt = prepareStatement(query, values)) {
            success = pstmt.execute();
            connection.commit();
        } catch (JdbcSQLIntegrityConstraintViolationException ex) {
            System.err.println(ex.getMessage());
        } catch (SQLException ex) {
            handleException(ex, false);
        }

        return success;
    }

    /**
     * Queries database for rows specified in query.
     * @param query Parameterized SQL query to execute in PreparedStatement
     * @param values Parameters for the query String
     * @return QueryResult object which holds column names and values, or null
     */
    public static QueryResult read(String query, Object... values) {

        try (PreparedStatement pstmt = prepareStatement(query, values)) {
            ResultSet resultSet = pstmt.executeQuery();

            return (new QueryResult(resultSet));
        } catch (SQLException ex) {
            handleException(ex, false);

            return null;
        }
    }

    public static void update(String query, Object... values) {
        boolean success = false;
        try (PreparedStatement pstmt = prepareStatement(query, values)) {
            success = (pstmt.executeUpdate() >= 1);
            connection.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

//        return success;
    }

    /**
     * Deletes rows from database specified in query.
     * @param query Parameterized SQL query to execute in PreparedStatement
     * @param values Parameters for the query String
     */
    public static void delete(String query, Object... values) {
        boolean success = false;
        try (PreparedStatement pstmt = prepareStatement(query, values)) {
            success = (pstmt.executeUpdate() >= 1);
            connection.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

//        return success;
    }

    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    /**
     * Internal helper method which prepares a PreparedStatement for any of the
     * methods above
     * @param query Parameterized SQL query to initialize PreparedStatement with
     * @param values Parameters for the query String
     * @return Prepared PrepareStatement object
     * @throws SQLException thrown because why not
     */
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