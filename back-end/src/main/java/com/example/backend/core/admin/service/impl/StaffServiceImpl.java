package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.StaffDTO;
import com.example.backend.core.admin.mapper.StaffMapper;
import com.example.backend.core.admin.repository.StaffRepository;
import com.example.backend.core.admin.service.StaffService;
import com.example.backend.core.model.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffRepository repository;
    @Autowired
    private StaffMapper staffMapper;
    @Override
    public List<StaffDTO> getAllStaff() {
        List<Staff> listStaff = repository.findAll();
        List<StaffDTO> listDto = new ArrayList<>();
         return listDto = staffMapper.toDto(listStaff);
    }

    @Override
    public Optional<Staff> findById(String id) {
        return repository.findById(Long.valueOf(id));
    }
}
