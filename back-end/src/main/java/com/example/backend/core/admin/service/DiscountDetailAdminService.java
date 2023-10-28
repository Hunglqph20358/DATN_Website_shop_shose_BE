package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.DiscountAdminDTO;
import com.example.backend.core.admin.dto.DiscountDetailAdminDTO;
import com.example.backend.core.commons.ServiceResult;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DiscountDetailAdminService {
    Page<DiscountDetailAdminDTO> getAllKhuyenMai(Integer page);
    List<DiscountDetailAdminDTO> getAll();
    ServiceResult<DiscountDetailAdminDTO> createKhuyenMai(DiscountDetailAdminDTO khuyenMaiDTO);
    ServiceResult<DiscountDetailAdminDTO> updateKhuyenMai(DiscountDetailAdminDTO khuyenMaiDTO);
    ServiceResult<Void> deleteKhuyenMai(Long khuyenMaiId);

}
