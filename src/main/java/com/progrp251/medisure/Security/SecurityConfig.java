package com.progrp251.medisure.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration // Indicates that this class contains Spring configuration
@EnableWebSecurity // Enables Spring Security's web security support
public class SecurityConfig {
    // Define public URLs that don't require authentication
    private static final String[] PUBLIC_URLS = {"/", "/login**", "/oauth2/**", "/auth/**"};
    // TODO: Add more public URLs as needed, for example:
    // TODO: add url (/register) for user registration
    // TODO: add url (/api/public/**) for public API endpoints
    // TODO: add url (/swagger-ui/**) for Swagger documentation
    // TODO: add url (/v3/api-docs/**) for OpenAPI documentation

    private static final String LOGOUT_SUCCESS_URL = "/"; // URL to redirect after logout

    // Dependencies for OAuth2 and JWT authentication
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;

    // Constructor injection of dependencies
    public SecurityConfig(
            CustomOAuth2UserService customOAuth2UserService,
            JwtAuthenticationFilter jwtAuthenticationFilter,
            OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.oauth2AuthenticationSuccessHandler = oauth2AuthenticationSuccessHandler;
    }

    // Configures the security filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS with custom configuration
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection (common for stateless APIs)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Use stateless sessions (no session cookies)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_URLS).permitAll() // Allow access to public URLs without authentication
                        .anyRequest().authenticated() // Require authentication for all other requests
                )
                // Add JWT authentication filter before the username/password authentication filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2 -> oauth2 // Configure OAuth2 login
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService) // Use custom OAuth2 user service
                        )
                        .successHandler(oauth2AuthenticationSuccessHandler) // Use custom success handler
                )
                .logout(logout -> logout // Configure logout
                        .logoutSuccessUrl(LOGOUT_SUCCESS_URL) // Redirect to home page after logout
                        .permitAll() // Allow everyone to access logout
                )
                .build();
    }

    // Configures CORS (Cross-Origin Resource Sharing) settings
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*")); // Allow all origins (consider restricting in production)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allow these HTTP methods
        configuration.setAllowedHeaders(Arrays.asList("*")); // Allow all headers
        configuration.setAllowCredentials(true); // Allow credentials (cookies, authorization headers)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply CORS configuration to all paths
        return source;
    }
}