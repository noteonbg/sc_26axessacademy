package bank;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Simple PostgreSQL connection helper.
 * CHANGE PASSWORD before running.
 */
public class DBConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/bankdb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres"; // <-- change this

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return null;
        }
    }
}
