package com.progrp251.medisure.client;

import com.progrp251.medisure.claim.Claim;
import com.progrp251.medisure.policy.Policy;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Client {
    @Id
    //Basic Information
    private Long clientID;
    private String firstName;
    private String lastName;
    private String nic;
    private LocalDate dateOfBirth;

    //Contact Details
    private String email;
    private String phoneNumber;
    private String address;

    //Authentication & Account
    private String username;
    private String password;
    private String role;
    private boolean active;

    //Insurance Details (Relationships)
    //one to many(mappedBy = client)
    private List<Policy> policies;

    //one to many
    private  List<Claim> claims;

    //metadata
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



}