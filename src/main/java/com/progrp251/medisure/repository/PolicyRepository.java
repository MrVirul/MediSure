package com.progrp251.medisure.repository;

import com.progrp251.medisure.domain.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
}
