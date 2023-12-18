package com.example.backend.core.salesCounter.service.impl;

import com.example.backend.core.admin.dto.CustomerAdminDTO;
import com.example.backend.core.admin.dto.OrderAdminDTO;
import com.example.backend.core.admin.dto.StaffAdminDTO;
import com.example.backend.core.admin.mapper.CustomerAdminMapper;
import com.example.backend.core.admin.mapper.StaffMapper;
import com.example.backend.core.admin.repository.StaffAdminRepository;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.constant.AppConstant;
import com.example.backend.core.model.Order;
import com.example.backend.core.model.Voucher;
import com.example.backend.core.salesCounter.dto.CustomerSCDTO;
import com.example.backend.core.salesCounter.dto.OrderSalesDTO;
import com.example.backend.core.salesCounter.dto.StaffSCDTO;
import com.example.backend.core.salesCounter.mapper.CustomerSCMapper;
import com.example.backend.core.salesCounter.mapper.OrderSalesCounterMapper;
import com.example.backend.core.salesCounter.mapper.StaffSCMapper;
import com.example.backend.core.salesCounter.repository.CustomerSCRepository;
import com.example.backend.core.salesCounter.repository.OrderSalesCountRepository;
import com.example.backend.core.salesCounter.repository.OrderSalesCounterDetailRepository;
import com.example.backend.core.salesCounter.repository.OrderSalesCustomRepository;
import com.example.backend.core.salesCounter.service.OrderSalesCounterService;
import com.example.backend.core.view.mapper.CustomerMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderSalesCounterServiceimpl implements OrderSalesCounterService {

    @Autowired
    private OrderSalesCountRepository orderSalesCountRepository;
    @Autowired
    private OrderSalesCounterMapper orderSalesCounterMapper;
    @Autowired
    private CustomerSCRepository customerSCRepository;
    @Autowired
    private CustomerSCMapper customerSCMapper;
    @Autowired
    private CustomerAdminMapper customerMapper;
    @Autowired
    private StaffMapper staffSCMapper;
    @Autowired
    private OrderSalesCustomRepository orderSalesCustomRepository;
    @Autowired
    private StaffAdminRepository staffAdminRepository;

    @Override
    public ServiceResult<OrderSalesDTO> createOrderSales(OrderSalesDTO orderSalesDTO) {
        ServiceResult<OrderSalesDTO> result = new ServiceResult<>();
        Order order = new Order();
        if (orderSalesDTO.getIdCustomer() == null){
            order.setCode("HD" + Instant.now().getEpochSecond());
            order.setIdStaff(orderSalesDTO.getIdStaff());
            order.setCreateDate(Instant.now());
            order.setPaymentType(AppConstant.DA_THANH_TOAN);
            order.setStatusPayment(orderSalesDTO.getStatusPayment());
            order.setTotalPrice(orderSalesDTO.getTotalPrice());
            order.setTotalPayment(orderSalesDTO.getTotalPayment());
            order.setStatus(AppConstant.HOAN_THANH);
            order.setType(1);
            order.setEmail(orderSalesDTO.getEmail());
            order = orderSalesCountRepository.save(order);
            orderSalesDTO = orderSalesCounterMapper.toDto(order);
            result.setData(orderSalesDTO);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Success");
        }
        else {
            CustomerSCDTO customerSCDTO = customerSCMapper.toDto(customerSCRepository.findByPhone(orderSalesDTO.getCustomerDTO().getPhone()));
            if(customerSCDTO != null){
                order.setCode("HD" + Instant.now().getEpochSecond());
                order.setCreateDate(Instant.now());
                order.setReceiver(orderSalesDTO.getReceiver());
                order.setIdCustomer(orderSalesDTO.getIdCustomer());
                order.setIdStaff(orderSalesDTO.getIdStaff());
                order.setPaymentType(AppConstant.DA_THANH_TOAN);
                order.setStatusPayment(orderSalesDTO.getStatusPayment());
                order.setTotalPrice(orderSalesDTO.getTotalPrice());
                order.setTotalPayment(orderSalesDTO.getTotalPayment());
                order.setStatus(AppConstant.HOAN_THANH);
                order.setType(1);
                order.setEmail(orderSalesDTO.getEmail());
                order = orderSalesCountRepository.save(order);
                orderSalesDTO = orderSalesCounterMapper.toDto(order);
                result.setData(orderSalesDTO);
                result.setStatus(HttpStatus.OK);
                result.setMessage("Success");
            }
        }

        return result;
    }

    @Override
    public ServiceResult<OrderSalesDTO> updateOrderSales(OrderSalesDTO orderSalesDTO) {
        return null;
    }

    @Override
    public List<OrderSalesDTO> getAllOrder() {
        return orderSalesCounterMapper.toDto(orderSalesCountRepository.findAll());
    }

    @Override
    public List<OrderAdminDTO> getAllOrderAdmin(OrderAdminDTO orderAdminDTO) {
        List<OrderAdminDTO> lst = orderSalesCustomRepository.getAllOrderAdmin(orderAdminDTO);
        return lst.stream().map(c -> {
            if (c.getIdCustomer() != null) {
                CustomerAdminDTO customerAdminDTO = customerMapper.toDto(
                        customerSCRepository.findById(c.getIdCustomer())
                                .orElse(null)
                );
                c.setCustomerAdminDTO(customerAdminDTO);
            }
            if (c.getIdStaff() != null) {
                StaffAdminDTO staffAdminDTO = staffSCMapper.toDto(staffAdminRepository.findById(c.getIdStaff()).orElse(null));
                c.setStaffAdminDTO(staffAdminDTO);
            }
            return c;
        }).collect(Collectors.toList());
    }
}
