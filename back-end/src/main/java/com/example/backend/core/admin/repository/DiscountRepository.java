package com.example.backend.core.admin.repository;
import com.example.backend.core.model.DiscountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<DiscountDetail,Long> {
    @Query("Select kmct.id from DiscountDetail kmct ")
    List<DiscountDetail> getAll();
}
