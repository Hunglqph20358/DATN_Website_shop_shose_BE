package com.example.backend.core.admin.controller;

import com.example.backend.core.security.config.custom.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class HelloController {
    @GetMapping("/home")
    public String hello(){
        return "Hello Word";
    }
}
