package com.progrp251.medisure.client;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client addClient(Client client) {
        if (clientRepository.existsByNic(client.getNic())) {
            throw new IllegalArgumentException("NIC already exists");
        }
        return clientRepository.save(client);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
}