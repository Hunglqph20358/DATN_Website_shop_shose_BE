package com.example.backend.core.view.repository;

import com.example.backend.core.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    @Query(value = "SELECT v.*\n" +
            "FROM Voucher v\n" +
            "WHERE v.endDate > NOW() AND v.quantity > 0\n" +
            "ORDER BY v.endDate ASC", nativeQuery = true)
    List<Voucher> getAllVoucher();
}
