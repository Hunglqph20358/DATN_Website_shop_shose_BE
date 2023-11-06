package com.example.backend.core.view.service.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.constant.AppConstant;
import com.example.backend.core.model.Address;
import com.example.backend.core.model.Order;
import com.example.backend.core.model.Voucher;
import com.example.backend.core.security.dto.UsersDTO;
import com.example.backend.core.view.dto.AddressDTO;
import com.example.backend.core.view.dto.CustomerDTO;
import com.example.backend.core.view.dto.OrderDTO;
import com.example.backend.core.view.dto.OrderDetailDTO;
import com.example.backend.core.view.mapper.CustomerMapper;
import com.example.backend.core.view.mapper.OrderMapper;
import com.example.backend.core.view.mapper.ProductDetailMapper;
import com.example.backend.core.view.repository.CustomerRepository;
import com.example.backend.core.view.repository.OrderRepository;
import com.example.backend.core.view.repository.ProductDetailRepository;
import com.example.backend.core.view.repository.VoucherRepository;
import com.example.backend.core.view.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
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
    @Autowired
    private VoucherRepository voucherRepository;
    @Override
    public ServiceResult<OrderDTO> createOrder(OrderDTO orderDTO) {
        ServiceResult<OrderDTO> result = new ServiceResult<>();
        OrderDTO dto = new OrderDTO();
        Order order = new Order();
        CustomerDTO customerDTO = customerMapper.toDto(customerRepository.findByCode(orderDTO.getCustomerDTO().getCode()));
        if(customerDTO != null){
            order.setCode("HD" + Instant.now().getEpochSecond());
            order.setCreateDate(Instant.now());
            order.setReceiver(orderDTO.getReceiver());
            order.setIdCustomer(customerDTO.getId());
            order.setPaymentType(orderDTO.getPaymentType());
            order.setShipPrice(orderDTO.getShipPrice());
            order.setTotalPrice(orderDTO.getTotalPrice());
            order.setTotalPayment(orderDTO.getTotalPayment());
            order.setReceiverPhone(orderDTO.getReceiverPhone());
            order.setAddressReceived(order.getAddressReceived());
            order.setStatus(0);
            order = orderRepository.save(order);
            if(StringUtils.isNotBlank(orderDTO.getCodeVoucher())){
                Voucher voucher = voucherRepository.findByCode(orderDTO.getCodeVoucher());
                if(null != voucher){
                    voucher.setQuantity(voucher.getQuantity() - 1);
                    voucherRepository.save(voucher);
                }
            }
            dto = orderMapper.toDto(order);
            result.setData(dto);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Success");
        }

        return result;
    }

    @Override
    public ServiceResult<OrderDTO> updateOrder(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public Page<OrderDTO> getAll(OrderDTO orderDTO) {
        Pageable pageable = PageRequest.of(orderDTO.getPage(), AppConstant.PAGE_SIZE);
        return orderRepository.findByIdCustomerOrderByCreateDate(orderDTO.getIdCustomer(), pageable).map(orderMapper::toDto);
    }
}
