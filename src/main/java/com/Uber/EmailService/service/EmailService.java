package com.Uber.EmailService.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

    private EmailSenderAdapter emailSenderAdapter;
    private EmailSenderAdapter secondaryEmailSenderAdapter;

    @Autowired
    public EmailService(EmailSenderAdapter adapter, EmailSenderAdapter adapter2) {
        this.emailSenderAdapter = adapter;
        this.secondaryEmailSenderAdapter = adapter2;
    }

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
