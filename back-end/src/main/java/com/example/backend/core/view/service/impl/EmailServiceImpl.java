package com.example.backend.core.view.service.impl;

import com.example.backend.core.view.service.EmailService;
import jakarta.mail.MessagingException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendHtmlEmail(String to, String subject, String htmlBody) throws MessagingException {

    }
}
