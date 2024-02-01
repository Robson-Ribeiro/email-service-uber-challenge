package com.Uber.EmailService.infrastructure.AmazonSES;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Uber.EmailService.Exception.SendEmailException;
import com.Uber.EmailService.adapter.EmailSenderAdapter;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;

/**
 * Classe utilizada para fazer uma ponte entre o sistema e o cliente do Amazon SES. Por meio dela é possível utilizar o SES de maneira abstraída,
 * desacoplando o nosso programa e o tornado mais fácil de se trabalhar e alterar.
 */

@Component("amazonEmailSender")
public class AmazonEmailSender implements EmailSenderAdapter {
 
    private final AmazonSimpleEmailService amazonSesClient;

    @Autowired
    public AmazonEmailSender(AmazonSimpleEmailService amazonSesClient) {
        this.amazonSesClient = amazonSesClient;
    }

    /**
     * Método responsável por enviar um e-mail através do AmazonSimpleEmailService.
     * @param receiverEmail Recebe um o endereço de e-mail que corresponde ao destinatário.
     * @param subject Corresponde ao assunto tratado no e-mail.
     * @param body É o texto principal do e-mail.
     */
    
    @Override
    public void sendEmail(String receiverEmail, String subject, String body) {
        SendEmailRequest request = new SendEmailRequest()
            .withSource("robsonribeirorra@gmail.com")
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
