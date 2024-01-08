package com.example.backend.core.salesCounter.repository;

import com.example.backend.core.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSalesCouterRepository extends JpaRepository<Product, Long> {
}
