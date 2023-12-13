package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.StaffAdminDTO;
import com.example.backend.core.admin.mapper.StaffMapper;
import com.example.backend.core.admin.repository.StaffAdminRepository;
import com.example.backend.core.admin.service.StaffAdminService;
import com.example.backend.core.model.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StaffAdminServiceImpl implements StaffAdminService {
    @Autowired
    private StaffAdminRepository repository;
    @Autowired
    private StaffMapper staffMapper;
    @Override
    public List<StaffAdminDTO> getAllStaff() {
        List<Staff> listStaff = repository.findAll();
        List<StaffAdminDTO> listDto = new ArrayList<>();
         return listDto = staffMapper.toDto(listStaff);
    }

    @Override
    public Optional<Staff> findById(String id) {
        return repository.findById(Long.valueOf(id));
    }
}
