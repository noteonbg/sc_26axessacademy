package com.standardchartered.jpademo.repository;

import com.standardchartered.jpademo.entity.BankCustomerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
 * SYNTAX COMMENTARY: Spring Data JPA Repositories & Custom Queries
 *
 * JpaRepository<BankCustomerJpaEntity, Long>:
 * - Provides generic out-of-the-box CRUD methods (save, findById, findAll, deleteById, count) without writing SQL implementation code.
 *
 * @Repository:
 * - Marks this interface as a Data Access Layer component and enables exception translation (converting SQL exceptions to DataAccessException).
 */
@Repository
public interface BankCustomerJpaRepository extends JpaRepository<BankCustomerJpaEntity, Long> {

    /*
     * SYNTAX COMMENTARY: Derived Finder Method Names
     *
     * Optional<BankCustomerJpaEntity> findByEmail(String email):
     * - Spring Data JPA automatically parses the method name ("findBy" + field "Email") and generates SQL:
     *   SELECT * FROM customers WHERE email = ?
     */
    Optional<BankCustomerJpaEntity> findByEmail(String email);

    /*
     * SYNTAX COMMENTARY: Custom Query using JPQL (Java Persistence Query Language)
     *
     * @Query("SELECT c FROM BankCustomerJpaEntity c WHERE c.status = :status"):
     * - JPQL operates on Java Entity classes and fields (c.status), NOT database SQL tables/columns.
     * - Portable across different RDBMS vendors (PostgreSQL, H2, Oracle, MySQL).
     */
    @Query("SELECT c FROM BankCustomerJpaEntity c WHERE c.status = :status")
    List<BankCustomerJpaEntity> findByStatusJpql(@Param("status") String status);

    /*
     * SYNTAX COMMENTARY: Custom Query using Native SQL
     *
     * @Query(value = "SELECT * FROM customers WHERE email LIKE %:domain%", nativeQuery = true):
     * - Executes raw SQL directly against the underlying database engine.
     * - Used when leveraging DB-specific features or performance optimizations.
     */
    @Query(value = "SELECT * FROM customers WHERE email LIKE %:domain%", nativeQuery = true)
    List<BankCustomerJpaEntity> findByEmailDomainNative(@Param("domain") String domain);
}
