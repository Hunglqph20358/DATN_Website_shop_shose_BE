package com.example.backend.core.view.repository;

import com.example.backend.core.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    @Query(value = "select d.* from discount d where d.end_date > now()", nativeQuery = true)
    List<Discount> getDiscountConApDung();
}
