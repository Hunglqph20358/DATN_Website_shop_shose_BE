package com.example.backend.core.view.service.impl;


import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Discount;
import com.example.backend.core.model.DiscountDetail;
import com.example.backend.core.model.OrderDetail;
import com.example.backend.core.model.OrderHistory;
import com.example.backend.core.model.ProductDetail;
import com.example.backend.core.view.dto.*;
import com.example.backend.core.view.mapper.*;
import com.example.backend.core.view.repository.*;
import com.example.backend.core.view.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Autowired
    private OrderHistoryMapper orderHistoryMapper;

    @Autowired
    private DiscountDetailRepository discountDetailRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public Map<String, Object> getAllByOrder(Long idOrder) {
        Map<String, Object> map = new HashMap<>();
        if (idOrder == null) {
            return null;
        }
        List<OrderHistoryDTO> orderHistoryDTOList = new ArrayList<>();
        List<OrderHistory> orderHistoryList = orderHistoryRepository.getAllOrderHistoryByOrder(idOrder);
        List<OrderDetailDTO> lst = orderDetailMapper.toDto(orderDetailRepository.findByIdOrder(idOrder));
        orderHistoryDTOList = orderHistoryMapper.toDto(orderHistoryList);
        for (int i = 0; i < lst.size(); i++) {
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
            lst.set(i, lst.get(i));
        }
        map.put("orderDetail", lst);
        map.put("orderHistory", orderHistoryDTOList);
        return map;
    }

    @Override
    public ServiceResult<OrderDetailDTO> createOrderDetail(OrderDetailDTO orderDetailDTO) {
        ServiceResult<OrderDetailDTO> result = new ServiceResult<>();
//        Discount discount = null;
//        DiscountDetail discountDetail = null;
//        if(orderDetailDTO.getCodeDiscount() != null && orderDetailDTO.getProductId() != null){
//            discount = discountRepository.findByCode(orderDetailDTO.getCodeDiscount());
//            if(discount != null){
//                discountDetail = discountDetailRepository.findByIdDiscountAndIdProduct(discount.getId(), orderDetailDTO.getProductId());
//            }
//        }

        if (orderDetailDTO.getIdOrder() != null && orderDetailDTO.getIdProductDetail() != null) {
            OrderDetail orderDetail = new OrderDetail();
            Optional<ProductDetail> productDetail = productDetailRepository.findById(orderDetailDTO.getIdProductDetail());
            if (productDetail.isPresent()) {
//                if(discount != null && discountDetail != null){
//                    Integer quantityDiscount = discount.getQuantity();
//
//                }
                orderDetail.setIdOrder(orderDetailDTO.getIdOrder());
                orderDetail.setIdProductDetail(orderDetailDTO.getIdProductDetail());
                orderDetail.setPrice(orderDetailDTO.getPrice());
                orderDetail.setQuantity(orderDetailDTO.getQuantity());
                if(orderDetailDTO.getCodeDiscount() != null){
                    orderDetail.setCodeDiscount(orderDetailDTO.getCodeDiscount());
                }
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
