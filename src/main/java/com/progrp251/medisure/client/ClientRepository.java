package com.progrp251.medisure.client;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> { // Long instead of Integer
    Optional<Client> findByProviderId(String providerId);
    Optional<Client> findByEmail(String email);
    Optional<Client> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}