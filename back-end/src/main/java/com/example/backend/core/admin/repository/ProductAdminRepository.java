package com.example.backend.core.admin.repository;

import com.example.backend.core.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface ProductAdminRepository extends JpaRepository<Product, Long> {
}
