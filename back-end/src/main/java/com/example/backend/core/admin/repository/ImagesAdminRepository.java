package com.example.backend.core.admin.repository;

import com.example.backend.core.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ImagesAdminRepository extends JpaRepository<Images, Long> {
    List<Images> findByIdProduct(Long idProduct);
}
