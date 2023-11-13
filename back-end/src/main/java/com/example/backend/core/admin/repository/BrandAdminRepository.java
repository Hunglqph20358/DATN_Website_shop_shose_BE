package com.example.backend.core.admin.repository;

import com.example.backend.core.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandAdminRepository extends JpaRepository<Brand,Long> {
}
