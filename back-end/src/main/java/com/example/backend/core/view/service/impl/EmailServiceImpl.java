package com.example.backend.core.view.service.impl;

import com.example.backend.core.model.Customer;
import com.example.backend.core.view.dto.OrderDTO;
import com.example.backend.core.view.dto.OrderDetailDTO;
import com.example.backend.core.view.repository.CustomerRepository;
import com.example.backend.core.view.service.EmailService;
import com.example.backend.core.view.service.OrderDetailService;
import jakarta.mail.MessagingException;


import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;
import java.util.Map;


@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CustomerRepository customerRepository;

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
    public void sendMessageUsingThymeleafTemplate(OrderDTO orderDTO) throws MessagingException {
        Context thymeleafContext = new Context();

        Customer customer = customerRepository.findById(orderDTO.getIdCustomer()).get();
        String emailTo = customer.getEmail();
        String subject =  " Thông tin đơn hàng";
        List<OrderDetailDTO> list = orderDetailService.getAllByOrder(orderDTO.getId());
        thymeleafContext.setVariable("order", orderDTO);
        thymeleafContext.setVariable("orderDetail", list);
        String htmlBody = templateEngine.process("sendEmailOrder", thymeleafContext);
        sendHtmlEmail(emailTo, subject, htmlBody);
    }

    @Override
    @Async
    public void sendMailOrderNotLogin(OrderDTO orderDTO) throws MessagingException {
        Context thymeleafContext = new Context();
        String emailTo = orderDTO.getEmail();
        String subject =  " Thông tin đơn hàng";
        List<OrderDetailDTO> list = orderDetailService.getAllByOrder(orderDTO.getId());
        thymeleafContext.setVariable("order", orderDTO);
        thymeleafContext.setVariable("orderDetail", list);
        String htmlBody = templateEngine.process("sendMailOrderNotLogin", thymeleafContext);
        sendHtmlEmail(emailTo, subject, htmlBody);
    }
}
