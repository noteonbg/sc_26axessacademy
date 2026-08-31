package com.standardchartered.jwtdemo.repository;

import com.standardchartered.jwtdemo.entity.JwtUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtUserRepository extends JpaRepository<JwtUserEntity, Long> {
    Optional<JwtUserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}
