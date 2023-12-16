package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.CustomerAdminDTO;
import com.example.backend.core.admin.dto.VoucherAdminDTO;
import com.example.backend.core.admin.dto.VoucherFreeShipDTO;
import com.example.backend.core.commons.ServiceResult;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface VoucherFSService {

    ServiceResult<VoucherFreeShipDTO> createVoucher(VoucherFreeShipDTO voucherDTO);
    ServiceResult<VoucherFreeShipDTO> updateVoucher(Long id, VoucherFreeShipDTO updatedVoucherAdminDTO);
    ServiceResult<Void> deleteVoucher(Long voucherId);
//    List<VoucherFreeShipDTO> detailById(Long voucherId);
    List<VoucherFreeShipDTO> getAllVouchers();
    List<CustomerAdminDTO> getAllCustomer();
    List<VoucherFreeShipDTO> getVouchersByTimeRange(Date fromDate, Date toDate);
    List<VoucherFreeShipDTO> getVouchersByKeyword(String keyword);
    ServiceResult<Void> KichHoat(Long idVoucher);
    VoucherFreeShipDTO getDetailVoucher(Long id);
    List<VoucherFreeShipDTO> getVouchersByCustomer(String searchTerm);
    List<VoucherFreeShipDTO> getAllKhongKH();
    List<VoucherFreeShipDTO> getAllKichHoat();
    void sendMessageUsingThymeleafTemplate(VoucherFreeShipDTO voucherAdminDTO) throws MessagingException;
    byte[] exportExcelVoucher() throws IOException;
}
