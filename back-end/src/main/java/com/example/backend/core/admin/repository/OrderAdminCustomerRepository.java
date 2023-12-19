package com.example.backend.core.admin.repository;

import com.example.backend.core.admin.dto.OrderAdminDTO;

import java.util.List;

public interface OrderAdminCustomerRepository {

    List<OrderAdminDTO> getAllOrderAdmin(OrderAdminDTO orderAdminDTO);
    List<OrderAdminDTO> getAllOrderSalesAdmin(OrderAdminDTO orderAdminDTO);
}
