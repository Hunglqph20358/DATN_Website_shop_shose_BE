package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.DiscountDetailAdminDTO;
import com.example.backend.core.admin.dto.VoucherAdminDTO;
import com.example.backend.core.commons.ServiceResult;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VoucherAdminService {
    Page<VoucherAdminDTO> getAllVoucher(Integer page);
    List<VoucherAdminDTO> getAll();

    ServiceResult<VoucherAdminDTO> createVoucher(VoucherAdminDTO voucherDTO);
    ServiceResult<VoucherAdminDTO> updateVoucher(Long id, VoucherAdminDTO updatedVoucherAdminDTO);
    ServiceResult<Void> deleteVoucher(Long voucherId);
    List<VoucherAdminDTO> detailById(Long voucherId);
}
