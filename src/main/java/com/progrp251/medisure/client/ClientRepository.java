package com.progrp251.medisure.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByProviderId(String providerId);
    Optional<Client> findByEmail(String email);
    Optional<Client> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.policies LEFT JOIN FETCH c.claims WHERE c.username = :username")
    Optional<Client> findByUsernameWithPoliciesAndClaims(@Param("username") String username);
}