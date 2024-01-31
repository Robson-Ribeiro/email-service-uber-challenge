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

    public List<EmailDto> getAllEmails() {
        Sort sort = Sort.by("id").ascending().and(
                Sort.by("subject").ascending()
            );

        List<EmailEntity> emailList = emailRepository.findAll(sort);
        return emailList.stream().map(EmailDto::new).toList();
    }
}
