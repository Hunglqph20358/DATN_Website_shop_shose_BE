package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.OrderAdminDTO;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Order;

import java.util.List;

public interface OrderAdminService {

    List<OrderAdminDTO> getAllOrderAdmin(Integer status);

    ServiceResult<OrderAdminDTO> updateStatusChoXuLy(Long idOrder);

    ServiceResult<OrderAdminDTO> huyDonHang(Long idOrder);
}
