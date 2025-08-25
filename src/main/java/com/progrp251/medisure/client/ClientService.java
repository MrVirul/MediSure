package com.progrp251.medisure.client;

import com.progrp251.medisure.policy.PolicyService;

public class ClientService {
    private final PolicyService policyService;

    public ClientService(PolicyService policyService) {
        this.policyService = policyService;
    }

}
