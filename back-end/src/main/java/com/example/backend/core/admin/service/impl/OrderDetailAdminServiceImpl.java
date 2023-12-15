package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.*;
import com.example.backend.core.admin.dto.ProductDetailAdminDTO;
import com.example.backend.core.admin.mapper.*;
import com.example.backend.core.admin.repository.*;
import com.example.backend.core.admin.service.OrderDetailAdminService;
import com.example.backend.core.model.OrderHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderDetailAdminServiceImpl implements OrderDetailAdminService {

    @Autowired
    private OrderDetailAdminRepository orderDetailAdminRepository;
    @Autowired
    private OrderDetailAdminMapper orderDetailAdminMapper;

    @Autowired
    private ProductAdminMapper productAdminMapper;

    private final ProductAdminRepository productAdminRepository;

    private final ProductDetailAdminRepository productDetailAdminRepository;

    @Autowired
    private ImagesAdminRepository imagesAdminRepository;
    @Autowired
    private ImagesAdminMapper imagesAdminMapper;

    @Autowired
    private ColorAdminMapper colorAdminMapper;
    @Autowired
    private ColorAdminRepository colorAdminRepository;

    @Autowired
    private SizeAdminRepository sizeAdminRepository;
    @Autowired
    private SizeAdminMapper sizeAdminMapper;

    @Autowired
    private ProductDetailAdminMapper productDetailAdminMapper;

    @Autowired
    private OrderHistoryAdminRepository orderHistoryAdminRepository;

    @Autowired
    private OrderHistoryAdminMapper orderHistoryAdminMapper;

    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private StaffRepository staffRepository;

    public OrderDetailAdminServiceImpl(ProductAdminRepository productAdminRepository, ProductDetailAdminRepository productDetailAdminRepository) {
        this.productAdminRepository = productAdminRepository;
        this.productDetailAdminRepository = productDetailAdminRepository;
    }

    @Override
    public Map<String, Object> getAllByOrder(Long idOrder) {
        Map<String, Object> map = new HashMap<>();
        if (idOrder == null) {
            return null;
        }
        List<OrderHistoryAdminDTO> orderHistoryAdminDTOList = new ArrayList<>();
        List<OrderHistory> orderHistoryList = orderHistoryAdminRepository.getAllOrderHistoryByOrder(idOrder);
        for (int i = 0; i < orderHistoryList.size(); i++) {
            StaffDTO staffDTO = staffMapper.toDto(staffRepository.findById(orderHistoryList.get(i).getIdStaff()).orElse(null));
            OrderHistoryAdminDTO orderHistoryAdminDTO = orderHistoryAdminMapper.toDto(orderHistoryList.get(i));
            orderHistoryAdminDTO.setStaffDTO(staffDTO);
            orderHistoryAdminDTOList.add(orderHistoryAdminDTO);
        }
        List<OrderDetailAdminDTO> lst = orderDetailAdminMapper.toDto(orderDetailAdminRepository.findByIdOrder(idOrder));
        for (int i = 0; i < lst.size(); i++) {
            ProductDetailAdminDTO productDetailDTO = productDetailAdminMapper.toDto(productDetailAdminRepository.findById(lst.get(i).getIdProductDetail()).get());
            ProductAdminDTO productDTO = productAdminMapper.toDto(productAdminRepository.findById(productDetailDTO.getIdProduct()).get());
            List<ImagesAdminDTO> imagesDTOList = imagesAdminMapper.toDto(imagesAdminRepository.findByIdProduct(productDTO.getId()));
            ColorAdminDTO colorDTO = colorAdminMapper.toDto(colorAdminRepository.findById(productDetailDTO.getIdColor()).get());
            productDetailDTO.setColorDTO(colorDTO);
            SizeAdminDTO sizeDTO = sizeAdminMapper.toDto(sizeAdminRepository.findById(productDetailDTO.getIdSize()).get());
            productDetailDTO.setSizeDTO(sizeDTO);
            productDTO.setImagesDTOList(imagesDTOList);
            productDetailDTO.setProductDTO(productDTO);
            lst.get(i).setProductDetailDTO(productDetailDTO);
            lst.set(i, lst.get(i));
        }
        map.put("orderDetail", lst);
        map.put("orderHistory", orderHistoryAdminDTOList);
        return map;
    }
}
