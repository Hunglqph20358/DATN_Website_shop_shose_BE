package com.example.backend.core.view.service.impl;

import com.example.backend.core.view.dto.OrderDTO;
import com.example.backend.core.view.service.EmailService;
import jakarta.mail.MessagingException;


import jakarta.mail.internet.MimeMessage;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;


@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender javaMailSender, MessageSource messageSource, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }


    public void sendHtmlEmail(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
        helper.setFrom("hunglqph20358@fpt.edu.vn");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        javaMailSender.send(message);
    }

    @Override
    @Async
    public void sendMessageUsingThymeleafTemplate(String to, String subject, OrderDTO orderDTO) throws MessagingException {
        Context thymeleafContext = new Context();

        thymeleafContext.setVariable("order", orderDTO);
        String htmlBody = templateEngine.process("sendEmailOrder.html", thymeleafContext);
        sendHtmlEmail(to, subject, htmlBody);
    }
}
