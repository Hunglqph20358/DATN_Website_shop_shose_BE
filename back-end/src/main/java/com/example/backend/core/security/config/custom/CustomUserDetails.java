package com.example.backend.core.security.config.custom;

import com.example.backend.core.model.Customer;
import com.example.backend.core.security.entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CustomUserDetails implements UserDetails {
    private Long id;
    private String code;
    private String fullname;
    private Date birthday;
    private String gender;
    private String address;
    private String phone;
    private String email;
    private String username;
    @JsonIgnore
    private String password;
    private String role;
    public Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null){
            return authorities;
        }
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    public CustomUserDetails(Long id, String code, String fullname, Date birthday, String gender, String username, String password, String phone, String role) {
        this.id = id;
        this.code = code;
        this.fullname = fullname;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static CustomUserDetails mapUserToUserDetail(Users users){
        return new CustomUserDetails(
                users.getId(),
                users.getCode(),
                users.getFullname(),
                users.getBirthday(),
                users.getGender(),
                users.getUsername(),
                users.getPassword(),
                users.getPhone(),
                users.getRole()
        );
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
