package com.example.backend.core.view.service;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.view.dto.OrderDTO;
import com.example.backend.core.view.dto.OrderDetailDTO;

import java.util.List;

public interface OrderService {


    ServiceResult<OrderDTO> createOrder(OrderDTO orderDTO);

    ServiceResult<OrderDTO> updateOrder(OrderDTO orderDTO);


}
