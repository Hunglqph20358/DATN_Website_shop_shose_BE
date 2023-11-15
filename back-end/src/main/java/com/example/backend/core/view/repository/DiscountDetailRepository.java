package com.example.backend.core.view.repository;

import com.example.backend.core.model.DiscountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface DiscountDetailRepository extends JpaRepository<DiscountDetail, Long> {

    List<DiscountDetail> findByIdDiscount(Long idDiscount);
}
