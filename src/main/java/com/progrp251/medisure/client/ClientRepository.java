package com.progrp251.medisure.client;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByNic(String nic);
    boolean existsByNic(String nic);
    boolean existsByPhoneNumber(String phoneNumber);
}