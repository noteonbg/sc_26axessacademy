package com.example.employee.repository.impl;

import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * HashMap-backed implementation of EmployeeRepository.
 * Uses java.util.HashMap as underlying data storage keyed by empNo.
 */
public class EmployeeHashMapRepository implements EmployeeRepository {

    private final Map<Integer, Employee> employeeMap = new HashMap<>();

    @Override
    public void save(Employee employee) {
        employeeMap.put(employee.getEmpNo(), employee);
    }

    @Override
    public Optional<Employee> findById(int empNo) {
        return Optional.ofNullable(employeeMap.get(empNo));
    }

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(employeeMap.values());
    }

    @Override
    public boolean update(int empNo, String newEmpName, String newEmail, String newLocation) {
        Employee emp = employeeMap.get(empNo);
        if (emp != null) {
            emp.setEmpName(newEmpName);
            emp.setEmail(newEmail);
            emp.setLocation(newLocation);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(int empNo) {
        return employeeMap.remove(empNo) != null;
    }

    @Override
    public boolean existsById(int empNo) {
        return employeeMap.containsKey(empNo);
    }
}
