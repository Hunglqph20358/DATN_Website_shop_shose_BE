package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.PromotionDTO;
import com.example.backend.core.commons.ServiceResult;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PromotionService {
    Page<PromotionDTO> getAllKhuyenMai(Integer page);
    List<PromotionDTO> getAll();
    ServiceResult<PromotionDTO> createKhuyenMai(PromotionDTO khuyenMaiDTO);
}
