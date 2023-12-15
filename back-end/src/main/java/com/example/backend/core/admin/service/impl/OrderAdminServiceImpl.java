package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.CustomerAdminDTO;
import com.example.backend.core.admin.dto.OrderAdminDTO;
import com.example.backend.core.admin.dto.StaffAdminDTO;
import com.example.backend.core.admin.mapper.CustomerAdminMapper;
import com.example.backend.core.admin.mapper.OrderAdminMapper;
import com.example.backend.core.admin.mapper.StaffMapper;
import com.example.backend.core.admin.repository.*;
import com.example.backend.core.admin.service.OrderAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.constant.AppConstant;
import com.example.backend.core.model.Order;
import com.example.backend.core.model.OrderHistory;
import com.example.backend.core.view.dto.CustomerDTO;
import com.example.backend.core.view.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderAdminServiceImpl implements OrderAdminService {


    @Autowired
    private OrderAdminMapper orderAdminMapper;
    @Autowired
    private OrderAdminRepository orderAdminRepository;

    @Autowired
    private CustomerAdminRepository customerAdminRepository;
    @Autowired
    private CustomerAdminMapper customerAdminMapper;
    @Autowired
    private OrderHistoryAdminRepository orderHistoryAdminRepository;

    @Autowired
    private OrderAdminCustomerRepository orderAdminCustomerRepository;

    @Autowired
    private StaffAdminRepository staffAdminRepository;

    @Autowired
    private StaffMapper staffMapper;

    @Override
    public List<OrderAdminDTO> getAllOrderAdmin(OrderAdminDTO orderAdminDTO) {
        List<OrderAdminDTO> lst = orderAdminCustomerRepository.getAllOrderAdmin(orderAdminDTO);
            return lst.stream().map(c -> {
                if (c.getIdCustomer() != null) {
                    CustomerAdminDTO customerAdminDTO = customerAdminMapper.toDto(
                            customerAdminRepository.findById(c.getIdCustomer())
                                    .orElse(null)
                    );
                    c.setCustomerAdminDTO(customerAdminDTO);
                }
                if (c.getIdStaff() != null) {
                    StaffAdminDTO staffAdminDTO = staffMapper.toDto(staffAdminRepository.findById(c.getIdStaff()).orElse(null));
                    c.setStaffAdminDTO(staffAdminDTO);
                }
                return c;
            }).collect(Collectors.toList());
    }


    @Override
    public ServiceResult<OrderAdminDTO> updateStatusChoXuLy(OrderAdminDTO orderAdminDTO) {
        ServiceResult<OrderAdminDTO> result = new ServiceResult<>();
        if(orderAdminDTO.getId() == null) {
            result.setData(null);
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Error");
            return result;
        }
        if(orderAdminDTO.getIdStaff() == null){
            result.setData(null);
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Error");
            return result;
        }
        Order order = orderAdminRepository.findById(orderAdminDTO.getId()).get();
        order.setStatus(AppConstant.CHO_XU_LY);
        order.setIdStaff(orderAdminDTO.getIdStaff());
        order = orderAdminRepository.save(order);
        if(order != null){
            OrderHistory orderHistory = new OrderHistory();
            orderHistory.setStatus(AppConstant.CHO_XU_LY);
            orderHistory.setCreateDate(Instant.now());
            orderHistory.setIdOrder(order.getId());
            orderHistory.setIdStaff(orderAdminDTO.getIdStaff());
            orderHistoryAdminRepository.save(orderHistory);
        }
        result.setData(orderAdminMapper.toDto(order));
        result.setStatus(HttpStatus.OK);
        result.setMessage("Success");
        return result;
    }

    @Override
    public ServiceResult<OrderAdminDTO> huyDonHang(OrderAdminDTO orderAdminDTO) {
        ServiceResult<OrderAdminDTO> result = new ServiceResult<>();
        if(orderAdminDTO.getId() == null) {
            result.setData(null);
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Error");
            return result;
        }
        if(orderAdminDTO.getIdStaff() == null){
            result.setData(null);
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Error");
            return result;
        }
        Order order = orderAdminRepository.findById(orderAdminDTO.getId()).get();
        order.setStatus(AppConstant.HOAN_HUY);
        order.setIdStaff(orderAdminDTO.getIdStaff());
        order = orderAdminRepository.save(order);
        result.setData(orderAdminMapper.toDto(order));
        result.setStatus(HttpStatus.OK);
        result.setMessage("Success");
        return result;
    }

    @Override
    public ServiceResult<OrderAdminDTO> giaoHangDonHang(OrderAdminDTO orderAdminDTO) {
        ServiceResult<OrderAdminDTO> result = new ServiceResult<>();
        if(orderAdminDTO.getId() == null) {
            result.setData(null);
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Error");
            return result;
        }
        if(orderAdminDTO.getIdStaff() == null){
            result.setData(null);
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Error");
            return result;
        }
        Order order = orderAdminRepository.findById(orderAdminDTO.getId()).get();
        order.setShipperPhone("0985218603");
        order.setDeliveryDate(Instant.now());
        order.setMissedOrder(0);
        order.setStatus(AppConstant.DANG_GIAO_HANG);
//        order.setIdStaff(orderAdminDTO.getIdStaff());
        order = orderAdminRepository.save(order);
        if(order != null){
            OrderHistory orderHistory = new OrderHistory();
            orderHistory.setStatus(AppConstant.DANG_GIAO_HANG);
            orderHistory.setCreateDate(Instant.now());
            orderHistory.setIdOrder(order.getId());
            orderHistory.setIdStaff(orderAdminDTO.getIdStaff());
            orderHistoryAdminRepository.save(orderHistory);
        }
        result.setData(orderAdminMapper.toDto(order));
        result.setStatus(HttpStatus.OK);
        result.setMessage("Success");
        return result;
    }

    @Override
    public ServiceResult<OrderAdminDTO> hoanThanhDonHang(OrderAdminDTO orderAdminDTO) {
        ServiceResult<OrderAdminDTO> result = new ServiceResult<>();
        if(orderAdminDTO.getId() == null) {
            result.setData(null);
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Error");
            return result;
        }
        if(orderAdminDTO.getIdStaff() == null){
            result.setData(null);
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Error");
            return result;
        }
        Order order = orderAdminRepository.findById(orderAdminDTO.getId()).get();
        order.setReceivedDate(Instant.now());
        order.setStatus(AppConstant.HOAN_THANH);
//        order.setIdStaff(orderAdminDTO.getIdStaff());
        order = orderAdminRepository.save(order);
        if(order != null){
            OrderHistory orderHistory = new OrderHistory();
            orderHistory.setStatus(AppConstant.HOAN_THANH);
            orderHistory.setCreateDate(Instant.now());
            orderHistory.setIdOrder(order.getId());
            orderHistory.setIdStaff(orderAdminDTO.getIdStaff());
            orderHistoryAdminRepository.save(orderHistory);
        }
        result.setData(orderAdminMapper.toDto(order));
        result.setStatus(HttpStatus.OK);
        result.setMessage("Success");
        return result;
    }

    @Override
    public ServiceResult<OrderAdminDTO> boLoDonHang(OrderAdminDTO orderAdminDTO) {
        ServiceResult<OrderAdminDTO> result = new ServiceResult<>();
        if(orderAdminDTO.getId() == null) {
            result.setData(null);
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Error");
            return result;
        }
        if(orderAdminDTO.getIdStaff() == null){
            result.setData(null);
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Error");
            return result;
        }
        Order order = orderAdminRepository.findById(orderAdminDTO.getId()).get();
        if(order.getMissedOrder() == null || order.getMissedOrder() == 0){
            order.setMissedOrder(AppConstant.BO_LO_LAN1);
        }
        if(order.getMissedOrder() == AppConstant.BO_LO_LAN1){
            order.setMissedOrder(AppConstant.BO_LO_LAN2);
        }
        if(order.getMissedOrder() == AppConstant.BO_LO_LAN2){
            order.setMissedOrder(AppConstant.BO_LO_LAN3);
            order.setStatus(AppConstant.HOAN_HUY);
        }
        order.setIdStaff(orderAdminDTO.getIdStaff());
        order = orderAdminRepository.save(order);
        result.setData(orderAdminMapper.toDto(order));
        result.setStatus(HttpStatus.OK);
        result.setMessage("Success");
        return result;
    }
}
