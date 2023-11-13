package com.example.backend.core.admin.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffDTO {
    private Long id;
    private String code;
    private String fullname;
    private Instant birthday;
    private String gender;
    private String address;
    private String phone;
    private Instant createDate;
    private String description;
    private String username;
    private String password;
    private String role;
    private Integer status;
    private Integer idel;
    private String isdn;
}
