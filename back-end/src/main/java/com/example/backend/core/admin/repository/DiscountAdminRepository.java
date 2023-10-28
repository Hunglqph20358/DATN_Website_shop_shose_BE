package com.example.backend.core.admin.repository;

import com.example.backend.core.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountAdminRepository extends JpaRepository<Discount,Long> {
}
