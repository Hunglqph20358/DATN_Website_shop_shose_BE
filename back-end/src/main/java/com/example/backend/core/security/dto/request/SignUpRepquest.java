package com.example.backend.core.security.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRepquest {
    private String code;
    private String fullname;
    private String username;
    private String password;
    private String email;
    private String id_customer;
    private String id_staff ;
    private String role;
}
