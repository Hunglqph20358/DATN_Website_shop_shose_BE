package com.example.backend.core.security.serivce.impl;

import com.example.backend.core.admin.repository.StaffRepository;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Customer;
import com.example.backend.core.model.Staff;
import com.example.backend.core.security.dto.request.SignUpRepquest;
import com.example.backend.core.security.entity.Users;
import com.example.backend.core.security.repositories.CustomerLoginRepository;
import com.example.backend.core.security.repositories.UserRepository;
import com.example.backend.core.security.serivce.UserService;
import com.example.backend.core.view.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
@Service
public class UsersServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private CustomerLoginRepository customerRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Users findByUsername(String userName) {
        return repository.findByUsername(userName);
    }

    @Override
    public boolean existsByUsername(String userName) {
        return repository.existsByUsername(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public String findByRole(String role) {
        return repository.findByRole(role);
    }

    @Override
    public Users saveOrUpdate(Users users) {
        return repository.save(users);
    }

    @Override
    public boolean isUser(String username) {
        Users users = repository.findByUsername(username);
        if (users.getRole()==null){
            return false;
        }
        return true;
    }


}
