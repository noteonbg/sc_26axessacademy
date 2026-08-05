package com.example.employee.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Configuration and Connection Utility for PostgreSQL Database.
 * Allows configuration via System properties, Environment variables, or defaults.
 */
public class DatabaseConfig {

    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/employeedb";
    private static final String DEFAULT_USER = "postgres";
    private static final String DEFAULT_PASSWORD = "postgres";

    static {
        try {
            // Explicitly load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found on classpath: " + e.getMessage());
        }
    }

    /**
     * Gets the configured PostgreSQL JDBC URL.
     */
    public static String getUrl() {
        String envUrl = System.getenv("DB_URL");
        if (envUrl != null && !envUrl.isBlank()) {
            return envUrl;
        }
        return System.getProperty("db.url", DEFAULT_URL);
    }

    /**
     * Gets the configured PostgreSQL database username.
     */
    public static String getUser() {
        String envUser = System.getenv("DB_USER");
        if (envUser != null && !envUser.isBlank()) {
            return envUser;
        }
        return System.getProperty("db.user", DEFAULT_USER);
    }

    /**
     * Gets the configured PostgreSQL database password.
     */
    public static String getPassword() {
        String envPass = System.getenv("DB_PASSWORD");
        if (envPass != null && !envPass.isBlank()) {
            return envPass;
        }
        return System.getProperty("db.password", DEFAULT_PASSWORD);
    }

    /**
     * Obtains a new JDBC Connection to PostgreSQL.
     *
     * @return Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getUrl(), getUser(), getPassword());
    }

    /**
     * Checks if PostgreSQL database connection can be successfully established.
     *
     * @return true if database is reachable, false otherwise
     */
    public static boolean isDatabaseAvailable() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Initializes the database table DDL if it does not already exist.
     *
     * @param conn Open database connection
     * @throws SQLException if DDL execution fails
     */
    public static void initializeDatabase(Connection conn) throws SQLException {
        String ddl = """
            CREATE TABLE IF NOT EXISTS employees (
                emp_no INT PRIMARY KEY,
                emp_name VARCHAR(100) NOT NULL,
                email VARCHAR(100) NOT NULL,
                location VARCHAR(100) NOT NULL
            );
            """;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(ddl);
        }
    }
}
