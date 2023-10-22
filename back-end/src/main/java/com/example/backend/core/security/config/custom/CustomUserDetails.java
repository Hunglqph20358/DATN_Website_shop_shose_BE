package com.example.backend.core.security.config.custom;

import com.example.backend.core.security.entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private Instant birthday;
    private String gender;
    private String address;
    private String phone;
    private String username;
    @JsonIgnore
    private String password;

//    private Collection<? extends GrantedAuthority> role;
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    public CustomUserDetails(Long id, String code, String fullname, Instant birthday, String gender, String username, String password, String phone, String role) {
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
//        Set<SimpleGrantedAuthority> rolesaa  = ;
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
