package com.example.backend.core.admin.repository;

import com.example.backend.core.model.MauSac;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface MauSacRepository extends JpaRepository<MauSac,Long> {
}
