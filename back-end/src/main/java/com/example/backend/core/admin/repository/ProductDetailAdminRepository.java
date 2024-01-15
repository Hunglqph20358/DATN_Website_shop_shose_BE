package com.example.backend.core.admin.repository;

import com.example.backend.core.admin.dto.ProductDetailAdminDTO;
import com.example.backend.core.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProductDetailAdminRepository extends JpaRepository<ProductDetail, Long> {
    List<ProductDetail> findByIdProduct(Long idProduct);
}
