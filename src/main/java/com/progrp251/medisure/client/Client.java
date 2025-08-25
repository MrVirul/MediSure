package com.progrp251.medisure.client;

import com.progrp251.medisure.claim.Claim;
import com.progrp251.medisure.policy.Policy;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.security.AuthProvider;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;  // Added missing import

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "clients")  // Changed to plural (convention)
public class Client {

    // Basic Information
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientID;
    private String name;
    private String nic;
    private LocalDate dateOfBirth;

    // Contact Details
    private String email;
    private String phoneNumber;
    private String address;

    // Authentication & Account
    private String username;
    private String password;
    private String role;
    private boolean active;

    //Fields to track the OAuth2 provider
    private String provider;
    private String providerId;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    public enum AuthProvider{
        local,
        google
    }
    // Insurance Details (Relationships)
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Policy> policies;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Claim> claims;
    // Metadata
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}