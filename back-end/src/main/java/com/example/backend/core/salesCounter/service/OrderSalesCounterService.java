package com.example.backend.core.salesCounter.service;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.salesCounter.dto.OrderSalesDTO;

public interface OrderSalesCounterService {
    ServiceResult<OrderSalesDTO> createOrderSales(OrderSalesDTO orderSalesDTO);
    ServiceResult<OrderSalesDTO> updateOrderSales(OrderSalesDTO orderSalesDTO);

}
