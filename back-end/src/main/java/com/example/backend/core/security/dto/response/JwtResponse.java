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
    private UsersDTO usersDTO;

    public JwtResponse(String token, UsersDTO toUserDTO) {
        this.token = token;
        this.usersDTO = toUserDTO;
    }
}
