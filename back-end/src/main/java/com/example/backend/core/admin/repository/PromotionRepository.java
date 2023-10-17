package com.example.backend.core.admin.repository;
import com.example.backend.core.model.PromotionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<PromotionDetail,Long> {
    @Query("Select kmct.id from PromotionDetail kmct ")
    List<PromotionDetail> getAll();
}
