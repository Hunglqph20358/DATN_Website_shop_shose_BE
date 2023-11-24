package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.CustomerAdminDTO;
import com.example.backend.core.admin.dto.VoucherAdminDTO;
import com.example.backend.core.admin.dto.VoucherFreeShipDTO;
import com.example.backend.core.commons.ServiceResult;

import java.util.List;

public interface VoucherFSService {
    List<VoucherFreeShipDTO> getAll();

    ServiceResult<VoucherFreeShipDTO> createVoucher(VoucherFreeShipDTO voucherDTO);
    ServiceResult<VoucherFreeShipDTO> updateVoucher(Long id, VoucherAdminDTO updatedVoucherAdminDTO);
    ServiceResult<Void> deleteVoucher(Long voucherId);
    List<VoucherFreeShipDTO> detailById(Long voucherId);
    List<VoucherFreeShipDTO> getAllVouchers();
    List<CustomerAdminDTO> getAllCustomer();
}
