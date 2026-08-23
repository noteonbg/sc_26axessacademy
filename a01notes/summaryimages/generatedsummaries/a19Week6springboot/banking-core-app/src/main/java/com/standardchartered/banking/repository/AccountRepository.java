package com.standardchartered.banking.repository;

import com.standardchartered.banking.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    List<AccountEntity> findByCustomerId(Long customerId);

    // Custom JPQL Query finding accounts with balance above minimum
    @Query("SELECT a FROM AccountEntity a WHERE a.accountBalance >= :minBalance")
    List<AccountEntity> findHighNetWorthAccounts(@Param("minBalance") BigDecimal minBalance);
}
