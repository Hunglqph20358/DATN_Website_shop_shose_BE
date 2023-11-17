package com.example.backend.core.view.controller;

import com.example.backend.core.view.dto.OrderDTO;
import com.example.backend.core.view.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/view/api")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/completePurchase")
    public ResponseEntity<String> completePurchase(@RequestBody OrderDTO orderDTO) {
        try {
            emailService.sendMessageUsingThymeleafTemplate("lehung10082003@gmail.com", "Thông tin đơn hàng",orderDTO);
            return ResponseEntity.ok("Purchase completed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}
