package com.standardchartered.jpademo.repository;

import com.standardchartered.jpademo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository interface for Employee entity.
 * 
 * Provides built-in CRUD operations (save, findById, findAll, deleteById, count)
 * along with derived query methods.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Derived Query Method: Spring Data generates SQL "SELECT * FROM employees WHERE department = ?"
    List<Employee> findByDepartmentIgnoreCase(String department);

    // Derived Query Method: Find employee by email
    Optional<Employee> findByEmail(String email);
}
