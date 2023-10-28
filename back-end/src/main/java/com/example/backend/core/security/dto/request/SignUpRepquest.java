package com.example.backend.core.security.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRepquest {
    private String code;
    private String fullname;
    private String birthday;
    private String gender;
    private String address;
    private String phone;
    private String email;
    private String username;
    private String password;
    private String role;
}
