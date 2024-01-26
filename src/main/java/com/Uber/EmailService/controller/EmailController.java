package com.Uber.EmailService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Uber.EmailService.dto.EmailDto;
import com.Uber.EmailService.service.EmailService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/email")
@CrossOrigin
public class EmailController {
    
    @Autowired
    private EmailService emailService;

    
    @PostMapping
    public ResponseEntity<String> sendEmail(@Valid @RequestBody EmailDto email) {
        try {
            emailService.sendEmail(email);
            return ResponseEntity.ok().body("Your e-mail has been sent successfully!");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.internalServerError().body("We were unable to send your email due to an error!");
        }
    }
}
