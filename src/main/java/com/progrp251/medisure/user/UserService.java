package com.progrp251.medisure.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor injection instead of @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return userRepository.findById(id);
    }

    public User userCreation(@Valid User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ValidationException("Email already exists");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ValidationException("Username already exists");
        }

        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User userUpdate(Long id, @Valid User userDetails) {
        if (id == null || userDetails == null) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        return userRepository.findById(id)
                .map(user -> {
                    // Check if email is being changed and if it's already taken
                    if (!user.getEmail().equals(userDetails.getEmail()) &&
                            userRepository.findByEmail(userDetails.getEmail()).isPresent()) {
                        throw new ValidationException("Email already exists");
                    }

                    // Check if username is being changed and if it's already taken
                    if (!user.getUsername().equals(userDetails.getUsername()) &&
                            userRepository.findByUsername(userDetails.getUsername()).isPresent()) {
                        throw new ValidationException("Username already exists");
                    }

                    user.setUsername(userDetails.getUsername());
                    // Only update password if it's provided
                    if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
                    }
                    user.setEmail(userDetails.getEmail());
                    user.setRole(userDetails.getRole());
                    user.setEnabled(userDetails.isEnabled());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public void deleteUser(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}