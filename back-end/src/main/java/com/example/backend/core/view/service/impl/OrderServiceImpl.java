package com.example.backend.core.view.service.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.constant.AppConstant;
import com.example.backend.core.model.Address;
import com.example.backend.core.model.Customer;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public ServiceResult<OrderDTO> createOrder(OrderDTO orderDTO) {
        ServiceResult<OrderDTO> result = new ServiceResult<>();
        OrderDTO dto = new OrderDTO();
        Order order = new Order();
        CustomerDTO customerDTO = customerMapper.toDto(customerRepository.findByCode(orderDTO.getCustomerDTO().getCode()));
        if (customerDTO != null) {
            order.setCode("HD" + Instant.now().getEpochSecond());
            order.setCreateDate(Instant.now());
            order.setReceiver(orderDTO.getReceiver());
            order.setIdCustomer(customerDTO.getId());
            order.setShipPrice(orderDTO.getShipPrice());
            order.setTotalPrice(orderDTO.getTotalPrice());
            order.setReceiverPhone(orderDTO.getReceiverPhone());
            order.setAddressReceived(orderDTO.getAddressReceived());
            if (orderDTO.getPaymentType() == 1) {
                order.setPaymentType(orderDTO.getPaymentType());
                order.setTotalPayment(orderDTO.getTotalPayment());
                order.setPaymentDate(Instant.now());
                order.setStatusPayment(0);
            } else {
                order.setPaymentType(orderDTO.getPaymentType());
                order.setTotalPayment(null);
                order.setStatusPayment(1);
            }
            order.setStatus(0);
            if (StringUtils.isNotBlank(orderDTO.getCodeVoucher())) {
                Voucher voucher = voucherRepository.findByCode(orderDTO.getCodeVoucher());
                if (null != voucher) {
                    voucher.setQuantity(voucher.getQuantity() - 1);
                    order.setCodeVoucher(voucher.getCode());
                    voucherRepository.save(voucher);
                }
            }
            order = orderRepository.save(order);
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
    public List<OrderDTO> getAll(OrderDTO orderDTO) {
        if (orderDTO.getIdCustomer() == null) {
            return null;
        }
        if (null != orderDTO.getStatus() && orderDTO.getStatus() != 6) {
            return orderMapper.toDto(orderRepository.findByIdCustomerAndStatusOrderByCreateDateDesc(orderDTO.getIdCustomer(), orderDTO.getStatus()));
        }
        return orderMapper.toDto(orderRepository.findByIdCustomerOrderByCreateDateDesc(orderDTO.getIdCustomer()));
    }

    @Override
    public ServiceResult<OrderDTO> createOrderNotLogin(OrderDTO orderDTO) {
        ServiceResult<OrderDTO> result = new ServiceResult<>();
        OrderDTO dto = new OrderDTO();
        Order order = new Order();
//        Customer customer = new Customer();
//        CustomerDTO customerDTO = customerMapper.toDto(customerRepository.findByCode(orderDTO.getCustomerDTO().getCode()));
//        if( customerRepository.findByEmail(orderDTO.getEmail()) == null){
//            customer.setFullname(orderDTO.getReceiver());
//            customer.setPhone(orderDTO.getReceiverPhone());
//            customer.setCreateDate(Instant.now());
//            customer.setEmail(orderDTO.getEmail());
//            customer.setCode("KH" + Instant.now().getEpochSecond());
//            customer.setUsername(orderDTO.getEmail().substring(0,orderDTO.getEmail().indexOf("@")));
//            customer.setPassword(passwordEncoder.encode("123456"));
//            customer = customerRepository.save(customer);
//            order.setIdCustomer(customer.getId());
//        }else {
//            order.setIdCustomer(customer.getId());
//        }
        order.setCode("HD" + Instant.now().getEpochSecond());
        order.setCreateDate(Instant.now());
        order.setReceiver(orderDTO.getReceiver());
        order.setPaymentType(orderDTO.getPaymentType());
        order.setShipPrice(orderDTO.getShipPrice());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setTotalPayment(orderDTO.getTotalPayment());
        order.setReceiverPhone(orderDTO.getReceiverPhone());
        order.setAddressReceived(order.getAddressReceived());
        if (orderDTO.getPaymentType() == 1) {
            order.setPaymentType(orderDTO.getPaymentType());
            order.setTotalPayment(orderDTO.getTotalPayment());
            order.setStatusPayment(0);
        } else {
            order.setPaymentType(orderDTO.getPaymentType());
            order.setTotalPayment(null);
            order.setStatusPayment(1);
        }
        order = orderRepository.save(order);
        if (StringUtils.isNotBlank(orderDTO.getCodeVoucher())) {
            Voucher voucher = voucherRepository.findByCode(orderDTO.getCodeVoucher());
            if (null != voucher) {
                voucher.setQuantity(voucher.getQuantity() - 1);
                voucherRepository.save(voucher);
            }
        }
        dto = orderMapper.toDto(order);
        result.setData(dto);
        result.setStatus(HttpStatus.OK);
        result.setMessage("Success");
        return result;
    }
}
