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

    public List<EmailDto> getAllEmails() {
        Sort sort = Sort.by("id").ascending().and(
				Sort.by("subject").ascending()	
		);

        List<EmailEntity> emailList = emailRepository.findAll(sort);
        return emailList.stream().map(EmailDto::new).toList();
    }
}
