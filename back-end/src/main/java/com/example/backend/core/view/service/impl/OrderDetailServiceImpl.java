package com.example.backend.core.view.service.impl;


import com.example.backend.core.view.dto.OrderDetailDTO;
import com.example.backend.core.view.service.OrderDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {


    @Override
    public List<OrderDetailDTO> getAllByOrder(Long idOrder) {
        return null;
    }
}
