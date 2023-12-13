package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.StaffAdminDTO;
import com.example.backend.core.model.Staff;

import java.util.List;
import java.util.Optional;

public interface StaffAdminService {

    List<StaffAdminDTO> getAllStaff();
    Optional<Staff> findById(String id);

}
