package com.standardchartered.jpademo.config;

import com.standardchartered.jpademo.entity.Employee;
import com.standardchartered.jpademo.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Startup Data Initializer component to seed initial Employee records into the H2 Database.
 * 
 * Ensures default employee records exist automatically whenever the application starts up.
 */
@Component
public class JpaDemoDataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(JpaDemoDataInitializer.class);

    private final EmployeeRepository employeeRepository;

    public JpaDemoDataInitializer(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (employeeRepository.count() == 0) {
            log.info("Seeding initial Employee records into H2 Database...");

            Employee e1 = new Employee("Alice Johnson", "alice.johnson@sc.com", "IT", new BigDecimal("85000.00"), "Senior Developer");
            Employee e2 = new Employee("Bob Smith", "bob.smith@sc.com", "Finance", new BigDecimal("92000.00"), "Financial Analyst");
            Employee e3 = new Employee("Carol Williams", "carol.williams@sc.com", "HR", new BigDecimal("68000.00"), "HR Specialist");
            Employee e4 = new Employee("David Brown", "david.brown@sc.com", "IT", new BigDecimal("95000.00"), "Solutions Architect");
            Employee e5 = new Employee("Eva Martinez", "eva.martinez@sc.com", "Operations", new BigDecimal("88000.00"), "Operations Manager");
            Employee e6 = new Employee("Frank Miller", "frank.miller@sc.com", "Compliance", new BigDecimal("91000.00"), "Compliance Officer");

            List<Employee> savedEmployees = employeeRepository.saveAll(List.of(e1, e2, e3, e4, e5, e6));
            log.info("Successfully seeded {} default Employee records into EMPLOYEES table.", savedEmployees.size());
        } else {
            log.info("EMPLOYEES table already contains {} records. Skipping default seeding.", employeeRepository.count());
        }
    }
}
