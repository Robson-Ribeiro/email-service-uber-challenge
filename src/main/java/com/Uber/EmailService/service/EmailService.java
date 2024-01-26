package com.Uber.EmailService.service;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public EmailService(EmailSenderAdapter adapter) {
        this.emailSenderAdapter = adapter;
    }

    public boolean sendEmail(EmailDto emailDto) {
        emailSenderAdapter.sendEmail(emailDto.getReceiverEmail(), emailDto.getSubject(), emailDto.getBody());
        EmailEntity emailEntity = new EmailEntity(emailDto);
        emailRepository.save(emailEntity);
        return true;
    }
}
