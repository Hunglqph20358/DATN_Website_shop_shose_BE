package com.example.backend.core.view.service;


import com.example.backend.core.view.dto.OrderDTO;
import jakarta.mail.MessagingException;

import java.util.Map;

public interface EmailService {
//    void sendHtmlEmail(String to, String subject, String htmlBody) throws MessagingException;

    void sendMessageUsingThymeleafTemplate(OrderDTO orderDTO) throws MessagingException;
    void sendMailOrderNotLogin(OrderDTO orderDTO) throws MessagingException;
}
