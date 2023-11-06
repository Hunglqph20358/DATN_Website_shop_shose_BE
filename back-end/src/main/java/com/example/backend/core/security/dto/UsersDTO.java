package com.example.backend.core.security.dto;

import com.example.backend.core.security.config.custom.CustomUserDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UsersDTO {
    private Long id;
    private String username;
    private String email;
    private Integer id_customer;
    private Integer id_staff ;
    private String role;

}
