package com.Uber.EmailService.dto;

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
    private String from;
    @NotBlank
    private String to;
    @NotBlank
    private String subject;
    @NotBlank
    private String body;
}
