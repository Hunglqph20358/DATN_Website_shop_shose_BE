package com.example.backend.core.view.service.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Order;
import com.example.backend.core.view.dto.CustomerDTO;
import com.example.backend.core.view.dto.OrderDTO;
import com.example.backend.core.view.dto.OrderDetailDTO;
import com.example.backend.core.view.mapper.CustomerMapper;
import com.example.backend.core.view.mapper.OrderMapper;
import com.example.backend.core.view.mapper.ProductDetailMapper;
import com.example.backend.core.view.repository.CustomerRepository;
import com.example.backend.core.view.repository.OrderRepository;
import com.example.backend.core.view.repository.ProductDetailRepository;
import com.example.backend.core.view.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public ServiceResult<OrderDTO> createOrder(OrderDTO orderDTO) {
        ServiceResult<OrderDTO> result = new ServiceResult<>();
        OrderDTO dto = new OrderDTO();
        Order order = new Order();
        CustomerDTO customerDTO = customerMapper.toDto(customerRepository.findByCode(orderDTO.getCustomerDTO().getCode()));
        order.setCode("HD" + Instant.now().getEpochSecond());
        order.setCreateDate(Instant.now());
        order.setReceiver(orderDTO.getReceiver());
        order.setIdCustomer(customerDTO.getId());
        order.setPaymentType(orderDTO.getPaymentType());
        order.setShipPrice(orderDTO.getShipPrice());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setReceiverPhone(orderDTO.getReceiverPhone());
        order = orderRepository.save(order);
        dto = orderMapper.toDto(order);
        result.setData(dto);
        result.setStatus(HttpStatus.OK);
        result.setMessage("Success");
        return result;
    }

    @Override
    public ServiceResult<OrderDTO> updateOrder(OrderDTO orderDTO) {
        return null;
    }
}
