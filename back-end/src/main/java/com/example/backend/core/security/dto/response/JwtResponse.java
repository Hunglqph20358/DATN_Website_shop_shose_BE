package com.example.backend.core.security.dto.response;

import com.example.backend.core.security.config.custom.CustomUserDetails;
import com.example.backend.core.security.dto.UsersDTO;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.Instant;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String userName;
    private String role;
    private String code;
    private String phone;
    private String email;

    public JwtResponse(String token, String username, String role, String code, String phone,String email) {
        this.token = token;
        this.userName = username;
        this.role = role;
        this.code = code;
        this.phone = phone;
        this.email = email;
    }
}
