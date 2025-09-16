package com.progrp251.medisure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<com.progrp251.medisure.policy.Policy, Long> {
}
