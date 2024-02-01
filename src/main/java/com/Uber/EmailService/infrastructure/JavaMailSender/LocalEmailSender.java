package com.Uber.EmailService.infrastructure.JavaMailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.Uber.EmailService.adapter.EmailSenderAdapter;

/**
 * Classe utilizada para fazer uma ponte entre o sistema e o JavaMailSender. Por meio dela é possível utilizar o JavaMailSender de maneira abstraída,
 * desacoplando o nosso programa e o tornado mais fácil de se trabalhar e alterar.
 */

@Component("localEmailSender")
public class LocalEmailSender implements EmailSenderAdapter {
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Método responsável por enviar um e-mail através do JavaMailSender.
     * @param receiverEmail Recebe um o endereço de e-mail que corresponde ao destinatário.
     * @param subject Corresponde ao assunto tratado no e-mail.
     * @param body É o texto principal do e-mail.
     */

    public void sendEmail(String receiverEmail, String subject, String body) {
        var message = new SimpleMailMessage();
        message.setFrom("noreply@email.com");
        message.setTo(receiverEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
