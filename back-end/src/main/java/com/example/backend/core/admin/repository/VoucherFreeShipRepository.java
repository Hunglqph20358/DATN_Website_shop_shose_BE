package com.example.backend.core.admin.repository;

import com.example.backend.core.model.VoucherFreeShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherFreeShipRepository extends JpaRepository<VoucherFreeShip,Long> {
}
