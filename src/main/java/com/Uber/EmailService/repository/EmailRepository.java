package com.Uber.EmailService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Uber.EmailService.entity.EmailEntity;

public interface EmailRepository extends JpaRepository<EmailEntity, Long> {

}
