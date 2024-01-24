package com.Uber.EmailService.adapter;

public interface EmailSenderAdapter {
    public void sendEmail(String from, String to, String subject, String body);
}
