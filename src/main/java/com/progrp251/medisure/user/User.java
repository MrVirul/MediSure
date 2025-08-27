package com.progrp251.medisure.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
public class User {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String username;

    @Getter
    @Setter
    @Column(nullable = false)
    private String password;

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    // New fields for OAuth2 profile data
    @Getter
    @Setter
    @Column(name = "full_name")
    private String fullName; // nullable

    @Getter
    @Setter
    @Column(name = "picture_url", length = 1000)
    private String pictureUrl; // nullable

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Role role;

    @Getter
    @Setter
    private boolean enabled = true;

    public enum Role {
        Insurance_Operation_Manager,
        Customer_Support_Service,
        Claims_Office,
        Finance_Executive,
        Sales_Officer,
        Medical_Records_Coordinator
    }

}
