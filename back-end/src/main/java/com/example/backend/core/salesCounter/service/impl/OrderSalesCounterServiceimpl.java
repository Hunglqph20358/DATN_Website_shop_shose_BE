package com.example.backend.core.salesCounter.service.impl;

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
import com.example.backend.core.salesCounter.service.OrderSalesCounterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;

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
    private StaffSCMapper staffSCMapper;

    @Override
    public ServiceResult<OrderSalesDTO> createOrderSales(OrderSalesDTO orderSalesDTO) {
        ServiceResult<OrderSalesDTO> result = new ServiceResult<>();
        Order order = new Order();
        if (orderSalesDTO.getIdCustomer() == null){
            order.setCode("HD" + Instant.now().getEpochSecond());
            order.setCreateDate(Instant.now());
            order.setPaymentType(orderSalesDTO.getPaymentType());
            order.setTotalPrice(orderSalesDTO.getTotalPrice());
            order.setTotalPayment(orderSalesDTO.getTotalPayment());
            order.setStatus(AppConstant.HOAN_THANH);
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
                order.setPaymentType(orderSalesDTO.getPaymentType());
                order.setTotalPrice(orderSalesDTO.getTotalPrice());
                order.setTotalPayment(orderSalesDTO.getTotalPayment());
                order.setStatus(0);
                order = orderSalesCountRepository.save(order);
//            if(StringUtils.isNotBlank(salesDTO.getCodeVoucher())){
//                Voucher voucher = voucherRepository.findByCode(orderDTO.getCodeVoucher());
//                if(null != voucher){
//                    voucher.setQuantity(voucher.getQuantity() - 1);
//                    voucherRepository.save(voucher);
//                }
//            }
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
}
