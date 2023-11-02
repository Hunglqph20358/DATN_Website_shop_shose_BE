package com.example.backend.core.admin.repository;

import com.example.backend.core.admin.dto.DiscountDetailAdminDTO;
import com.example.backend.core.model.DiscountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DiscountDetailAdminRepository extends JpaRepository<DiscountDetail,Long> {

}
