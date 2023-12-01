package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.DiscountAdminDTO;
import com.example.backend.core.admin.dto.DiscountDetailAdminDTO;
import com.example.backend.core.admin.dto.ProductAdminDTO;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.view.dto.ProductDTO;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface DiscountDetailAdminService {
    List<DiscountAdminDTO> getAllDiscount();
    List<DiscountAdminDTO> getAll();
    ServiceResult<DiscountDetailAdminDTO> createDiscount(DiscountDetailAdminDTO khuyenMaiDTO);
    ServiceResult<DiscountDetailAdminDTO> updateDiscount (Long discountDetailId, DiscountDetailAdminDTO updatedDiscountDetailAdminDTO);
    ServiceResult<Void> KichHoat(Long idDiscount);
    List<ProductAdminDTO> getAllProduct();
    DiscountAdminDTO getDetailDiscount(Long id);
    List<ProductAdminDTO> getProduct(String code, String name);
     List<DiscountAdminDTO> getAllKichHoat();
     List<DiscountAdminDTO> getAllKhongKichHoat();
    List<DiscountAdminDTO> getAllByDateRange(Date fromDate, Date toDate);
    List<DiscountAdminDTO> getAllByCodeOrName(String search);
}
