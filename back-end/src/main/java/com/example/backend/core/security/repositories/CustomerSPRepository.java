package com.example.backend.core.security.repositories;

import com.example.backend.core.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerSPRepository extends JpaRepository<Customer,Long> {
    Customer findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
