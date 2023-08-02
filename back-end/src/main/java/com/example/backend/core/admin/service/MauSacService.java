package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.MauSacDTO;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.MauSac;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MauSacService {

    Page<MauSacDTO> getAllMauSac(Integer page);

    ServiceResult<MauSacDTO> createMauSac(MauSacDTO mauSacDTO);
}
