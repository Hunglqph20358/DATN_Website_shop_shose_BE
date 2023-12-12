package com.example.backend.core.admin.repository;

import com.example.backend.core.admin.dto.CustomerAdminDTO;
import com.example.backend.core.admin.dto.VoucherFreeShipDTO;
import com.example.backend.core.commons.ServiceResult;

import java.util.Date;
import java.util.List;

public interface VoucherFSCustomerRepository {
    List<VoucherFreeShipDTO> getAllVouchers();
    List<CustomerAdminDTO> getAllCustomer();
    List<VoucherFreeShipDTO> getVouchersByTimeRange(Date fromDate, Date toDate);
    List<VoucherFreeShipDTO> getVouchersByKeyword(String keyword);
    List<VoucherFreeShipDTO> getVouchersByCustomer(String searchTerm);
    List<VoucherFreeShipDTO> getAllKhongKH();
    List<VoucherFreeShipDTO> getAllKichHoat();
}
