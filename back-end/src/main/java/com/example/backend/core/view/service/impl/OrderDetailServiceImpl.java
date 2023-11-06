package com.example.backend.core.view.service.impl;


import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.OrderDetail;
import com.example.backend.core.model.ProductDetail;
import com.example.backend.core.view.dto.OrderDetailDTO;
import com.example.backend.core.view.dto.ProductDetailDTO;
import com.example.backend.core.view.repository.OrderDetailRepository;
import com.example.backend.core.view.repository.OrderRepository;
import com.example.backend.core.view.repository.ProductDetailRepository;
import com.example.backend.core.view.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Override
    public List<OrderDetailDTO> getAllByOrder(Long idOrder) {
        List<OrderDetailDTO> lst = new ArrayList<>();
        return null;
    }

    @Override
    public ServiceResult<OrderDetailDTO> createOrderDetail(OrderDetailDTO orderDetailDTO) {
        ServiceResult<OrderDetailDTO> result = new ServiceResult<>();
        if(orderDetailDTO.getIdOrder() !=null && orderDetailDTO.getIdProductDetail() !=null){
            OrderDetail orderDetail = new OrderDetail();
                Optional<ProductDetail> productDetail = productDetailRepository.findById(orderDetailDTO.getIdProductDetail());
            if(productDetail.isPresent()){
                orderDetail.setIdOrder(orderDetailDTO.getIdOrder());
                orderDetail.setIdProductDetail(orderDetailDTO.getIdProductDetail());
                orderDetail.setPrice(orderDetailDTO.getPrice());
                orderDetail.setQuantity(orderDetailDTO.getQuantity());
                productDetail.get().setQuantity(productDetail.get().getQuantity() - orderDetailDTO.getQuantity());
                orderDetail.setStatus(0);
                orderDetailRepository.save(orderDetail);
                productDetailRepository.save(productDetail.get());
                result.setStatus(HttpStatus.OK);
                result.setMessage("Success");
                result.setData(orderDetailDTO);
            }
        }
        return result;
    }
}
