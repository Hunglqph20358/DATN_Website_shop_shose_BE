package com.example.backend.core.security.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UsersDTO {
    private Long id;
    private String code;
    private String fullname;
    private Date birthday;
    private String gender;
    private String phone;
    private String email;
    private String username;
    private String role;
    private String isdn;
}
