package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.repository.CustomerRepository;
import com.example.backend.core.admin.service.CustomerService;
import com.example.backend.core.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository repository;
    @Override
    public Optional<Customer> findById(String id) {
        return repository.findById(Long.valueOf(id));
    }
}
