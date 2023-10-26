package com.example.backend.core.security.serivce;

import com.example.backend.core.model.Customer;

public interface CustomerSPService {
    Customer saveCustomer(Customer customer);
    Customer findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
