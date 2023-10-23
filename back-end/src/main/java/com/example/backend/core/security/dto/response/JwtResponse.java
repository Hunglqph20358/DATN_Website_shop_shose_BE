package com.example.backend.core.security.dto.response;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

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

    public JwtResponse(String token, String userName, String role) {
        this.token = token;
        this.userName = userName;
        this.role = role;
    }
}
