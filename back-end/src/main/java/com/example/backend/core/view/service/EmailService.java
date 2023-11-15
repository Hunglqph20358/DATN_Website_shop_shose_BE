package com.example.backend.core.view.service;


import jakarta.mail.MessagingException;

public interface EmailService {
    void sendHtmlEmail(String to, String subject, String htmlBody) throws MessagingException;
}
