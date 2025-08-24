package com.progrp251.medisure;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.beans.BeanProperty;

@SpringBootApplication
public class MediSureApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediSureApplication.class, args);
    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
