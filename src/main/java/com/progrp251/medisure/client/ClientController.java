package com.progrp251.medisure.client;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {
    @GetMapping({"/", "/index"})
    public String home() {
        // Return the Thymeleaf template located at templates/Home/index.html
        return "Home/index";
    }

    @GetMapping("/login")
    public String loginPage() {
        // Return the Thymeleaf template located at templates/Home/login.html
        return "Home/login";
    }
}