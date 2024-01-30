package com.Uber.EmailService.infrastructure.AmazonSES;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.Uber.EmailService.Exception.SendEmailException;
import com.Uber.EmailService.adapter.EmailSenderAdapter;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;

@Primary
@Service
public class AmazonEmailSender implements EmailSenderAdapter {
 
    private final AmazonSimpleEmailService amazonSesClient;

    @Autowired
    public AmazonEmailSender(AmazonSimpleEmailService amazonSesClient) {
        this.amazonSesClient = amazonSesClient;
    }

    @Override
    public void sendEmail(String receiverEmail, String subject, String body) {
        SendEmailRequest request = new SendEmailRequest()
            .withSource("noreply@gmail.com")
            .withDestination(new Destination().withToAddresses(receiverEmail))
            .withMessage(new Message()
                .withSubject(new Content(subject))
                .withBody(new Body().withText(new Content(body)))
            );

        try {
            this.amazonSesClient.sendEmail(request);
        } catch (AmazonServiceException e) {
            throw new SendEmailException("Unable to send the e-mail.", e);
        }
    }
}
