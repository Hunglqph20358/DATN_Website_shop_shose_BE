package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.OrderDetailAdminDTO;
import com.example.backend.core.view.dto.OrderDetailDTO;

import java.util.List;

public interface OrderDetailAdminService {
    List<OrderDetailAdminDTO> getAllByOrder(Long idOrder);
}
