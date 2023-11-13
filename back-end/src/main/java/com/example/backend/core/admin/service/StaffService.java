package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.StaffDTO;
import com.example.backend.core.model.Staff;

import java.util.List;
import java.util.Optional;

public interface StaffService {

    List<StaffDTO> getAllStaff();
    Optional<Staff> findById(String id);

}
