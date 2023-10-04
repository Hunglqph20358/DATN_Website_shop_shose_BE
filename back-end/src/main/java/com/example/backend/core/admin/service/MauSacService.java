package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.MauSacDTO;
import com.example.backend.core.commons.ServiceResult;
import org.springframework.data.domain.Page;

public interface MauSacService {

    Page<MauSacDTO> getAllMauSac(Integer page);

    ServiceResult<MauSacDTO> createMauSac(MauSacDTO mauSacDTO);
}
