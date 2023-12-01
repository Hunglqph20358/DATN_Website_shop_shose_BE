package com.example.backend.core.admin.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerAdminDTO {
    private Long id;
    private String code;
    private String fullname;
    private String email;
    private Date birthday;
    private String phone;
    private String gender;
    private String username;
    private String password;
    private Instant createDate;
    private Instant updateDate;
    private Integer status;
    private Integer idel;
}
