package com.standardchartered.controllerdemo.controller;

import com.standardchartered.controllerdemo.exception.CustomerNotFoundException;
import com.standardchartered.controllerdemo.model.CustomerProfile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/*
 * SYNTAX COMMENTARY: REST Controller Architecture
 *
 * @RestController:
 * - Specialization of @Controller. Combines @Controller and @ResponseBody.
 * - Every method in this class returns domain objects/collections directly serialized to JSON/XML HTTP responses.
 *
 * @RequestMapping("/api/v1/customers"):
 * - Defines the base URL path for all endpoints in this controller class.
 */
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestController {

    private final List<CustomerProfile> customerList = new ArrayList<>();

    public CustomerRestController() {
        customerList.add(new CustomerProfile(1L, "Sandra", "Rogers", "sandra@bank.com", "SAVINGS", new BigDecimal("100000.00")));
        customerList.add(new CustomerProfile(2L, "Steve", "Casey", "steve@bank.com", "CURRENT", new BigDecimal("300000.00")));
    }

    /*
     * SYNTAX COMMENTARY: HTTP GET Operation for Retrieving Collections
     *
     * @GetMapping:
     * - Shortcut for @RequestMapping(method = RequestMethod.GET).
     * - Maps HTTP GET requests sent to "/api/v1/customers".
     */
    @GetMapping
    public List<CustomerProfile> getAllCustomers() {
        return customerList;
    }

    /*
     * SYNTAX COMMENTARY: Dynamic URL Path Variables & ResponseEntity
     *
     * @GetMapping("/{id}"):
     * - Defines a URI template variable "{id}". Example URL: GET /api/v1/customers/1
     *
     * @PathVariable("id") Long id:
     * - Binds the dynamic URI path segment "{id}" directly to the Java method parameter 'id'.
     *
     * ResponseEntity<T>:
     * - Wraps the response payload along with custom HTTP headers and status codes (e.g. 200 OK).
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerProfile> getCustomerById(@PathVariable("id") Long id) {
        CustomerProfile customer = customerList.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " does not exist"));

        return ResponseEntity.ok(customer);
    }

    /*
     * SYNTAX COMMENTARY: Query Parameters (@RequestParam)
     *
     * @RequestParam(name = "type", required = false) String type:
     * - Extracts URL query parameters. Example URL: GET /api/v1/customers/search?type=SAVINGS
     */
    @GetMapping("/search")
    public List<CustomerProfile> searchByAccountType(@RequestParam(name = "type", required = false, defaultValue = "SAVINGS") String type) {
        return customerList.stream()
                .filter(c -> c.getAccountType().equalsIgnoreCase(type))
                .toList();
    }

    /*
     * SYNTAX COMMENTARY: HTTP POST Operation & Body Deserialization
     *
     * @PostMapping:
     * - Maps HTTP POST requests for resource creation.
     *
     * @RequestBody CustomerProfile customer:
     * - Reads the inbound JSON HTTP request body and deserializes it into the Java 'CustomerProfile' object.
     *
     * ResponseEntity.status(HttpStatus.CREATED):
     * - Explicitly returns HTTP 201 Created status upon successful resource creation.
     */
    @PostMapping
    public ResponseEntity<CustomerProfile> createCustomer(@RequestBody CustomerProfile customer) {
        customer.setId((long) (customerList.size() + 1));
        customerList.add(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    /*
     * SYNTAX COMMENTARY: HTTP PUT Operation for Updating Resources
     *
     * @PutMapping("/{id}"):
     * - Maps HTTP PUT requests for updating existing resources at URI /api/v1/customers/1.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerProfile> updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerProfile updated) {
        CustomerProfile existing = customerList.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new CustomerNotFoundException("Cannot update: Customer ID " + id + " not found"));

        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setEmail(updated.getEmail());
        existing.setAccountType(updated.getAccountType());
        existing.setBalance(updated.getBalance());

        return ResponseEntity.ok(existing);
    }

    /*
     * SYNTAX COMMENTARY: HTTP DELETE Operation
     *
     * @DeleteMapping("/{id}"):
     * - Maps HTTP DELETE requests at URI /api/v1/customers/1.
     * - Returns HTTP 204 No Content indicating successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long id) {
        CustomerProfile existing = customerList.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new CustomerNotFoundException("Cannot delete: Customer ID " + id + " not found"));

        customerList.remove(existing);
        return ResponseEntity.noContent().build();
    }
}
