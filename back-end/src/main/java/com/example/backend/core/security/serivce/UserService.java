package com.example.backend.core.security.serivce;

import com.example.backend.core.security.entity.ERole;
import com.example.backend.core.security.entity.Users;

import java.util.Optional;

public interface UserService {
    public Users findByUsername(String userName);
    boolean existsByUsername(String userName);
    boolean existsByPhone(String phone);
    String findByRole(String role);
    Users saveOrUpdate(Users users);
}
