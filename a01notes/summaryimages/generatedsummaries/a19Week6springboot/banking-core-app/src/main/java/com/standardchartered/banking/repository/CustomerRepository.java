package com.standardchartered.banking.repository;

import com.standardchartered.banking.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByEmail(String email);

    // Custom JPQL Query operating on Java Entity fields
    @Query("SELECT c FROM CustomerEntity c WHERE c.status = :status")
    List<CustomerEntity> findCustomersByStatusJpql(@Param("status") String status);

    // Native SQL Query operating directly on SQL table columns
    @Query(value = "SELECT * FROM customers WHERE email LIKE %:domain%", nativeQuery = true)
    List<CustomerEntity> findCustomersByEmailDomainNative(@Param("domain") String domain);
}
