package com.example.employee.repository.impl;

import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * ArrayList-backed implementation of EmployeeRepository.
 * Uses java.util.ArrayList as underlying data storage.
 */
public class EmployeeArrayListRepository implements EmployeeRepository {

    private final List<Employee> employeeList = new ArrayList<>();

    @Override
    public void save(Employee employee) {
        employeeList.add(employee);
    }

    @Override
    public Optional<Employee> findById(int empNo) {
        return employeeList.stream()
                .filter(e -> e.getEmpNo() == empNo)
                .findFirst();
    }

    @Override
    public List<Employee> findAll() {
        // Return an unmodifiable snapshot or copy to prevent external unintended mutation
        return new ArrayList<>(employeeList);
    }

    @Override
    public boolean update(int empNo, String newEmpName, String newEmail, String newLocation) {
        Optional<Employee> empOptional = findById(empNo);
        if (empOptional.isPresent()) {
            Employee emp = empOptional.get();
            emp.setEmpName(newEmpName);
            emp.setEmail(newEmail);
            emp.setLocation(newLocation);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(int empNo) {
        return employeeList.removeIf(e -> e.getEmpNo() == empNo);
    }

    @Override
    public boolean existsById(int empNo) {
        return employeeList.stream().anyMatch(e -> e.getEmpNo() == empNo);
    }
}
