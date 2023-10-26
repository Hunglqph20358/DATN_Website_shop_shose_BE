package com.example.backend.core.security.serivce.impl;

import com.example.backend.core.security.entity.Users;
import com.example.backend.core.security.repositories.UserRepository;
import com.example.backend.core.security.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UsersServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public Users findByUsername(String userName) {
        return repository.findByUsername(userName);
    }

    @Override
    public boolean existsByUsername(String userName) {
        return repository.existsByUsername(userName);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return repository.existsByPhone(phone);
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
