
package com.progrp251.medisure.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping({"/", "/index"})
    public String home() {
        return "Home/index";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            @RequestParam(value = "signup", required = false) String signup,
                            Model model) {

        if (error != null) {
            model.addAttribute("error", "Invalid username or password!");
        }

        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }

        if (signup != null && signup.equals("success")) {
            model.addAttribute("message", "Registration successful! Please login.");
        }

        return "Home/login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("client", new Client());
        return "Home/signup";
    }
    @PostMapping("/signup")
    public String handleSignup(@RequestParam String fullname,
                               @RequestParam String email,
                               @RequestParam String phone,
                               @RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String confirmPassword,
                               Model model) {

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match!");
            return "Home/signup";
        }

        // Optional: uniqueness checks
        if (clientService.existsByUsername(username)) {
            model.addAttribute("error", "Username is already taken");
            return "Home/signup";
        }
        if (clientService.existsByEmail(email)) {
            model.addAttribute("error", "Email is already registered");
            return "Home/signup";
        }

        Client client = new Client();
        client.setName(fullname);           // was setFullName
        client.setEmail(email);
        client.setPhoneNumber(phone);       // was setPhone
        client.setUsername(username);
        client.setPassword(passwordEncoder.encode(password));
        client.setActive(true);
        if (client.getRole() == null) client.setRole("USER");

        try {
            clientService.save(client);
            return "redirect:/login?signup=success";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred during registration. Please try again.");
            return "Home/signup";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        String currentUsername = clientService.getCurrentUsername();
        Client currentClient = currentUsername != null
                ? clientService.findByUsername(currentUsername)
                : null;
        model.addAttribute("client", currentClient);
        return "Home/dashboard";
    }}