package com.Uber.EmailService.entity;

import org.springframework.beans.BeanUtils;

import com.Uber.EmailService.dto.EmailDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "Emails")
public class EmailEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String from;
    @Column(nullable = false)
    private String to;
    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private String body;

    public EmailEntity(EmailDto email) {
        BeanUtils.copyProperties(email, this);
    }

}
