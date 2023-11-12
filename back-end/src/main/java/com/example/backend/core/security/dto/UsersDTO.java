package com.example.backend.core.security.dto;


import com.example.backend.core.security.config.custom.CustomUserDetails;
import com.example.backend.core.security.config.custom.CustomerUserDetails;
import lombok.*;

import java.util.Date;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class UsersDTO {
    private Long id;
    private String code;
    private String fullname;
    private Date birthday;
    private String gender;
    private String phone;
    private String email;
    private String username;
    private String isdn;
    private String role;
    public UsersDTO toStaffDTO(CustomUserDetails customUserDetails){
        this.setCode(customUserDetails.getCode());
        this.setFullname(customUserDetails.getFullname());
        this.setUsername(customUserDetails.getUsername());
        this.setEmail(customUserDetails.getEmail());
        this.setIsdn(customUserDetails.getIsdn());
        this.setRole(customUserDetails.getRole());
        return  UsersDTO.this;
    }
    public UsersDTO toCustomerDTO(CustomerUserDetails customerUserDetails){
        this.setFullname(customerUserDetails.getFullname());
        this.setUsername(customerUserDetails.getUsername());
        this.setEmail(customerUserDetails.getEmail());
        this.setPhone(customerUserDetails.getPhone());
        return  UsersDTO.this;
    }
}
