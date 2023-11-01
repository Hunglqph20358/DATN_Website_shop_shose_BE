package com.example.backend.core.security.config.custom;

import com.example.backend.core.model.Customer;
import com.example.backend.core.security.entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private Long id;
    private String code;
    private String fullname;
    private Integer id_customer;
    private Integer id_staff;
    private String email;
    private String username;
    @JsonIgnore
    private String password;

//    private Collection<? extends GrantedAuthority> role;
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role.isEmpty()){
            return Collections.emptyList();
        }
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    public CustomUserDetails(Long id, String fullname, Integer id_customer, Integer id_staff, String email, String username, String password, String role) {
        this.id = id;
        this.fullname = fullname;
        this.id_customer = id_customer;
        this.id_staff = id_staff;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public static CustomUserDetails mapUserToUserDetail(Users users){
        return new CustomUserDetails(
                users.getId(),
                users.getFullname(),
                users.getId_customer(),
                users.getId_staff(),
                users.getEmail(),
                users.getUsername(),
                users.getPassword(),
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
