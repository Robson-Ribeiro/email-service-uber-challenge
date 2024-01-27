package com.Uber.EmailService.Exception;

public class SendEmailException extends RuntimeException {

    public SendEmailException(String message) {
        super(message);
    }
    
    public SendEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
