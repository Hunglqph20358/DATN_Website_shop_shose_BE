package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.OrderAdminDTO;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Order;

import java.util.List;

public interface OrderAdminService {

    List<OrderAdminDTO> getAllOrderAdmin(Integer status);

    ServiceResult<OrderAdminDTO> updateStatusChoXuLy(OrderAdminDTO orderAdminDTO);

    ServiceResult<OrderAdminDTO> huyDonHang(OrderAdminDTO orderAdminDTO);
    ServiceResult<OrderAdminDTO> giaoHangDonHang(OrderAdminDTO orderAdminDTO);
    ServiceResult<OrderAdminDTO> hoanThanhDonHang(OrderAdminDTO orderAdminDTO);
    ServiceResult<OrderAdminDTO> boLoDonHang(OrderAdminDTO orderAdminDTO);
}
