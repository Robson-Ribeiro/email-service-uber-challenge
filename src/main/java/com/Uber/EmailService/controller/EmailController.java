package com.Uber.EmailService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Uber.EmailService.dto.EmailDto;
import com.Uber.EmailService.service.EmailService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;

/**
* Classe voltada para definir as funcionalidades que atendem as requisições destinadas à rota "/email"
*/

@RestController
@RequestMapping(value = "/email")
@CrossOrigin
public class EmailController {
    
    @Autowired
    private EmailService emailService;

    /**
     * Método responsável por realizar o envio de um e-mail de acordo com os dados informados ao sistema.
     * @param email Representa um email que deverá ser enviado. Esse e-mail apresenta um destinatário(receiverEmail), um assunto(subject) e um corpo(body).
     */
    
    @PostMapping
    public ResponseEntity<String> sendEmail(@Valid @RequestBody EmailDto email) {
            var isSuccess = emailService.sendEmail(email);
            if(isSuccess) {
                return ResponseEntity.ok().body("Your e-mail has been sent successfully!");
            }
            return ResponseEntity.internalServerError().body("We were unable to send your e-mail due to an error!");
    }

    /**
     * Método responsável por retornar para o navegador todos os e-mails salvos na base de dados.
     */

    @GetMapping
    public ResponseEntity getAllEmails() {
        try {
            return ResponseEntity.ok().body(emailService.getAllEmails());
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("We were unable to get the e-mails!");
        }
    }
}
