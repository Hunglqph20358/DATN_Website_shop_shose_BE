package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.CustomerAdminDTO;
import com.example.backend.core.admin.dto.DiscountDetailAdminDTO;
import com.example.backend.core.admin.dto.VoucherAdminDTO;
import com.example.backend.core.commons.ServiceResult;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface VoucherAdminService {
    List<VoucherAdminDTO> getAll();

    ServiceResult<VoucherAdminDTO> createVoucher(VoucherAdminDTO voucherDTO);
    ServiceResult<VoucherAdminDTO> updateVoucher(Long id, VoucherAdminDTO updatedVoucherAdminDTO);
    ServiceResult<Void> deleteVoucher(Long voucherId);
    List<VoucherAdminDTO> detailById(Long voucherId);
    List<VoucherAdminDTO> getAllVouchers();
    List<CustomerAdminDTO> getAllCustomer();
    List<VoucherAdminDTO> getVouchersByTimeRange(Date fromDate, Date toDate);
    List<VoucherAdminDTO> getVouchersByKeyword(String keyword);
    ServiceResult<Void> KichHoat(Long idVoucher) throws MessagingException;
    VoucherAdminDTO getDetailVoucher(Long id);
    List<VoucherAdminDTO> getAllKhongKH();
    List<VoucherAdminDTO> getAllKichHoat();
    List<VoucherAdminDTO> getVouchersByCustomer(String searchTerm);
    void sendMessageUsingThymeleafTemplate(VoucherAdminDTO voucherAdminDTO) throws MessagingException;
}
