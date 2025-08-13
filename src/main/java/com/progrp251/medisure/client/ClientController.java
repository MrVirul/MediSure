package com.progrp251.medisure.client;

import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
//import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public Client addClient(@Valid @RequestBody ClientDTO clientDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new IllegalArgumentException(result.getFieldErrors().toString());
        }
        Client client = new Client();
        client.setNic(clientDTO.getNic());          // Now works!
        client.setName(clientDTO.getName());        // Now works!
        client.setPhoneNumber(clientDTO.getPhoneNumber()); // Now works!
        return clientService.addClient(client);
    }
}