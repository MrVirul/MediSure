package com.progrp251.medisure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepository extends JpaRepository<com.progrp251.medisure.claim.Claim, Long> {
}
