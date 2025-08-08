package com.progrp251.medisure.client;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^([0-9]{9}[vVxX]|[0-9]{12})$", message = "Invalid NIC format")
    private String nic;

    @NotBlank(message = "Name is required")
    private String name;

    @Pattern(regexp = "^0[1-9][0-9]{8}$", message = "Invalid Sri Lankan phone number")
    private String phoneNumber;

    @NotBlank
    private String address;

    @Past
    private LocalDate dateOfBirth;

    @Email
    private String email;

    private String gender;
    private String bloodGroup;
}