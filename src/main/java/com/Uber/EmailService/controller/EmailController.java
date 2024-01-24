package com.Uber.EmailService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Uber.EmailService.dto.EmailDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/email")
@CrossOrigin
public class EmailController {
    
    //@Autowired
    //private EmailService emailService;

    @PostMapping
    public ResponseEntity sendEmail(@Valid @RequestBody EmailDto email) {
        //emailService.sendEmail(email);
        return ResponseEntity.ok().body("Your email has been sent successfully!");
    }
}
