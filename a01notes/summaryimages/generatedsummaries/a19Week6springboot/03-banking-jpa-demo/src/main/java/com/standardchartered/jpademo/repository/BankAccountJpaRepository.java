package com.standardchartered.jpademo.repository;

import com.standardchartered.jpademo.entity.BankAccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BankAccountJpaRepository extends JpaRepository<BankAccountJpaEntity, Long> {

    List<BankAccountJpaEntity> findByCustomerId(Long customerId);

    @Query("SELECT a FROM BankAccountJpaEntity a WHERE a.accountBalance >= :minBalance")
    List<BankAccountJpaEntity> findHighNetWorthAccounts(@Param("minBalance") BigDecimal minBalance);
}
