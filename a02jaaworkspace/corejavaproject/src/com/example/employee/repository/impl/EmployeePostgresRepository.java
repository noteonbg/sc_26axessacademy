package com.example.employee.repository.impl;

import com.example.employee.config.DatabaseConfig;
import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * PostgreSQL Database Implementation of EmployeeRepository.
 * Demonstrates JDBC Persistence layer for Employee maintenance.
 */
public class EmployeePostgresRepository implements EmployeeRepository {

    /**
     * Initializes PostgreSQL repository and auto-creates table schema if missing.
     */
    public EmployeePostgresRepository() {
        try (Connection conn = getConnection()) {
            DatabaseConfig.initializeDatabase(conn);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize PostgreSQL Employee database schema: " + e.getMessage(), e);
        }
    }

    /**
     * Helper method to acquire a database connection.
     */
    protected Connection getConnection() throws SQLException {
        return DatabaseConfig.getConnection();
    }

    @Override
    public void save(Employee employee) {
        String sql = "INSERT INTO employees (emp_no, emp_name, email, location) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employee.getEmpNo());
            ps.setString(2, employee.getEmpName());
            ps.setString(3, employee.getEmail());
            ps.setString(4, employee.getLocation());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving employee to PostgreSQL database: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Employee> findById(int empNo) {
        String sql = "SELECT emp_no, emp_name, email, location FROM employees WHERE emp_no = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empNo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Employee emp = new Employee(
                            rs.getInt("emp_no"),
                            rs.getString("emp_name"),
                            rs.getString("email"),
                            rs.getString("location")
                    );
                    return Optional.of(emp);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error searching employee in PostgreSQL database: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT emp_no, emp_name, email, location FROM employees ORDER BY emp_no";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt("emp_no"),
                        rs.getString("emp_name"),
                        rs.getString("email"),
                        rs.getString("location")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all employees from PostgreSQL database: " + e.getMessage(), e);
        }
        return employees;
    }

    @Override
    public boolean update(int empNo, String newEmpName, String newEmail, String newLocation) {
        String sql = "UPDATE employees SET emp_name = ?, email = ?, location = ? WHERE emp_no = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newEmpName);
            ps.setString(2, newEmail);
            ps.setString(3, newLocation);
            ps.setInt(4, empNo);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating employee in PostgreSQL database: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteById(int empNo) {
        String sql = "DELETE FROM employees WHERE emp_no = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empNo);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting employee from PostgreSQL database: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existsById(int empNo) {
        String sql = "SELECT 1 FROM employees WHERE emp_no = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empNo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking employee existence in PostgreSQL database: " + e.getMessage(), e);
        }
    }
}
