package com.progrp251.medisure.repository;

import com.progrp251.medisure.domain.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
}
