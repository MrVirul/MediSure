
package com.progrp251.medisure.Security.service;

import com.progrp251.medisure.client.Client;
import com.progrp251.medisure.client.ClientRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final ClientRepository clientRepository;

    public CustomOAuth2UserService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        // Extract user information
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        // Find or create a client
        Client client = clientRepository.findByEmail(email)
                .orElseGet(() -> {
                    Client newClient = new Client();
                    newClient.setEmail(email);
                    newClient.setName(name);
                    return clientRepository.save(newClient);
                });

        return oauth2User;
    }
}