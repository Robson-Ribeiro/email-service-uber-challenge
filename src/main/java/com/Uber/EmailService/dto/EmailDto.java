package com.Uber.EmailService.dto;

import org.springframework.beans.BeanUtils;

import com.Uber.EmailService.entity.EmailEntity;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class EmailDto {
    
    private Long id;

    @NotBlank
    private String receiverEmail;
    @NotBlank
    private String subject;
    @NotBlank
    private String body;

    public EmailDto(EmailEntity email) {
        BeanUtils.copyProperties(email, this);
    }
}
