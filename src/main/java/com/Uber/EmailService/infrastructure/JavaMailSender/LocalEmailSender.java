package com.Uber.EmailService.infrastructure.JavaMailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.Uber.EmailService.adapter.EmailSenderAdapter;

@Service
public class LocalEmailSender implements EmailSenderAdapter {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String receiverEmail, String subject, String body) {
        var message = new SimpleMailMessage();
        message.setFrom("myemail@email.com");
        message.setTo(receiverEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
