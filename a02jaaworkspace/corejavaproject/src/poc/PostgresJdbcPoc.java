package poc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *  POC demonstrating core JDBC concepts in Java for PostgreSQL:
 * 1. Establishing a Connection using DriverManager
 * 2. Parameterized queries with PreparedStatement (prevention of SQL injection)
 * 3. executing updates (INSERT, UPDATE, DELETE) using executeUpdate()
 * 4. executing queries (SELECT ALL, SELECT BY NON-PK COLUMN) using executeQuery()
 * 5. Iterating ResultSet with rs.next() and mapping rows into a Java List<Employee>
 */
public class PostgresJdbcPoc {

    // Connection constants (adjust to your PostgreSQL environment)
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    public static void main(String[] args) {
        System.out.println("=========================================================");
        System.out.println("   POSTGRESQL JDBC SYNTAX & RESULTSET MAPPING POC");
        System.out.println("=========================================================\n");

        // STEP 1: Establish Connection using try-with-resources
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("✅ Connected to PostgreSQL successfully!\n");

            // Setup table for demo
            setupDatabaseTable(conn);

            // ------------------------------------------------------------------
            // DEMO 1: INSERT OPERATION (executeUpdate)
            // ------------------------------------------------------------------
            System.out.println("--- 1. INSERTING EMPLOYEES ---");
           // insertEmployee(conn, 221, "David Miller", "david.m@example.com", "Chicago");
           // insertEmployee(conn, 222, "Eva Green", "eva.g@example.com", "New York");
           // insertEmployee(conn, 223, "Frank Wright", "frank.w@example.com", "Chicago");
            System.out.println();

            // ------------------------------------------------------------------
            // DEMO 2: SELECT ALL (executeQuery + Mapping ResultSet -> List)
            // ------------------------------------------------------------------
            System.out.println("--- 2. SELECT ALL EMPLOYEES (Mapping ResultSet to List) ---");
            List<Employee> allEmployees = selectAllEmployees(conn);
            allEmployees.forEach(emp -> System.out.println("  -> " + emp));
            System.out.println();

            // ------------------------------------------------------------------
            // DEMO 3: SELECT BY NON-PRIMARY KEY COLUMN (location = 'Chicago')
            // ------------------------------------------------------------------
            System.out.println("--- 3. SELECT BY NON-PRIMARY KEY COLUMN (location = 'Chicago') ---");
            List<Employee> chicagoEmployees = selectEmployeesByLocation(conn, "Chicago");
            chicagoEmployees.forEach(emp -> System.out.println("  -> " + emp));
            System.out.println();

            /*
             ------------------------------------------------------------------
             DEMO 4: UPDATE OPERATION (executeUpdate)
             ------------------------------------------------------------------
            */
            System.out.println("--- 4. UPDATING EMPLOYEE (empNo = 201) ---");
            //updateEmployee(conn, 201, "David Miller Jr.", "david.mjr@example.com", "Los Angeles");
            
            System.out.println("Re-querying Chicago Employees after location update for 201:");
            selectEmployeesByLocation(conn, "Chicago").forEach(emp -> System.out.println("  -> " + emp));
            System.out.println();

            // ------------------------------------------------------------------
            // DEMO 5: DELETE OPERATION (executeUpdate)
            // ------------------------------------------------------------------
            System.out.println("--- 5. DELETING EMPLOYEE (empNo = 202) ---");
            deleteEmployee(conn, 902);

            System.out.println("\nFinal List of All Employees:");
            selectAllEmployees(conn).forEach(emp -> System.out.println("  -> " + emp));

        } catch (SQLException e) {
            System.err.println("⚠️  Could not connect to PostgreSQL or execute POC: " + e.getMessage());
            System.err.println("    Make sure PostgreSQL server is running on localhost:5432 with database 'employeedb'.");
        }
    }

    /**
     * Helper to create the demo table if it does not exist.
     */
    private static void setupDatabaseTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS employees (
                emp_no INT PRIMARY KEY,
                emp_name VARCHAR(100) NOT NULL,
                email VARCHAR(100) NOT NULL,
                location VARCHAR(100) NOT NULL
            );
            """;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    // =========================================================================
    // 1. INSERT FUNCTION: Demonstrates PreparedStatement & executeUpdate()
    // =========================================================================
    public static void insertEmployee(Connection conn, int empNo, String name, String email, String location) throws SQLException {
        // PreparedStatement SQL with parameter placeholders (?)
        String sql = "INSERT INTO employees (emp_no, emp_name, email, location) VALUES (?, ?, ?, ?)";

        // Try-with-resources automatically closes PreparedStatement after execution
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Bind parameters (1-indexed)

            pstmt.setString(3, email);     // Third ? -> email
            pstmt.setInt(1, empNo);       // First ? -> emp_no
            pstmt.setString(2, name);      // Second ? -> emp_name
            pstmt.setString(4, location);  // Fourth ? -> location

            // executeUpdate() is used for INSERT, UPDATE, DELETE queries
            int rowsInserted = pstmt.executeUpdate();
            System.out.println("Inserted employee [empNo=" + empNo + "], rows affected: " + rowsInserted);
        }
    }

    // =========================================================================
    // 2. UPDATE FUNCTION: Demonstrates PreparedStatement UPDATE query
    // =========================================================================
    public static void updateEmployee(Connection conn, int empNo, String newName, String newEmail, String newLocation) throws SQLException {
        String sql = "UPDATE employees SET emp_name = ?, email = ?, location = ? WHERE emp_no = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setString(2, newEmail);
            pstmt.setString(3, newLocation);
            pstmt.setInt(4, empNo); // Primary Key filter

            int rowsUpdated = pstmt.executeUpdate();
            System.out.println("Updated employee [empNo=" + empNo + "], rows affected: " + rowsUpdated);
        }
    }

    // =========================================================================
    // 3. DELETE FUNCTION: Demonstrates PreparedStatement DELETE query
    // =========================================================================
    public static void deleteEmployee(Connection conn, int empNo) throws SQLException {
        String sql = "DELETE FROM employees WHERE emp_no = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, empNo);

            int rowsDeleted = pstmt.executeUpdate();
            if( rowsDeleted == 0)
                System.out.println("no employee found with " + empNo);
            else
             System.out.println("Deleted employee [empNo=" + empNo + "], rows affected: " + rowsDeleted);
        }
    }

    // =========================================================================
    // 4. SELECT ALL FUNCTION: Demonstrates ResultSet iteration & List mapping
    // =========================================================================
    public static List<Employee> selectAllEmployees(Connection conn) throws SQLException {
        // Create an empty List to accumulate Employee domain objects
        List<Employee> employeeList = new ArrayList<>();
        String sql = "SELECT emp_no, emp_name, email, location FROM employees ORDER BY emp_no";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             // executeQuery() returns a ResultSet representing database rows
             ResultSet rs = pstmt.executeQuery()) {

            // rs.next() advances the cursor to the next row (returns true if row exists)
            while (rs.next()) {
                // Extract column values from current row
                int empNo = rs.getInt("emp_no");
                String empName = rs.getString("emp_name");
                String email = rs.getString("email");
                String location = rs.getString("location");

                // Instantiate Employee Java object
                Employee employee = new Employee(empNo, empName, email, location);

                // Add to our list
                employeeList.add(employee);
            }
        }
        return employeeList;
    }

    // =========================================================================
    // 5. SELECT BY NON-PK COLUMN: Filtering by 'location' column (e.g. "Chicago")
    // =========================================================================
    public static List<Employee> selectEmployeesByLocation(Connection conn, String targetLocation) throws SQLException {
        List<Employee> filteredList = new ArrayList<>();
        // Querying on a NON-PRIMARY KEY column (location) using parameterized SQL
        String sql = "SELECT emp_no, emp_name, email, location FROM employees WHERE location = ? ORDER BY emp_no";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Bind the non-primary key column parameter
            pstmt.setString(1, targetLocation);

            try (ResultSet rs = pstmt.executeQuery()) {
                // Loop through all matching records for targetLocation
                while (rs.next()) {
                    int empNo = rs.getInt("emp_no");
                    String empName = rs.getString("emp_name");
                    String email = rs.getString("email");
                    String location = rs.getString("location");

                    Employee emp = new Employee(empNo, empName, email, location);
                    filteredList.add(emp);
                }
            }
        }
        return filteredList;
    }
}
