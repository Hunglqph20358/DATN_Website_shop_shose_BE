package com.example.backend.core.view.service.impl;


import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.OrderDetail;
import com.example.backend.core.model.ProductDetail;
import com.example.backend.core.view.dto.*;
import com.example.backend.core.view.mapper.*;
import com.example.backend.core.view.repository.*;
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

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ImagesRepository imagesRepository;
    @Autowired
    private ImagesMapper imagesMapper;

    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private ColorMapper colorMapper;

    @Autowired
    private SizeRepository sizeRepository;
    @Autowired
    private SizeMapper sizeMapper;

    @Override
    public List<OrderDetailDTO> getAllByOrder(Long idOrder) {
        if(idOrder == null){
            return null;
        }
        List<OrderDetailDTO> lst = orderDetailMapper.toDto(orderDetailRepository.findByIdOrder(idOrder));
        for (int i = 0; i < lst.size() ; i++) {
            ProductDetailDTO productDetailDTO = productDetailMapper.toDto(productDetailRepository.findById(lst.get(i).getIdProductDetail()).get());
            ProductDTO productDTO = productMapper.toDto(productRepository.findById(productDetailDTO.getIdProduct()).get());
            List<ImagesDTO> imagesDTOList = imagesMapper.toDto(imagesRepository.findByIdProduct(productDTO.getId()));
            ColorDTO colorDTO = colorMapper.toDto(colorRepository.findById(productDetailDTO.getIdColor()).get());
            productDetailDTO.setColorDTO(colorDTO);
            SizeDTO sizeDTO = sizeMapper.toDto(sizeRepository.findById(productDetailDTO.getIdSize()).get());
            productDetailDTO.setSizeDTO(sizeDTO);
            productDTO.setImagesDTOList(imagesDTOList);
            productDetailDTO.setProductDTO(productDTO);
            lst.get(i).setProductDetailDTO(productDetailDTO);
            lst.set(i,lst.get(i));
        }
        return lst;
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
