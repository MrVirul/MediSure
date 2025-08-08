package com.progrp251.medisure.client;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientDTO {
    @Pattern(regexp = "^([0-9]{9}[vVxX]|[0-9]{12})$")
    private String nic;

    @NotBlank
    private String name;

    @Pattern(regexp = "^0[1-9][0-9]{8}$")
    private String phoneNumber;

    @NotBlank
    private String address;

    @Past
    private LocalDate dateOfBirth;

    @Email
    private String email;

    // Getters and Setters (omitted for brevity)
}