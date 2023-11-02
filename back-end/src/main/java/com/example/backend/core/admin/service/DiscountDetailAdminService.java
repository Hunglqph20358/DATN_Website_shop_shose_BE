package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.DiscountAdminDTO;
import com.example.backend.core.admin.dto.DiscountDetailAdminDTO;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.view.dto.ProductDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DiscountDetailAdminService {
    Page<DiscountDetailAdminDTO> getAllDiscount(Integer page);
    List<DiscountDetailAdminDTO> getAll();
    ServiceResult<DiscountDetailAdminDTO> createDiscount(DiscountDetailAdminDTO khuyenMaiDTO);
    ServiceResult<DiscountDetailAdminDTO> updateDiscount (Long discountDetailId, DiscountDetailAdminDTO updatedDiscountDetailAdminDTO);
    ServiceResult<Void> deleteDiscount(Long idDisount);
    List<ProductDTO> getAllProduct();
    List<DiscountDetailAdminDTO> getDetailDiscount(long id);
}
