package com.example.backend.core.view.repository;

import com.example.backend.core.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByCode(String code);
}
