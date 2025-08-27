package com.progrp251.medisure.Security.oauth;

import com.progrp251.medisure.user.User;
import com.progrp251.medisure.user.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // e.g., "google"
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // Extract common attributes (Google specifics handled here)
        String email = (String) attributes.getOrDefault("email", null);
        String name = (String) attributes.getOrDefault("name", null);
        String picture = (String) attributes.getOrDefault("picture", null);

        if (email != null) {
            // Upsert user by email
            User user = userRepository.findByEmail(email).orElseGet(() -> {
                User u = new User();
                u.setEmail(email);
                // Create a username from email prefix; ensure uniqueness if needed
                String baseUsername = email.contains("@") ? email.substring(0, email.indexOf('@')) : email;
                String usernameCandidate = baseUsername;
                int counter = 1;
                while (userRepository.findByUsername(usernameCandidate).isPresent()) {
                    usernameCandidate = baseUsername + counter;
                    counter++;
                }
                u.setUsername(usernameCandidate);
                // Set a random encoded password (OAuth users don't use local password by default)
                u.setPassword(new BCryptPasswordEncoder().encode(UUID.randomUUID().toString()));
                // Default role for new OAuth users
                u.setRole(User.Role.Sales_Officer);
                u.setEnabled(true);
                return u;
            });

            // Update profile info
            if (name != null) {
                user.setFullName(name);
            }
            if (picture != null) {
                user.setPictureUrl(picture);
            }

            userRepository.save(user);
        }

        // Build authorities (simple ROLE_USER for OAuth principal view)
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        // Use "email" as the name attribute key if present; fallback to sub
        String nameAttributeKey = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        if (nameAttributeKey == null || !attributes.containsKey(nameAttributeKey)) {
            nameAttributeKey = attributes.containsKey("email") ? "email" : "sub";
        }

        return new DefaultOAuth2User(authorities, attributes, nameAttributeKey);
    }
}
