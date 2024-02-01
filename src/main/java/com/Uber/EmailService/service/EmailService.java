package com.Uber.EmailService.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.Uber.EmailService.adapter.EmailSenderAdapter;
import com.Uber.EmailService.dto.EmailDto;
import com.Uber.EmailService.entity.EmailEntity;
import com.Uber.EmailService.repository.EmailRepository;

/**
* Classe responsável por realizar a lógica de negócio necessária para atender os controllers.
*/

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    @Qualifier("amazonEmailSender")
    private EmailSenderAdapter emailSenderAdapter;

    @Autowired
    @Qualifier("localEmailSender")
    private EmailSenderAdapter secondaryEmailSenderAdapter;

    /**
     * Método responsável por utilizar EmailSenderAdapters para enviar um e-mail, além de também fazer uso do EmailRepository para salvar os e-mails enviados.
     * Caso um EmailSernderAdapter não consiga funcionar corretamente, o sistema automaticamente usa o outro para cobrir a falha.
     * @param emailDto Recebe um EmailDto.
     * @return Retorna um boolean que indica o sucesso ou fracasso da operação.
     */

    public boolean sendEmail(EmailDto emailDto) {
        try {
            emailSenderAdapter.sendEmail(emailDto.getReceiverEmail(), emailDto.getSubject(), emailDto.getBody());
            EmailEntity emailEntity = new EmailEntity(emailDto);
            emailRepository.save(emailEntity);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            try {
                secondaryEmailSenderAdapter.sendEmail(emailDto.getReceiverEmail(), emailDto.getSubject(), emailDto.getBody());
                EmailEntity emailEntity = new EmailEntity(emailDto);
                emailRepository.save(emailEntity);
                return true;
            } catch (Exception ex) {
                System.out.println(ex);
                return false;
            }
        }
    }

    /**
     * Método responsável por retornar todos os e-mails salvos na base de dados de maneira ordenada.
     */

    public List<EmailDto> getAllEmails() {
        Sort sort = Sort.by("id").ascending().and(
                Sort.by("subject").ascending()
            );

        List<EmailEntity> emailList = emailRepository.findAll(sort);
        return emailList.stream().map(EmailDto::new).toList();
    }
}
