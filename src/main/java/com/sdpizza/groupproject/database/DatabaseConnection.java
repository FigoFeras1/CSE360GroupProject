package com.sdpizza.groupproject.database;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.h2.tools.Script;

import java.sql.*;

public class DatabaseConnection {
    private final static String DRIVER = "org.h2.Driver";
    private final static String USER = "admin";
    private final static String PASSWORD = "password";
    private final static String URL =
            "jdbc:h2:./database/db/sdpizza;AUTO_SERVER=TRUE";
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
    public static long create(String query, Object... values) {
        long generatedKey = -1;

        try (PreparedStatement pstmt = prepareStatement(query, values)) {
            pstmt.executeUpdate();
            connection.commit();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) generatedKey = rs.getLong(1);
        } catch (JdbcSQLIntegrityConstraintViolationException ex) {
            System.err.println(ex.getMessage());
        } catch (SQLException ex) {
            handleException(ex, false);
        }

        return generatedKey;
    }

    /**
     * Queries database for rows specified in query.
     * @param query Parameterized SQL query to execute in PreparedStatement
     * @param values Parameters for the query String
     * @return QueryResult object which holds column names and values, or null
     */
    public static QueryResult read(String query, Object... values) {
        try (PreparedStatement pstmt = prepareStatement(query, values)) {
            return (new QueryResult(pstmt.executeQuery()));
        } catch (SQLException ex) {
            handleException(ex, false);

            return null;
        }
    }

    /**
     * Updates rows in database given the query String and an array of
     * @param query Parameterized SQL query to execute in PreparedStatement
     * @param values Parameters for the query String
     * @return QueryResult object which holds column names and values, or null
     */

    public static boolean update(String query, Object... values) {
        boolean success = false;
        try (PreparedStatement pstmt = prepareStatement(query, values)) {
            success = (pstmt.executeUpdate() >= 1);
            connection.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return success;
    }

    /**
     * Deletes rows from database specified in query.
     * @param query Parameterized SQL query to execute in PreparedStatement
     * @param values Parameters for the query String
     * @return true if 1 or more rows were affected, false otherwise
     */
    public static boolean delete(String query, Object... values) {
        boolean success = false;
        try (PreparedStatement pstmt = prepareStatement(query, values)) {
            success = (pstmt.executeUpdate() >= 1);
            connection.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return success;
    }

    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static void dumpSQL() {
        try {
            Script.process(connection, "./database/dump.sql",
                   "", "");
        } catch (SQLException ex) { ex.printStackTrace(); }
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
        PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

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