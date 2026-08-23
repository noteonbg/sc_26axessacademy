package com.standardchartered.banking.repository;

import com.standardchartered.banking.entity.BankUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankUserRepository extends JpaRepository<BankUserEntity, Long> {
    Optional<BankUserEntity> findByEmail(String email);
}
