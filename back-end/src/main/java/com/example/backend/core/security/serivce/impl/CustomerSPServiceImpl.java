package com.example.backend.core.security.serivce.impl;

import com.example.backend.core.model.Customer;
import com.example.backend.core.security.repositories.CustomerSPRepository;
import com.example.backend.core.security.serivce.CustomerSPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerSPServiceImpl implements CustomerSPService {
    @Autowired
    private CustomerSPRepository repository;
    @Override
    public Customer saveCustomer(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public Customer findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
