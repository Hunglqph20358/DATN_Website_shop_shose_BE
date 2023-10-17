package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.DiscountDTO;
import com.example.backend.core.commons.ServiceResult;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DiscountService {
    Page<DiscountDTO> getAllKhuyenMai(Integer page);
    List<DiscountDTO> getAll();
    ServiceResult<DiscountDTO> createKhuyenMai(DiscountDTO khuyenMaiDTO);
}
