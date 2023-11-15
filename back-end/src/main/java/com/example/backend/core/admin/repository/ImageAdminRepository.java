package com.example.backend.core.admin.repository;

import com.example.backend.core.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageAdminRepository extends JpaRepository<Images,Long> {
    List<Images> findByOrderById();

}
