package com.Uber.EmailService.adapter;

public interface EmailSenderAdapter {
    public void sendEmail(String receiverEmail, String subject, String body);
}
