package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.OrderAdminDTO;
import com.example.backend.core.admin.mapper.OrderAdminMapper;
import com.example.backend.core.admin.repository.OrderAdminRepository;
import com.example.backend.core.admin.service.OrderAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderAdminServiceImpl implements OrderAdminService {


    @Autowired
    private OrderAdminMapper orderAdminMapper;
    @Autowired
    private OrderAdminRepository orderAdminRepository;

    @Override
    public List<OrderAdminDTO> getAllOrderAdmin(Integer status) {
        if(status != null && status != 6){
            return orderAdminMapper.toDto(orderAdminRepository.findByStatusOrderByCreateDateDesc(status));
        }
        return orderAdminMapper.toDto(orderAdminRepository.getAllByOrderByCreateDateDesc());
    }

    @Override
    public ServiceResult<OrderAdminDTO> updateStatusChoXuLy(Long idOrder) {
        ServiceResult<OrderAdminDTO> result = new ServiceResult<>();
        if(idOrder == null) {
            result.setData(null);
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Error");
            return result;
        }
        Order order = orderAdminRepository.findById(idOrder).get();
        order.setStatus(1);
        order = orderAdminRepository.save(order);
        result.setData(orderAdminMapper.toDto(order));
        result.setStatus(HttpStatus.BAD_REQUEST);
        result.setMessage("Success");
        return result;
    }

    @Override
    public ServiceResult<OrderAdminDTO> huyDonHang(Long idOrder) {
        ServiceResult<OrderAdminDTO> result = new ServiceResult<>();
        if(idOrder == null) {
            result.setData(null);
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Error");
            return result;
        }
        Order order = orderAdminRepository.findById(idOrder).get();
        order.setStatus(4);
        order = orderAdminRepository.save(order);
        result.setData(orderAdminMapper.toDto(order));
        result.setStatus(HttpStatus.OK);
        result.setMessage("Success");
        return result;
    }
}
